package assetexpenselog

import assetExpenseLog.Person

class Asset {

    Person client
    String assetName
    String acctNo
    Double balance
    Date expiryDate
    Status status
    
    static constraints = {
        acctNo nullable: true
        expiryDate nullable: true
    }
}