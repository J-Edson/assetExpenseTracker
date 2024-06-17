<!doctype html>
<html>
    <head>
        <meta name="layout" content="main"/>
        <title>Asset Expense Log</title>
    </head>
    <body>
        <g:form controller="asset" action="save">
            <input id="assetName" name="assetName" type="text" placeholder="Asset" required/><br>
            <input id="balance" name="balance" type="number" step="0.01" min="0" placeholder="0.00" required/><br>
            <g:submitButton name="Add Asset" value="Add Asset"/>
        </g:form>
        <div>
            <g:each var="asset" in="${assetList}">
                <g:link controller="asset" action="show" params="[id: asset.id]">${asset.assetName}:${asset.balance}:${asset.status.name}</g:link><br>
            </g:each>
        </div>
        <div>
            Asset Total Balance: ${totalBalance}
        </div>
    </body>
</html>