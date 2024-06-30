package assetexpenselog

import assetExpenseLog.Person

class Savings {

    Person client
    String acctName
    String acctNo
    Double balance
    Date expiryDate
    Status status
    
    static constraints = {
        acctNo nullable: true
        expiryDate nullable: true
    }
}