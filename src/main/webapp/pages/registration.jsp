<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Registration</title>
    <link rel="stylesheet" type="text/css" href="/static/css/main.css"/>
    <link rel="stylesheet" type="text/css" href="/static/css/registration.css"/>
    <script src="/static/js/jQuery.js"></script>
    <script src="/static/js/registration.js"></script>
    <link rel="shortcut icon" href="../favicon.ico" type="image/x-icon"/>
</head>

<body>
    <div class="container">
        <div class="form">
            <form method="post" action="/register">
                <div class="input-data">
                    <p>All fields are required!</p>
                    <p>Firstname:</p>
                    <input class="input" name="firstname" value="${userDto.firstName}" type="text" maxlength="20" onkeyup="checkFristName()" required/>
                    <p>Lastname:</p>
                    <input class="input" name="lastname" value="${userDto.lastName}" type="text" maxlength="20" onkeyup="checkLastName()" required/>
                    <p>Gender:</p>
                    <input class="input" name="gender" value="MALE" type="radio" ${userDto.maleGenderChecked}>MALE</input>
                    <input class="input" name="gender" value="FEMALE" type="radio" ${userDto.femaleGenderChecked}>FEMALE</input>
                    <p>email:</p>
                    <p class="info">On this email we will sent mail.</p>
                    <input class="input" name="email" value="${userDto.email}" type="email" onkeyup="checkEmail()" required/>
                    <p>Password:</p>
                    <p class="info">Minimum 6 symbols, max 15. One title, some digits and at least one symbol
                        like(!@#$%^&*).</p>
                    <input class="input" name="credentials" type="password" onkeyup="checkPasswordOnValid()" required/>
                    <p>Confirm password:</p>
                    <input class="input" name="confirmCredentials" type="password"
                        onkeyup="comparePasswordAndConfirmation()" required/>
                </div>
                <button class="navigation-buttons green-button button" type="submit" disabled>Sign in</button>
            </form>
            <form action="/" method="GET">
                <button class="navigation-buttons blue-button button" type="submit">Main page</button>
            </form>
            <%
            if (request.getParameter("exists") != null) {
                out.println("<span class=\"error\">User with this email already exists!</span>");
            }
            %>
        </div>
    </div>
</body>

</html>