<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Asset Expense Log</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
    <g:render template="/components/navbar"/>
    <div class="indexWrapper"> 
        <div class="row" >
            <div class="col-2">
                <g:render template="/components/sidebar"/>
            </div>
            <div class="col-7">
                <div class="row text-center" >
                    <div class="col">
                        Total Income ${totalBalance}
                    </div>
                    <div class="col">
                        Total Expense ${totalExpense}
                    </div>
                </div>
            </div>
            <div class="col-3">
            </div>
        </div>
    </div>
</body>
</html>
