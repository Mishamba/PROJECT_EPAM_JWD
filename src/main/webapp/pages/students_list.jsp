<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>
<%@taglib prefix="ft" uri="former-tags"%>
<jsp:useBean id="course" scope="request" type="com.mishamba.project.model.Course"/>
<%--
  Created by IntelliJ IDEA.
  User: mishamba
  Date: 10/4/20
  Time: 3:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Locale locale = (Locale) request.getSession().getAttribute("locale");
    ResourceBundle resourceBundle = ResourceBundle.getBundle("signs.sign", locale);
%>
<html>
<head>
    <title><%=resourceBundle.getString("students_list_sign")%></title>
</head>
<body>
<h3><%=resourceBundle.getString("course_name_sign")%></h3>
<br>
${course.courseName}
<br>
<h3><%=resourceBundle.getString("students_sign")%></h3>
<br>
<jsp:useBean id="students" scope="request" type="java.util.List"/>
<c:forEach var="student" items="${students}">
    <ft:user-info user="${student}"/>
</c:forEach>
</body>
</html>
