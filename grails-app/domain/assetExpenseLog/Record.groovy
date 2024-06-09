package assetexpenselog

import java.time.LocalDateTime
import java.time.ZoneId

import assetExpenseLog.Person

class Record {

    Person client
    Asset credit
    Asset debit
    String description
    Expense expense
    RecordType recordType
    Status status
    Double txnAmt
    LocalDateTime logDate = { -> LocalDateTime.now(ZoneId.of("Asia/Manila")) }()

    static constraints = {
        credit nullable: true
        debit nullable: true
        expense nullable: true
    }
}
