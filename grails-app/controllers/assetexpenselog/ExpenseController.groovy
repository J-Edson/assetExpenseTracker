package assetexpenselog

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured('ROLE_ADMIN')
class ExpenseController {

    def index() {
        def expenseList = Expense.list()
        def assetList = Asset.list()
        def totalExpense = 0
        for (expense in expenseList) {
            totalExpense += expense.txnAmt
        }
        println totalExpense
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

        creditAsset.balance -= txnAmt

        def expenseInstance = new Expense(
        txnName: params.txnName,
        txnAmt: txnAmt,
        creditAsset: creditAsset
        )

        creditAsset.save()
        expenseInstance.save()            

        redirect(action: "index")
    }

    @Transactional
    def reverse(){
        println "params " + params
        def expenseInstance = Expense.get(params.id)
        def assetInstance = expenseInstance?.creditAsset
        assetInstance.balance += expenseInstance.txnAmt
        expenseInstance.status = Status.get(3)
        assetInstance.save()
        expenseInstance.save()
        println "newAssetBal " + assetInstance.balance
        println "reversedExpense " + expenseInstance.txnName
        redirect(action: "index")
    }
}