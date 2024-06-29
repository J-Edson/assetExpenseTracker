package assetexpenselog
import grails.plugin.springsecurity.annotation.Secured

@Secured('ROLE_ADMIN')
class LogController {

    def index() { 
        def recordList = Record.listOrderByLogDate(order: "desc")

        [recordList:recordList]
    }
}
