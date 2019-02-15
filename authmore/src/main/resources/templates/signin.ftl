<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Authmore Sign in</title>
</head>
<body>
<h1>Please Sign in</h1>
<h4>${error!}</h4>
<form action="/signin?from=${RequestParameters['from']!}" method="post">
    <label for="ui">Username</label>
    <input id="ui" type="text" name="ui" title="Username"><br>
    <label for="uc">Password</label>
    <input id="uc" type="password" name="uc" title="Password"><br>
    <input type="submit" value="Sign In">
</form>
</body>
</html>