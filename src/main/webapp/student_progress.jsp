<%@ page import="com.mishamba.project.service.CustomServiceFactory" %>
<jsp:useBean id="hometask" scope="request" type="java.lang.String"/>
<jsp:useBean id="menu" scope="request" type="java.lang.String"/>
<jsp:useBean id="student_info" scope="request" type="java.lang.String"/>
<%--
  Created by IntelliJ IDEA.
  User: mishamba
  Date: 10/14/20
  Time: 7:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Student progress</title>
</head>
<body>
<h3>Menu</h3>
<br>
${menu}
<br>
<h3>Student info</h3>
<br>
${student_info}
<br>
<h3>Hometasks</h3>
<br>
${hometask}
<br>
<br>
<form action="/PROJECT_EPAM_JWD_war">
    <input type="hidden" name="command" value="deducate_student">
    <input type="hidden" name="course_id" value=<%= request.getParameter("course_id")%>>
    <input type="hidden" name="student_id" value=<%=request.getParameter("student_id")%>>
    <input type="submit" value="Kick Out">
</form>
</body>
</html>
