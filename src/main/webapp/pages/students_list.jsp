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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bt" uri="button-tags" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="signs.sign"/>
<html>
<head>
    <title><fmt:message key="students_list_sign"/></title>
    <style>
        h2 {
            font-size: 28px;
            font-family: 'Courier New', Courier, monospace;
            font-weight: bold;
            color: #051C0E;
        }

        h3 {
            font-size: 28px;
            font-family: 'Courier New', Courier, monospace;
            font-weight: bold;
            color: #051C0E;
        }

        h2 form input {
            background-color: #8aa54d;
            height: 60px;
            width: 140px;
            font-size: 20px;
            font-family: 'Courier New', Courier, monospace;
            font-weight: bold;
            color: #051C0E;
            border: 2px solid rgb(76, 102, 45);
            border-radius: 5px;
            position: relative;
        }

        form input {
            background-color: #8aa54d;
            height: 60px;
            width: 140px;
            font-size: 20px;
            font-family: 'Courier New', Courier, monospace;
            font-weight: bold;
            color: #051C0E;
            border: 2px solid rgb(76, 102, 45);
            border-radius: 5px;
            position: relative;
        }
    </style>
</head>
<body>
<h3><fmt:message key="course_name_sign"/></h3>
<br>
${course.courseName}
<br>
<h3><fmt:message key="students_sign"/></h3>
<br>
<jsp:useBean id="students" scope="request" type="java.util.List"/>
<c:forEach var="student" items="${students}">
    <ft:user-info user="${student}"/>
    <br>
    <bt:student-progress studentId="${student.id}" courseId="${course.id}"/>
</c:forEach>
<br>
<br>
<bt:change-locale/>
</body>
</html>
