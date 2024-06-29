<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Asset Expense Log</title>
</head>
<body>
    <div class="row py-5 justify-content-center" style="background-color: #F5F7FA;">
        <div class="col-11">
            <div class="row">
                <div class="col-12">
                    <div class="row justify-content-around">
                        <div class="col-7">
                            <div style="color: #343C6A;font-size: 40px;">Add Expense</div>
                            <g:form controller="expense" action="save">
                                <div class="row justify-content-between gy-5">
                                    <div class="col-6">
                                        <div class="row">
                                            <div class="col-12">
                                                <div class="form-text">Description</div>
                                            </div>
                                            <div class="col-12">
                                                <input class="form-input" id="txnName" name="txnName" type="text" placeholder="Description" required/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-6">
                                        <div class="row">
                                            <div class="col-12">
                                                <div class="form-text">Amount</div>
                                            </div>
                                            <div class="col-12">
                                                <input class="form-input" id="txnAmt" name="txnAmt" type="number" step="0.01" min="0" placeholder="0.00" required/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-6">
                                        <div class="row">
                                            <div class="col-12">
                                                <div class="form-text">Category</div>
                                            </div>
                                            <div class="col-12">
                                                <select class="form-input" id="categoryID" name="categoryID" multiple required>
                                                    <g:each var="category" in="${categoryList}">
                                                        <option value=${category.id}>${category.name}</option>
                                                    </g:each>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-6">
                                        <div class="row">
                                            <div class="col-12">
                                                <div class="form-text">Account Credited</div>
                                            </div>
                                            <div class="col-12">
                                                <select class="form-input" id="creditAssetID" name="creditAssetID" multiple required>
                                                    <g:each var="asset" in="${assetList}">
                                                        <option value=${asset.id}>${asset.assetName}</option>
                                                    </g:each>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-12 mt-4 text-end">
                                        <g:submitButton id="addExpenseButton" name="Log Expense" value="Log Expense" style="background-color: #F02C07; border-radius: 10px; color: white; padding: 10px; font-size: 20px !important;"/>
                                    </div>
                                </div>
                            </g:form>
                        </div>
                        <div class="col-4">
                            <g:render template="/components/expense_chart"/>
                        </div>
                    </div>    
                </div>
                <div class="col-12 mt-5">
                    <table class="table-danger table-hover table-striped" style="font-size: 20px">
                        <thead>
                            <tr>
                            <th scope="col">Description</th>
                            <th scope="col">Category</th>
                            <th scope="col">Account Credited</th>
                            <th scope="col">Amount</th>
                            <th scope="col">Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <g:each var="expense" in="${expenseList}">
                                <tr>
                                    <td>${expense.txnName}</td>
                                    <td>${expense.category.name}</td>
                                    <td>${expense.creditAsset.assetName}</td>
                                    <td><g:formatNumber number="${expense.txnAmt}" format="#,##0.00" /></td>
                                    <td>${expense.status.name}</td>
                                </tr>
                            </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</body>
</html>