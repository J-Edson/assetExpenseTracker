package assetexpenselog

import assetExpenseLog.Person

class Asset {

    Person client
    String assetName
    Double balance
    Status status
    
    static constraints = {
    }
}