package assetexpenselog

	
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import groovy.sql.Sql
import java.time.LocalDate
import java.time.ZoneId
import java.text.SimpleDateFormat
import grails.converters.JSON

@Secured('ROLE_ADMIN')
class AssetController {
    //statusList = ["0-Active", "1-Closed", "2-Processed", "3-Reversed"]
    //recordTypeList = ["0-New Asset", "1-Remove Asset", "2-Debit Balance", "3-Credit Balance", "4-Transfer Credit", "5-Transfer Debit", "6-Log Expense", "7-Reverse Expense"]
    private statusList = Status.listOrderByCode()
    private recordTypeList = RecordType.listOrderByCode()
    private userInstance = getAuthenticatedUser()
    def dataSource

    def index () {
        def assetList = Asset.list()
        def assetActiveList = Asset.findAllByClientAndStatus(userInstance, statusList[0])
        def totalBalance = 0
        for (asset in assetActiveList) {
            totalBalance += asset.balance
        }
        println totalBalance

        //balance history chart
        def sql = new Sql(dataSource)
        def query = ""
        println recordTypeList
        query = "SELECT UPPER(to_char(log_date::date, 'Mon-YYYY')) AS month_record, SUM(CASE WHEN record_type_id in ("+recordTypeList[0].id+", "+recordTypeList[2].id+", "+recordTypeList[7].id+") THEN txn_amt ELSE -txn_amt END) AS balance FROM record WHERE NOT record_type_id in ("+recordTypeList[4].id+", "+recordTypeList[5].id+") AND client_id = "+userInstance.id+" GROUP BY UPPER(to_char(log_date::date, 'Mon-YYYY'))"
        def savingsBalanceTable = sql.rows(query)
        def savingsBalanceHistory = []
        def balanceHistory = 0
        savingsBalanceTable.each { savingBalance ->
            balanceHistory += savingBalance.balance
            savingsBalanceHistory.add([savingBalance.month_record, balanceHistory])
        }
        savingsBalanceHistory = savingsBalanceHistory as JSON
        //
        query = "SELECT to_char(log_date, 'YYYY-MM-DD HH24:MI:SS') AS date, description, CASE WHEN record_type_id IN (8, 10, 13, 15) THEN txn_amt ELSE -1*txn_amt END AS amount, CASE WHEN expense_id is null THEN 0 ELSE 1 END AS log_type FROM record WHERE client_id = "+userInstance.id+" ORDER BY log_date DESC limit 8"
        def recordList = sql.rows(query)
        println recordList
        [assetList: assetList, totalBalance:totalBalance, assetActiveList:assetActiveList, savingsBalanceHistory:savingsBalanceHistory, recordList:recordList]
    }

    def show (Long id) {
        def assetInstance = Asset.get(id)

        def assetActiveList = Asset.findAllByClientAndStatus(userInstance, statusList[0])
        [assetInstance:assetInstance, assetActiveList:assetActiveList]
    }

    @Transactional
    def save () {
        println "params " + params
        def assetInstance = new Asset(
            client: userInstance,
            assetName: params.assetName,
            acctNo: params.acctNo,
            expiryDate: params.expiryDate,
            balance: params.balance,
            status: statusList[0]
        )
        println assetInstance.assetName
        println assetInstance.balance
        if(!assetInstance.save()) {
            assetInstance.errors.allErrors.each {
                println it
            }
        }else {
            println "AssetSave"
        }
        def recordLog = new Record(
            client: userInstance,
            debit: assetInstance,
            description: "New Asset",
            recordType: recordTypeList[0],
            status: statusList[2],
            txnAmt: assetInstance.balance
        )
        if(!recordLog.save()) {
            println "%%Error%%"
            recordLog.errors.allErrors.each {
                println "Error " + it
            }
        }else {
            println "RecordSave"
        }
        println "Redirecting"
        redirect(action: "index")
    }

    @Transactional
    def delete () {
        println "params " + params
        def assetInstance = Asset.get(params.id)
        assetInstance.status = statusList[1]
        assetInstance.save()
        def recordLog = new Record(
            client: userInstance,
            credit: assetInstance,
            description: "Closed Asset",
            recordType: recordTypeList[1],
            status: statusList[2],
            txnAmt: assetInstance.balance
        )
        recordLog.save()
        redirect(action: "index")
    }

    @Transactional
    def updateBalance (Long id) {
        println "%param%" + params
        def assetInstance = Asset.get(id)
        def assetBalance = assetInstance.balance
        def txnAmt = params.txnAmt.toDouble()
        def recordLog = new Record()
        if(params.transactionType.equals("debit")) {
            println "debitBalance"
            assetInstance.balance += txnAmt
            assetInstance.save()

            recordLog.client = userInstance
            recordLog.debit = assetInstance
            recordLog.description = "Add Balance"
            recordLog.recordType = recordTypeList[2]
            recordLog.status = statusList[2]
            recordLog.txnAmt = txnAmt
            recordLog.save()
        }else{
            println "creditBalance"
            if(assetBalance >= txnAmt){
                println "assetBalance < txnAmt"
                assetInstance.balance -= txnAmt
                assetInstance.save()

                recordLog.client = userInstance
                recordLog.credit = assetInstance
                recordLog.description = "Reduce Balance"
                recordLog.recordType = recordTypeList[3]
                recordLog.status = statusList[2]
                recordLog.txnAmt = txnAmt
                recordLog.save()
            }
        }
        println "%newBalance% " + assetInstance.balance  
        redirect(action: "index")
    }

    @Transactional
    def transferBalance (Long id) {
        println "%transferBalanceParams% " + params
        def creditAsset = Asset.get(id)
        def debitAsset = Asset.get(params.debitAssetID)
        def txnAmt = params.txnAmt.toDouble()

        if(txnAmt <= creditAsset.balance){
            creditAsset.balance -= txnAmt
            debitAsset.balance += txnAmt
            def recordLogDebit = new Record(
                client: userInstance,
                debit: debitAsset,
                description: "Balance Received",
                recordType: recordTypeList[5],
                status: statusList[2],
                txnAmt: txnAmt
            )
            def recordLogCredit = new Record(
                client: userInstance,
                credit: creditAsset,
                description: "Balance Transfer",
                recordType: recordTypeList[4],
                status: statusList[2],
                txnAmt: txnAmt
            )
            recordLogDebit.save()
            recordLogCredit.save()
        }

        creditAsset.save()
        debitAsset.save()
        redirect(action: "index")
    }

}