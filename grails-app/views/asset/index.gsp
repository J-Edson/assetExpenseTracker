<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Assets</title>
</head>
<body>
    <a href="/">
        <button>Homepage</button>
    </a>
    <!-- Modal Start -->
    <div class="modal fade" id="addAsset" tabindex="-1" role="dialog" aria-labelledby="addAsset" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Add Asset</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <g:form controller="asset" action="save">
                    <input id="assetName" name="assetName" type="text" placeholder="Asset" required/><br>
                    <input id="balance" name="balance" type="number" step="0.01" min="0" placeholder="0.00" required/><br>
                    <g:submitButton name="Add Asset" value="Add Asset"/>
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
        <g:each var="asset" in="${assetList}">
            <g:link controller="asset" action="show" params="[id: asset.id]">${asset.assetName}:${asset.balance}:${asset.status.name}</g:link><br>
        </g:each>
    </div>
    <div>
        Asset Total Balance: ${totalBalance}
    </div>
</body>
</html>