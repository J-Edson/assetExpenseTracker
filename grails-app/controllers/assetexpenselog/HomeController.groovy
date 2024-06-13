package assetexpenselog

import grails.plugin.springsecurity.annotation.Secured

@Secured('ROLE_ADMIN')
class HomeController {
    //statusList = ["0-Active", "1-Closed", "2-Processed", "3-Reversed"]
    //recordTypeList = ["0-New Asset", "1-Remove Asset", "2-Debit Balance", "3-Credit Balance", "4-Transfer Credit", "5-Transfer Debit", "6-Log Expense", "7-Reverse Expense"]
    private statusList = Status.listOrderByCode()
    private recordTypeList = RecordType.listOrderByCode()
    private userInstance = getAuthenticatedUser()

    def index() { 
        def assetActiveList = Asset.findAllByClientAndStatus(userInstance, statusList[0])
        def totalBalance = 0
        for (asset in assetActiveList) {
            totalBalance += asset.balance
        }

        def expenseList = Expense.findAllByClientAndStatus(userInstance, statusList[2])
        def totalExpense = 0
        for (expense in expenseList) {
            totalExpense += expense.txnAmt
        }  
        [totalBalance:totalBalance, totalExpense:totalExpense]
    }
}
