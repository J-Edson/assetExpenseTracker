package assetexpenselog

import assetExpenseLog.Person
import assetExpenseLog.Authority
import assetExpenseLog.PersonAuthority

import grails.gorm.transactions.Transactional

class BootStrap {

    def init = {
        addInitialPersonAuthority()
        addInitialStatus()
        addInitialRecordType()
    }

    @Transactional
    void addInitialPersonAuthority() {
        def adminRole = Authority.findOrSaveByAuthority('ROLE_ADMIN')
        def userRole = Authority.findOrSaveByAuthority('ROLE_USER')
        def adminUser = Person.findByUsername('admin')
        if(!adminUser){
            adminUser = new Person(username: 'admin', password: 'admin').save()
            PersonAuthority.create adminUser, adminRole
        }

        PersonAuthority.withSession {
            it.flush()
            it.clear()
        }

    }

    @Transactional
    void addInitialStatus() {
        def statusTypeList = ["0-Active", "1-Closed", "2-Processed", "3-Reversed"]

        statusTypeList.each { type ->
            String[] statusType = type.split("-");
            def code = Integer.parseInt(statusType[0])
            def name = statusType[1]
            def newStatus = Status.findByName(name)
            if(!newStatus){
                newStatus = new Status(name: name, code: code).save()
            }
        }
    }

    @Transactional
    void addInitialRecordType() {
        def recordTypeList = ["0-New Asset", "1-Remove Asset", "2-Debit Balance", "3-Credit Balance", "4-Transfer Credit", "5-Transfer Debit", "6-Log Expense", "7-Reverse Expense"]

        recordTypeList.each { type ->
            String[] recordType = type.split("-");
            def code = Integer.parseInt(recordType[0])
            def name = recordType[1]
            def newRecordType = RecordType.findByName(name)
            if(!newRecordType){
                newRecordType = new RecordType(name: name, code: code).save()
            }
        }
    }
}
