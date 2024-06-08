package assetexpenselog

import assetExpenseLog.Person
import assetExpenseLog.Authority
import assetExpenseLog.PersonAuthority

import grails.gorm.transactions.Transactional

class BootStrap {

    def init = {
        addTestUser()
    }

    @Transactional
    void addTestUser() {
        def adminRole = new Authority(authority: 'ROLE_ADMIN').save()

        def testUser = new Person(username: 'password', password: 'password').save()

        PersonAuthority.create testUser, adminRole

        PersonAuthority.withSession {
            it.flush()
            it.clear()
        }

        assert Person.count() == 1
        assert Authority.count() == 1
        assert PersonAuthority.count() == 1
    }
}
