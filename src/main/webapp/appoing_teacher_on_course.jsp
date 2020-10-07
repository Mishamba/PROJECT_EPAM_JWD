<%--
  Created by IntelliJ IDEA.
  User: mishamba
  Date: 10/7/20
  Time: 10:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Appoint page</title>
</head>
<body>
${free_teachers}
    <form action="/PROJECT_EPAM_JWD_war/appoint" method="get">
        <label>
            <input type="number" name="teacher_id">
        </label>
        <input type="hidden" name="command" value="appoint_teacher">
        <input type="submit" value="Appoint">
    </form>
</body>
</html>
