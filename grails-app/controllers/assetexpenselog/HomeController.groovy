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
    //expenseCategoryList = ["0-Food", "1-Transportation", "2-Entertainment", "3-Utilities", "4-Personal Care", "5-Housing", "6-Healthcare", "7-Loans", "8-Insurance"]
    private statusList = Status.listOrderByCode()
    private recordTypeList = RecordType.listOrderByCode()
    private expenseCategoryList = ExpenseCategory.listOrderByCode()
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
        //weekly activity chart
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
        }
        weeklyActivity = weeklyActivity as JSON
        //weekly activity chart end

        //balance history chart
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

        //expense chart
        println statusList
        query = "SELECT B.name, SUM(A.txn_amt) as amount FROM expense A JOIN expense_category B on B.id = A.category_id WHERE A.client_id = "+userInstance.id+" AND A.status_id = "+statusList[2].id+" GROUP BY B.name"
        def expenseSummary = sql.rows(query)
        def expenseData = []
        expenseSummary.each { expense ->
            expenseData.add([expense.name, expense.amount])
        }
        expenseData = expenseData as JSON
        //

        query = "SELECT to_char(log_date, 'YYYY-MM-DD HH24:MI:SS') AS date, description, CASE WHEN record_type_id IN (8, 10, 13, 15) THEN txn_amt ELSE -1*txn_amt END AS amount, CASE WHEN expense_id is null THEN 0 ELSE 1 END AS log_type FROM record WHERE client_id = "+userInstance.id+" ORDER BY log_date DESC limit 4"
        def recordList = sql.rows(query)

        [totalBalance:totalBalance, totalExpense:totalExpense, weeklyActivity:weeklyActivity, savingsBalanceHistory:savingsBalanceHistory, expenseData:expenseData, recordList:recordList]
    }
}
