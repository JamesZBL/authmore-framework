<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>访问授权 ${(client.clientName)!(client.clientId)} - Authmore</title>
    <#include "./dist.ftl">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-6 offset-3">
            <div class="card p-5">
                <div class="pl-5 pt-5 pr-5 pb-2">
                    <div class="text-left">
                        <img class="w-25" src="/app-icon.svg" alt="应用程序"/>
                    </div>
                    <h2 class="pb-3 pt-5"><b>你好, ${(Session.current_user)!'用户'} :) </b></h2>
                    <h5 class="pb-3">允许 <b>${(client.clientName)!(client.clientId)}</b> 访问你的数据吗？</h5>
                </div>
                <form id="id_form_opinion" action="/authorize/confirm" method="post">
                    <input id="id_input_opinion" type="hidden" name="opinion">
                    <input type="hidden" name="client_id" value="${client.clientId}">
                </form>
                <div class="pl-5 pr-5">
                    <#if scopes??>
                        <p class="mb-3">该应用将获取你如下数据的访问权限：</p>
                        <ul class="list-group">
                            <#list scopes as scope >
                                <li class="list-group-item">
                                    <input class="mr-2" type="checkbox" checked disabled> ${scope!}
                                </li>
                            </#list>
<#--                            <li class="list-group-item"></li>-->
                        </ul>
                    </#if>
                </div>
                <div class="row p-5">
                    <div class="col-md-6">
                        <button class="btn btn-danger form-control" onclick="allow(false);">拒绝授权</button>
                    </div>
                    <div class="col-md-6">
                        <button class="btn btn-primary form-control" onclick="allow(true);">同意授权</button>
                    </div>
                </div>
                <div class="text-center mt-5">
                    <p>Apache2 Licensed | Copyright © 2019 郑保乐</p>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    var allow = function (allow) {
        var opinion = allow ? "allow" : "deny";
        $('#id_input_opinion').val(opinion);
        $('#id_form_opinion').submit();
    };

    $(function () {
        setTimeout(function () {
            location.reload();
        }, 30 * 1000)
    });
</script>
</html>
