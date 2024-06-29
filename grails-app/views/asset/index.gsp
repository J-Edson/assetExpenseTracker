<!doctype html>
<html>
    <head>
        <meta name="layout" content="main"/>
        <title>Asset Expense Log</title>
    </head>
    <body>
        <div class="row py-5" style="background-color: #F5F7FA;">
            <div class="col-12">
                <div class="swiper-container">
                    <div class="swiper-wrapper">
                        <g:each var="activeAsset" in="${assetActiveList}">
                            <g:render template="/components/asset_swiper_slide" model="${['activeAsset': activeAsset]}"/>
                        </g:each>
                    </div>
                    <div class="swiper-pagination"></div>
                    <div class="swiper-button-prev"></div>
                    <div class="swiper-button-next"></div>
                </div>
            </div>
            <div class="row justify-content-around mt-5">
                <div class="col-7">
                    <div class="row">
                        <div class="col-12" >
                            <div style="color: #343C6A;font-size: 40px;">Add New Account</div>
                            <g:form controller="asset" action="save">
                                <div class="row justify-content-between">
                                    <div class="col-6">
                                        <div class="row">
                                            <div class="col-12">
                                                <div class="form-text">Account Name</div>
                                            </div>
                                            <div class="col-12">
                                                <input class="form-input" id="assetName" name="assetName" type="text" placeholder="Name" required/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-6">
                                        <div class="row">
                                            <div class="col-12">
                                                <div class="form-text">Account Number</div>
                                            </div>
                                            <div class="col-12">
                                                <input class="form-input" id="acctNo" name="acctNo" type="text" placeholder="###"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-6">
                                        <div class="row">
                                            <div class="col-12">
                                                <div class="form-text">Balance</div>
                                            </div>
                                            <div class="col-12">
                                                <input class="form-input" id="balance" name="balance" type="number" step="0.01" min="0" placeholder="0.00" required/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-6">
                                        <div class="row">
                                            <div class="col-12">
                                                <div class="form-text">Expiry Date</div>
                                            </div>
                                            <div class="col-12">
                                                <input class="form-input" type="date" id="expiryDate" name="expiryDate">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-12 mt-4 text-end">
                                        <g:submitButton id="addAccountButton" name="Add Account" value="Add Account" style="background-color: #0A42DA; border-radius: 10px; color: white; padding: 10px; font-size: 20px !important;"/>
                                    </div>
                                </div>
                            </g:form>
                        </div>
                        <div class="col-12 mt-5">
                            <g:render template="/components/savings_history" model="${['savingsBalanceHistory': savingsBalanceHistory]}"/>
                        </div>
                    </div>
                </div>
                <div class="col-4">
                    <g:render template="/components/recent_transactions"/>
                </div>
            </div>
        </div>

        <script>
        document.addEventListener('DOMContentLoaded', function () {
            var mySwiper = new Swiper('.swiper-container', {
                // Optional parameters
                direction: 'horizontal',
                loop: true,

                // If you need pagination
                pagination: {
                    el: '.swiper-pagination',
                },

                // Navigation arrows
                navigation: {
                    nextEl: '.swiper-button-next',
                    prevEl: '.swiper-button-prev',
                },

                navigation: {
                    nextEl: '.swiper-button-next',
                    prevEl: '.swiper-button-prev',
                },
                slidesPerView: 3,

                // And if we need scrollbar
            });
        });
        </script>
    </body>
</html>