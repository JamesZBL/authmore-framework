<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>用户登录 - Authmore 开放平台</title>
    <#include "./dist.ftl">
</head>
<body>
<#include "./header.ftl">
<div class="header text-center mb-5">
    <img class="logo" src="/logo.png" alt="Authmore">
    <h3 class="title">Authmore 开放平台</h3>
    <h4 class="mb-lg-3">用 户 登 录</h4>
</div>
<#if error?? >
    <div class="alert alert-danger" role="alert">
        ${error!}
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
</#if>
<form class="form-signin" action="/signin?from=${RequestParameters['from']!}" method="post">
    <input type="text" id="ui" name="ui" class="form-control" placeholder="用户名" required autofocus>
    <input type="password" id="uc" name="uc" class="form-control" placeholder="密 码" required>
    <div class="checkbox mt-3">
        <label>
            <input name="ur" type="checkbox" class="mr-2">记住我
        </label>
    </div>
    <button class="btn btn-primary btn-block" type="submit">登 录</button>
</form>
<#include "./footer.ftl">
</body>
</html>
