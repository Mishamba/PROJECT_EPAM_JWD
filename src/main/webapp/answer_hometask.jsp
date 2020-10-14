<%--
  Created by IntelliJ IDEA.
  User: mishamba
  Date: 3.10.20
  Time: 23:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hometask</title>
</head>
<body>
<h3>Menu</h3>
<br>
${menu}
<br>
<h3>Hometask</h3>
<br>
${hometask}
<br>
<h3>Answer</h3>
<br>
<form action="/PROJECT_EPAM_JWD_war/hometask" method="get">
    <label>
        <input type="text" name="answer">
    </label>
    <input type="hidden" name="hometask_id" value=<%= request.getParameter("hometask_id")%>>
    <input type="hidden" name="command" value="enter_hometask_answer">
    <input type="submit" value="Send answer">
</form>
</body>
</html>
