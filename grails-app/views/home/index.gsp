<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Asset Expense Log</title>
</head>
<body>
    <g:render template="/components/navbar"/>
    <div class="indexWrapper"> 
        <div class="row" >
            <div class="col-2">
                <g:render template="/components/sidebar"/>
            </div>
            <div class="col-7">
                <div class="row" >
                    <div class="col-12">
                        <div class="row text-center" >
                            <div class="col mx-5 p-3" style="background-image: linear-gradient(to bottom right, #26E1D3, #3B706D); border-radius: 15px">
                                <div class="row text-center align-items-center" >
                                    <div class="col-3">
                                        <div><img id="savingsArrowIcon" src='${assetPath(src: "savings_arrow_icon.svg")}'/></div>
                                    </div>
                                    <div class="col-6 text-start">
                                        <div style="font-family: 'Inter', sans-serif;font-optical-sizing: auto;font-weight: 600;font-style: normal;">Total Savings</div> 
                                        <div style="font-family: 'Inter', sans-serif;font-optical-sizing: auto;font-weight: 700;font-style: normal;font-size: 35px;">
                                            &#x20B1;<g:formatNumber number="${totalBalance}" format="#,##0.00" />
                                        </div>
                                    </div>
                                    <div class="col-2 align-self-end" style="color: #41D4A8; background-color: #FFFFFF; border-radius: 5px; font-family: 'Inter', sans-serif;font-optical-sizing: auto;font-weight: 500;font-style: normal;">
                                        +1.29%
                                    </div>
                                </div>
                            </div>
                            <div class="col mx-5 p-3" style="background-image: linear-gradient(to bottom right, #B05C31, #F02C07); border-radius: 15px">
                                <div class="row text-center align-items-center" >
                                    <div class="col-3">
                                        <img id="expenseArrowIcon" src='${assetPath(src: "expense_arrow_icon.svg")}'/>
                                    </div>
                                    <div class="col-6 text-start">
                                        <div style="font-family: 'Inter', sans-serif;font-optical-sizing: auto;font-weight: 600;font-style: normal;color: #FFFFFF">Total Expense</div> 
                                        <div style="font-family: 'Inter', sans-serif;font-optical-sizing: auto;font-weight: 700;font-style: normal;font-size: 35px;color: #FFFFFF">
                                            &#x20B1;<g:formatNumber number="${totalExpense}" format="#,##0.00" />
                                        </div>
                                    </div>
                                    <div class="col-2 align-self-end" style="color: red; background-color: #FFFFFF; border-radius: 5px; font-family: 'Inter', sans-serif;font-optical-sizing: auto;font-weight: 400;font-style: normal;">
                                        +1.29%
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col mx-5 my-5">
                        <g:hiddenField id ="weeklyActivity" name="weeklyActivity" value="${weeklyActivity}" />
                        <g:render template="/components/weekly_activity_chart" />
                    </div>
                    <div class="col mx-5 mb-5">
                        <g:render template="/components/savings_history"/>
                    </div>
                </div>
            </div>
            <div class="col-3">
                <div class="row">
                    <div class="col-12">
                        <g:render template="/components/expense_chart"/>
                    </div>
                    <div class="col-10">
                        <g:render template="/components/recent_transactions"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
