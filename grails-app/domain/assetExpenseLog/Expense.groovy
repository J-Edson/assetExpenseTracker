package assetexpenselog

import assetExpenseLog.Person

class Expense {

    Person client
    String txnName
    Double txnAmt
    Asset creditAsset
    Status status

    static constraints = {
    }
}
