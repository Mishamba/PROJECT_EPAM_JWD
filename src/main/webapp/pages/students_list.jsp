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
<html>
<head>
    <title>Students list</title>
</head>
<body>
<h3>Course name</h3>
<br>
${course.id}
<br>
<h3>Students list</h3>
<br>
<jsp:useBean id="students" scope="request" type="java.util.List"/>
<c:forEach var="student" items="${students}">
    <ft:user-info user="${student}"/>
</c:forEach>
</body>
</html>