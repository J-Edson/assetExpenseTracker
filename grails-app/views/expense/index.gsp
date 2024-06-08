<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Expense</title>
</head>
<body>
    <a href="/">
        <button>Homepage</button>
    </a>
    <!-- Modal Start -->
    <div class="modal fade" id="logExpense" tabindex="-1" role="dialog" aria-labelledby="logExpense" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Log Expense</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <g:form controller="expense" action="save">
                    <input id="txnName" name="txnName" type="text" placeholder="Transaction" required/><br>
                    <input id="txnAmt" name="txnAmt" type="number" step="0.01" min="0" placeholder="0.00" required/><br>
                    <select id="creditAssetID" name="creditAssetID" multiple>
                        <g:each var="asset" in="${assetList}">
                            <option value=${asset.id}>${asset.assetName}</option>
                        </g:each>
                    </select>
                    <g:submitButton name="Log Expense" value="Log Expense"/>
                </g:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary">Confirm</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
            </div>
        </div>
    </div>
    <!-- Modal End -->
    <div>
        <g:each var="expense" in="${expenseList}">
            <g:link controller="expense" action="show" params="[id: expense.id]">${expense.txnName}:${expense.txnAmt}</g:link>
        </g:each>
    </div>
    <div>
        Asset Total Expense: ${totalExpense}
    </div>
</body>
</html>