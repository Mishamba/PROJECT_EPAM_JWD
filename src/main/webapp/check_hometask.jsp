<%--
  Created by IntelliJ IDEA.
  User: mishamba
  Date: 10/4/20
  Time: 6:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Check hometask</title>
</head>
<body>
<h3>Menu</h3>
<br>
${menu}
<br>
<h3>Hometask data</h3>
<br>
${hometask_data}
<br>
<form action="\PROJECT_EPAM_JWD_war/hometask">
    <label>
        <input type="number" name="mark">
    </label>
    <br>
    <input type="hidden" name="command" value="set_hometask_mark">
    <input type="hidden" name="student_id" value=<%=request.getParameter("student_id")%>>
    <input type="hidden" name="hometask_id" value=<%=request.getParameter("hometask_id")%>>
    <input type="submit" value="Set mark">
</form>
</body>
</html>
