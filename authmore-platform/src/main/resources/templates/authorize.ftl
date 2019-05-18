<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sign in into ${(client.clientName)!(client.clientId)} - Authmore</title>
    <script src="/webjars/jquery/3.4.1/jquery.min.js"></script>
</head>
<body>
<h3>你好 <b>${(Session.current_user)!'用户'}</b>，允许应用${(client.clientName)!(client.clientId)}访问你的信息吗？</h3>
<form id="id_form_opinion" action="/authorize/confirm" method="post">
    <input id="id_input_opinion" type="hidden" name="opinion">
    <input type="hidden" name="client_id" value="${client.clientId}">
</form>
<button onclick="allow(false);">Cancel</button>
<button onclick="allow(true);">Allow</button>
</body>
</html>
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