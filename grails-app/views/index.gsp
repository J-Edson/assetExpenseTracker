<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Asset Expense Log</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
    <g:render template="components/navbar"/>
    <div class="indexWrapper"> 
        <div class="row g-0 " >
            <div class="col-2">
                <g:render template="components/sidebar"/>
            </div>
            <div class="col" style="background-color:#F5F7FA">
                <div id="controllers" role="navigation">
                    <h2>Available Controllers:</h2>
                    <ul>
                        <g:each var="c" in="${grailsApplication.controllerClasses.sort { it.fullName } }">
                            <li class="controller">
                                <g:link controller="${c.logicalPropertyName}">${c.fullName}</g:link>
                            </li>
                        </g:each>
                    </ul>
                </div>
                <g:form controller="logout" action="" method="post">
                    <g:submitButton name="Logout" value="Logout"/>
                </g:form>
            </div>
        </div>
    </div>
</body>
</html>
