<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="/static/css/main.css" />
    <link rel="stylesheet" type="text/css" href="/static/css/login.css" />
    <link rel="shortcut icon" href="/static/favicon.ico" type="image/x-icon" />
    <script src="/static/js/jQuery.js"></script>
</head>

<body>
    <div class="container">
        <div class="form">
            <img class="logo" src="/static/logos/market.png" />
            <form method="POST" action="j_security_check">
                <div class="input-data">
                    <p>Login:</p>
                    <input class="input" name="j_username" type="text" />
                    <p>Password:</p>
                    <input class="input" name="j_password" type="password" />
                </div>
                <button class="navigation-buttons green-button button" type="submit">Sign in</button>
            </form>
            <form action="/pages/registration.jsp" method="GET">
                <button class="navigation-buttons green-button button" type="submit">Sign up</button>
            </form>
            <form action="/" method="GET">
                <button class="navigation-buttons green-button button" type="submit">Enter as guest</button>
            </form>
            <% if (request.getParameter("error") != null) {
                    out.println("<span class=\"error\">Bad Credentials!</span>");
                }
                if (request.getParameter("success") != null) {
                    out.println("<span class=\"success\">Registration was successfully!</span>");
                } 
                 %>
        </div>
    </div>
</body>

</html>