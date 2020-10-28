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
<html>
<head>
    <title>Profile</title>
</head>
<body>
<h3>Role</h3>
<br>
${user.role}
<br>
<h3>First Name</h3>
<br>
${user.firstName}
<br>
<h3>Last Name</h3>
<br>
${user.lastName}
<br>
<h3>Birthday</h3>
<br>
${user.birthday}
<br>
<h3>Email</h3>
<br>
${user.email}
<br>
<h3>Courses you participated</h3>
<br>
<% if (request.getSession().getAttribute("role").equals("teacher")) {%>
<form action="/PROJECT_EPAM_JWD_war/active_courses">
    <input type="hidden" name="command" value="user_courses">
    <input type="hidden" name="finished" value="false">
    <input type="submit" value="Managing courses">
</form>
<br>
<form action="/PROJECT_EPAM_JWD_war/passed_courses">
    <input type="hidden" name="command" value="user_courses">
    <input type="hidden" name="finished" value="true">
    <input type="submit" value="Managed courses">
</form>
<br>
<%} else if (request.getSession().getAttribute("role").equals("student")) {%>
<form action="/PROJECT_EPAM_JWD_war/active_courses">
    <input type="hidden" name="command" value="user_courses">
    <input type="hidden" name="finished" value="false">
    <input type="submit" value="Active courses">
</form>
<br>
<form action="/PROJECT_EPAM_JWD_war/passed_courses">
    <input type="hidden" name="command" value="user_courses">
    <input type="hidden" name="finished" value="true">
    <input type="submit" value="Passed courses">
</form>
<br>
<%}%>
<br>
<h3>Menu</h3>
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
</body>
</html>
