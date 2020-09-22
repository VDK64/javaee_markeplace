<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Items</title>
    <link rel="stylesheet" type="text/css" href="/static/css/items.css"/>
    <link rel="stylesheet" type="text/css" href="/static/css/main.css"/>
    <link rel="stylesheet" type="text/css" href="/static/css/header.css"/>
    <script src="/static/js/jQuery.js"></script>
    <script src="/static/js/items.js"></script>
    <!-- <link rel="shortcut icon" href="/static/favicon.ico" type="image/x-icon"/> -->
</head>

<body>
    <input class="principal" type="hidden" value="<%= request.getRemoteUser() %>" />
    <header>
        <div class="header sticky">
            <span class="navigation">
                <a class="items-link" href="/myItems">My Items</a>
            </span>
            <span class="navigation">
                <a class="items-link " href="/item">Create Item</a>
            </span>
            <span class="logo"><img src="/static/logos/market.png" alt=""></span>
            <span class="user-info">
                <% if (request.getRemoteUser() != null) {
                    out.println("<form class=\"user-info\" action=\"/showItems\" method=\"POST\">You logged as: " + request.getRemoteUser() + "<button class=\"logout red-button button\" type=\"submit\">Logout</button></form>");
                } else {
                    out.println("<form action=\"/pages/login.jsp\" method=\"GET\"><button class=\"login green-button button\" type=\"submit\">Login</button></form>");
                } %>
            </span>            
        </div>
    </header>
    <div class="flex-container content">
        <img class="spinner" src="/static/logos/hammer.gif" />
    </div>
</body>

</html>