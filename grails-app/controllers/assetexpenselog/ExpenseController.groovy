package assetexpenselog

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured('ROLE_ADMIN')
class ExpenseController {

    //statusList = ["0-Active", "1-Closed", "2-Processed", "3-Reversed"]
    //recordTypeList = ["0-New Asset", "1-Remove Asset", "2-Debit Balance", "3-Credit Balance", "4-Transfer Credit", "5-Transfer Debit", "6-Log Expense", "7-Reverse Expense"]
    private userInstance = getAuthenticatedUser()
    private statusList = Status.listOrderByCode()
    private recordTypeList = RecordType.listOrderByCode()

    def index() {
        def expenseList = Expense.findAllByClient(userInstance)
        println expenseList
        def assetList = Asset.list()
        def totalExpense = 0
        for (expense in expenseList) {
            if(expense.status.id == statusList[2].id){
                println expense
                totalExpense += expense.txnAmt
            }
        }

        [expenseList: expenseList, totalExpense:totalExpense, assetList:assetList]
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
                status: statusList[2]
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