package assetexpenselog

import grails.gorm.transactions.Transactional
import assetExpenseLog.Person
import assetExpenseLog.Authority
import assetExpenseLog.PersonAuthority

class AuthController {
    def index(){

    }
    @Transactional
    def register() {
        println "params " + params
        def personInstance = new Person(username: params.username, password: params.password)
        def initialAuthority = Authority.findByAuthority('ROLE_USER')

        println personInstance.username
        println personInstance.password
        if (!personInstance.save()) {
            personInstance.errors.allErrors.each {
                println it
            }
        }else {
            println "PersonSave"
        }
        PersonAuthority.create personInstance, initialAuthority
        redirect(uri: "/")
     }
}
