<%--
  Created by IntelliJ IDEA.
  User: mishamba
  Date: 10/4/20
  Time: 5:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sing Up</title>
</head>
<body>
<p>first name</p>
<form>
    <input type="text" name="first_name">
</form>
<br>
<p>last name</p>
<form>
    <input type="text" name="last_name">
</form>
<br>
<p>email</p>
<form>
    <input type="email" name="email">
</form>
<br>
<p>password</p>
<form>
    <input type="password" name="password">
</form>
<br>
<p>birthday (enter like YYYY-MM-DD)</p>
<form>
    <input type="text" name="birthday">
</form>
<br>
${role_definer}
<form action="/PROJECT_EPAM_JWD_war/university" method="post">
    <input type="hidden" name="command" value="sing_up_check">
    <input type="submit" value="Sign Up">
</form>
</body>
</html>
