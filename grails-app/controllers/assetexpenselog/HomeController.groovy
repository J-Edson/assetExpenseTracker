package assetexpenselog

import grails.plugin.springsecurity.annotation.Secured

import groovy.sql.Sql
import java.time.LocalDate
import java.time.ZoneId
import java.text.SimpleDateFormat
import grails.converters.JSON

@Secured('ROLE_ADMIN')
class HomeController {
    //statusList = ["0-Active", "1-Closed", "2-Processed", "3-Reversed"]
    //recordTypeList = ["0-New Asset", "1-Remove Asset", "2-Debit Balance", "3-Credit Balance", "4-Transfer Credit", "5-Transfer Debit", "6-Log Expense", "7-Reverse Expense"]
    private statusList = Status.listOrderByCode()
    private recordTypeList = RecordType.listOrderByCode()
    private userInstance = getAuthenticatedUser()
    def dataSource

    def index() { 
        def assetActiveList = Asset.findAllByClientAndStatus(userInstance, statusList[0])
        def totalBalance = 0.00D
        for (asset in assetActiveList) {
            totalBalance += asset.balance
        }

        def expenseList = Expense.findAllByClientAndStatus(userInstance, statusList[2])
        def totalExpense = 0.00D
        for (expense in expenseList) {
            totalExpense += expense.txnAmt
        }
        def sdf = new SimpleDateFormat('EEEE')
        def dateToday = LocalDate.now(ZoneId.of("Asia/Manila"))
        def sql = new Sql(dataSource)
        def query = ""
        def weeklyActivity = []
        println recordTypeList
        for(int x = 6; x >= 0; x--){
            def dataDate = dateToday.minusDays(x)
            query = "SELECT to_char(A.log_date::date, 'Day') AS record_date, SUM(CASE WHEN record_type_id = "+recordTypeList[6].id+" THEN txn_amt ELSE 0 END) - SUM(CASE WHEN record_type_id = "+recordTypeList[7].id+" THEN txn_amt ELSE 0 END) AS total_expense FROM record A WHERE A.client_id = "+userInstance.id+" AND  A.log_date::date = '" + dataDate + "' AND A.record_type_id in ("+recordTypeList[6].id+", "+recordTypeList[7].id+") GROUP BY A.log_date::date"
            def activity = sql.rows(query)
            def dailyActivity = [];
            if(!activity){
                dailyActivity[0] = sdf.format(Date.from(dataDate.atStartOfDay(ZoneId.of("Asia/Manila")).toInstant()))
                dailyActivity[2] = 0
            }else{
                dailyActivity[0] = activity.record_date[0]
                dailyActivity[2] = activity.total_expense[0] 
            }
            query = "SELECT to_char(A.log_date::date, 'Day') AS record_date, SUM(CASE WHEN record_type_id in ("+recordTypeList[0].id+", "+recordTypeList[2].id+") THEN txn_amt ELSE 0 END) - SUM(CASE WHEN record_type_id in ("+recordTypeList[1].id+", "+recordTypeList[3].id+") THEN txn_amt ELSE 0 END) AS total_savings FROM record A WHERE A.client_id = "+userInstance.id+" AND  A.log_date::date = '" + dataDate + "' AND A.record_type_id in ("+recordTypeList[0].id+", "+recordTypeList[2].id+", "+recordTypeList[1].id+", "+recordTypeList[3].id+") GROUP BY A.log_date::date"
            activity = sql.rows(query)
            if(!activity){
                dailyActivity[1] = 0
            }else{
                dailyActivity[1] = activity.total_savings[0]
            }

            weeklyActivity.add(dailyActivity)
            println dailyActivity + " daily"
            println weeklyActivity + " weeklyActivity"
        }
        weeklyActivity = weeklyActivity as JSON
        println weeklyActivity
        println totalExpense  
        [totalBalance:totalBalance, totalExpense:totalExpense, weeklyActivity:weeklyActivity]
    }
}
