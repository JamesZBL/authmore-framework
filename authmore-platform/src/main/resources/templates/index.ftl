<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>Authmore Authorization Center</title>
</head>
<body>
<h1>Authmore Authorization Center</h1>
<#if Session.current_user??>
    <h2>Hello, ${Session.current_user!}</h2>
<#else>
    <a href="/signin">Sign in</a>
</#if>
</body>
</html>