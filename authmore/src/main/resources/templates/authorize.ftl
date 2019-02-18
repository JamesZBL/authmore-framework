<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sign in into ${(client.clientName)!(client.clientId)}</title>
</head>
<body>
<h3>你好 <b>${(Session.current_user)!'用户'}</b>，允许应用${(client.clientName)!(client.clientId)}访问你的信息吗？</h3>
<button>Cancel</button>
<button>Allow</button>
</body>
</html>