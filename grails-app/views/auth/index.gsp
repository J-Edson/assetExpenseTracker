<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
</head>
<body>
    <a href="/">
        <button>Homepage</button>
    </a>
    <g:form controller="auth" action="register">
        <input id="username" name="username" type="text" placeholder="username" required/><br>
        <input id="password" name="password" type="text" placeholder="password" required/><br>
        <g:submitButton name="Register" value="Register"/>
    </g:form>
</body>
</html>