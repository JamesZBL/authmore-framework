<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Authmore Implicit</title>
    <script src="/webjars/jquery/3.4.1/jquery.min.js"></script>
</head>
<body>
<h1>跳转中...</h1>
</body>
</html>
<script>
    $(function () {
        var hash = location.hash.replace('#', '');
        var body = {};

        hash.split('&').map(function (value) {
            return value.split('=')
        }).forEach(function (value) {
            body[value[0]] = value[1];
        });

        setTimeout(function () {
            $.ajax({
                    url: "${callBackUri}",
                    type: "post",
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify(body),
                    success: function (r) {
                        document.write("<pre>" + JSON.stringify(r, null, 2) + "</pre>");
                    }
                }
            );
        }, 1000);
    });
</script>