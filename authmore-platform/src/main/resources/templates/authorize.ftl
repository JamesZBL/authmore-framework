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
                <div class="p-5">
                    <h2 class="pb-3 pt-5"><b>你好, ${(Session.current_user)!'用户'} :) </b></h2>
                    <h5 class="pb-3">允许应用 <b>${(client.clientName)!(client.clientId)}</b> 访问你的数据吗？</h5>
                </div>
                <form id="id_form_opinion" action="/authorize/confirm" method="post">
                    <input id="id_input_opinion" type="hidden" name="opinion">
                    <input type="hidden" name="client_id" value="${client.clientId}">
                </form>
                <div class="card-body p-5">
                    <button class="btn btn-success form-control" onclick="allow(true);">允许访问</button>
                    <button class="btn btn-danger form-control" onclick="allow(false);">拒绝访问</button>
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
