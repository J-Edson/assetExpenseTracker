package assetexpenselog

import assetExpenseLog.Person

class Expense {

    Person client
    String txnName
    Double txnAmt
    Savings creditAcct
    Status status
    ExpenseCategory category
    
    static constraints = {
    }
}
