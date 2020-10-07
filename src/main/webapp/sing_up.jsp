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
<form action="/PROJECT_EPAM_JWD_war/university" method="post">
<p>first name</p>
    <label>
        <input type="text" name="first_name">
    </label>
    <br>
<p>last name</p>
    <label>
        <input type="text" name="last_name">
    </label>
<p>email</p>
<label>
    <input type="email" name="email">
</label>
<br>
<p>password</p>
<label>
    <input type="password" name="password">
</label>
<br>
<p>birthday (enter like YYYY-MM-DD)</p>
<label>
    <input type="text" name="birthday">
</label>
<br>
${role_definer}
<br>
    <input type="hidden" name="command" value="sing_up_check">
    <input type="submit" value="Sign Up">
</form>
</body>
</html>
