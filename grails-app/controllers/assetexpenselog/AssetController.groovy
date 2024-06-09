package assetexpenselog

	
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured('ROLE_ADMIN')
class AssetController {
    //statusList = ["0-Active", "1-Closed", "2-Processed", "3-Reversed"]
    //recordTypeList = ["0-New Asset", "1-Remove Asset", "2-Debit Balance", "3-Credit Balance", "4-Transfer Credit", "5-Transfer Debit", "6-Log Expense", "7-Reverse Expense"]
    private statusList = Status.listOrderByCode()
    private recordTypeList = RecordType.listOrderByCode()
    private userInstance = getAuthenticatedUser()

    def index () {
        def assetList = Asset.list()
        def assetActiveList = Asset.findAllByClientAndStatus(userInstance, statusList[0])
        def totalBalance = 0
        for (asset in assetActiveList) {
            totalBalance += asset.balance
        }
        println totalBalance
        [assetList: assetList, totalBalance:totalBalance]
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