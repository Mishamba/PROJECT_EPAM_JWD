<jsp:useBean id="user" scope="request" type="com.mishamba.project.model.User"/>
<%--
  Created by IntelliJ IDEA.
  User: mishamba
  Date: 10/4/20
  Time: 3:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="bt" uri="button-tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title><fmt:message key="profile_sign"/></title>
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
<h3><fmt:message key="role_sign"/></h3>
<br>
${user.role}
<br>
<h3><fmt:message key="first_name_sign"/></h3>
<br>
${user.firstName}
<br>
<h3><fmt:message key="last_name_sign"/></h3>
<br>
${user.lastName}
<br>
<h3><fmt:message key="birthday_sign"/></h3>
<br>
${user.birthday}
<br>
<h3>Email</h3>
<br>
${user.email}
<br>
<h3><fmt:message key="courses_you_participated_sign"/></h3>
<br>
<% if (request.getSession().getAttribute("role").equals("teacher")) {%>
<form action="${pageContext.request.contextPath}/active_courses">
    <input type="hidden" name="command" value="user_courses">
    <input type="hidden" name="finished" value="false">
    <input type="submit" value=<fmt:message key="managing_courses_sign"/>>
</form>
<br>
<form action="${pageContext.request.contextPath}/passed_courses">
    <input type="hidden" name="command" value="user_courses">
    <input type="hidden" name="finished" value="true">
    <input type="submit" value=<fmt:message key="managed_courses_sign"/>>
</form>
<br>
<%} else if (request.getSession().getAttribute("role").equals("student")) {%>
<form action="${pageContext.request.contextPath}/active_courses">
    <input type="hidden" name="command" value="user_courses">
    <input type="hidden" name="finished" value="false">
    <input type="submit" value=<fmt:message key="active_courses_sign"/>>
</form>
<br>
<form action="${pageContext.request.contextPath}/passed_courses">
    <input type="hidden" name="command" value="user_courses">
    <input type="hidden" name="finished" value="true">
    <input type="submit" value=<fmt:message key="passed_courses_sign"/>>
</form>
<br>
<%}%>
<br>
<h3><fmt:message key="menu_sign"/></h3>
<br>

<%-- forming menu --%>
<% String role = (String) request.getSession().getAttribute("role"); %>
<% if (role == null)
    role = "anonym";
%>
<% switch (role) {
    case "anonym": %>
<bt:course-catalog/>
<bt:main-page/>
<%
        break;
    case "teacher":
    case "student":
%>
<bt:main-page/>
<bt:course-catalog/>
<bt:user-profile/>
<bt:sign-out/>
<%
        break;
    case "admin":
%>
<bt:main-page/>
<bt:sign-out/>
<bt:user-profile/>
<bt:create-user/>
<%
            break;
    }
%>
<br>
<br>
<bt:change-locale/>
</body>
</html>
