<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Asset</title>
</head>
<body>
    <div>
        Name: ${assetInstance.assetName}
    </div>
    <div>
        Balance: ${assetInstance.balance}
    </div>
    <div>
        Status: ${assetInstance.status.name}
    </div>
    <g:form controller="asset" action="updateBalance">
        <label for="cashAmount">Enter amount:</label><br>
        <input id="txnAmt" name="txnAmt" type="number" step="0.01" min="0" placeholder="0.00" required/><br>
        <input type="hidden" name="id" value="${assetInstance.id}" />
        <input type="hidden" name="transactionType" value="debit" />
        <g:submitButton name="Add" value="Add"/>
    </g:form>
    <g:form controller="asset" action="updateBalance">
        <label for="cashAmount">Enter amount:</label><br>
        <input id="txnAmt" name="txnAmt" type="number" step="0.01" min="0" placeholder="0.00" required/><br>
        <input type="hidden" name="id" value="${assetInstance.id}" />
        <input type="hidden" name="transactionType" value="credit" />
        <g:submitButton name="Reduce" value="Reduce"/>
    </g:form>

    <div class="modal fade" id="transferBalanceModal" tabindex="-1" role="dialog" aria-labelledby="transferBalanceModal" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Transfer Balance</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <g:form controller="asset" action="transferBalance">
                    <div>${assetInstance.assetName}</div>
                    <input id="txnAmt" name="txnAmt" type="number" step="0.01" min="0" placeholder="0.00" required/><br>
                    <select id="debitAssetID" name="debitAssetID" multiple>
                        <g:each var="asset" in="${assetList}">
                            <option value=${asset.id}>${asset.assetName}</option>
                        </g:each>
                    </select>
                    <input type="hidden" name="id" value="${assetInstance.id}" />
                    <g:submitButton name="Transfer" value="Transfer Balance"/>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                </g:form>
            </div>
        </div>
    </div>
    </div>

    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#transferBalanceModal">Transfer Balance</button>
    <g:link action="delete" id="${assetInstance.id}">Remove Asset</g:link>
</body>
</html>