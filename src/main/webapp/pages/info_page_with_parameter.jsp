<%--
  Created by IntelliJ IDEA.
  User: mishamba
  Date: 10/7/20
  Time: 3:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Info page</title>
</head>
<body>
${info}
<form action="/PROJECT_EPAM_JWD_war/info">
    <input type="hidden" name="command" value="main_page">
    <input type="submit" value="Go to main page">
</form>
</body>
</html>
