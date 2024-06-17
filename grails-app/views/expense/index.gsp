<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Asset Expense Log</title>
</head>
<body>
    <g:form controller="expense" action="save">
        <input id="txnName" name="txnName" type="text" placeholder="Transaction" required/><br>
        <select id="categoryID" name="categoryID" multiple>
            <g:each var="category" in="${categoryList}">
                <option value=${category.id}>${category.name}</option>
            </g:each>
        </select><br>
        <input id="txnAmt" name="txnAmt" type="number" step="0.01" min="0" placeholder="0.00" required/><br>
        <select id="creditAssetID" name="creditAssetID" multiple>
            <g:each var="asset" in="${assetList}">
                <option value=${asset.id}>${asset.assetName}</option>
            </g:each>
        </select>
        <g:submitButton name="Log Expense" value="Log Expense"/>
    </g:form>
    <div>
        <g:each var="expense" in="${expenseList}">
            <g:link controller="expense" action="show" params="[id: expense.id]">${expense.txnName}:${expense.txnAmt}:${expense.status.name}</g:link><br>
        </g:each>
    </div>
    <div>
        Asset Total Expense: ${totalExpense}
    </div>
</body>
</html>