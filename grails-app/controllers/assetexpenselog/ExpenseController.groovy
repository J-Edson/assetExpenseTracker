package assetexpenselog

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import groovy.sql.Sql
import java.time.LocalDate
import java.time.ZoneId
import java.text.SimpleDateFormat
import grails.converters.JSON

@Secured('ROLE_ADMIN')
class ExpenseController {

    //statusList = ["0-Active", "1-Closed", "2-Processed", "3-Reversed"]
    //recordTypeList = ["0-New Asset", "1-Remove Asset", "2-Debit Balance", "3-Credit Balance", "4-Transfer Credit", "5-Transfer Debit", "6-Log Expense", "7-Reverse Expense"]
    //expenseCategoryList = ["0-Food", "1-Transportation", "2-Entertainment", "3-Utilities", "4-Personal Care", "5-Housing", "6-Healthcare", "7-Loans", "8-Insurance"]
    private userInstance = getAuthenticatedUser()
    private statusList = Status.listOrderByCode()
    private recordTypeList = RecordType.listOrderByCode()
    def dataSource


    def index() {
        def expenseList = Expense.findAllByClient(userInstance, [sort: "id", order: "desc"])
        println statusList
        def assetList = Asset.findAllByClientAndStatus(userInstance, statusList[0])
        def totalExpense = 0
        def categoryList = ExpenseCategory.list()
        for (expense in expenseList) {
            if(expense.status.id == statusList[2].id){
                println expense
                totalExpense += expense.txnAmt
            }
        }
        //expense chart
        def sql = new Sql(dataSource)
        def query = ""
        println statusList
        query = "SELECT B.name, SUM(A.txn_amt) as amount FROM expense A JOIN expense_category B on B.id = A.category_id WHERE A.client_id = "+userInstance.id+" AND A.status_id = "+statusList[2].id+" GROUP BY B.name"
        def expenseSummary = sql.rows(query)
        def expenseData = []
        expenseSummary.each { expense ->
            expenseData.add([expense.name, expense.amount])
        }
        expenseData = expenseData as JSON
        //
        println categoryList
        [expenseList: expenseList, totalExpense:totalExpense, assetList:assetList, categoryList:categoryList, expenseData:expenseData]
    }

    def show (Long id) {
        def expenseInstance = Expense.get(id)
        [expenseInstance:expenseInstance]
    }

    @Transactional
    def save () {
        println "%%params%% " + params
        def creditAsset = Asset.get(params.creditAssetID)
        def txnAmt = params.txnAmt.toDouble()
        if(creditAsset.balance >= txnAmt){
            creditAsset.balance -= txnAmt

            def expenseInstance = new Expense(
                client: userInstance,
                txnName: params.txnName,
                txnAmt: txnAmt,
                creditAsset: creditAsset,
                status: statusList[2],
                category: ExpenseCategory.get(params.categoryID)
            )

            creditAsset.save(flush: true)
            expenseInstance.save(flush: true)
            def recordLog = new Record(
                client: userInstance,
                credit: creditAsset,
                expense: expenseInstance,
                description: "Log Expense",
                recordType: recordTypeList[6],
                status: statusList[2],
                txnAmt: txnAmt
            )
            recordLog.save(flush: true)   
        }

        redirect(action: "index")     

    }

    @Transactional
    def reverse(){
        println "params " + params
        def expenseInstance = Expense.get(params.id)
        def assetInstance = expenseInstance?.creditAsset
        def txnAmt = expenseInstance.txnAmt

        assetInstance.balance += txnAmt
        expenseInstance.status = statusList[3]
        assetInstance.save()
        expenseInstance.save()

        def recordLog = new Record(
            client: userInstance,
            debit: assetInstance,
            expense: expenseInstance,
            description: "Reverse Expense",
            recordType: recordTypeList[7],
            status: statusList[3],
            txnAmt: txnAmt
        )
        recordLog.save(flush: true)

        println "newAssetBal " + assetInstance.balance
        println "reversedExpense " + expenseInstance.txnName
        redirect(action: "index")
    }
}