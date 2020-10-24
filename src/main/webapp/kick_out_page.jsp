<%--
  Created by IntelliJ IDEA.
  User: mishamba
  Date: 10/19/20
  Time: 4:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="bt" uri="button-tags"%>
<%@taglib prefix="ft" uri="former-tags"%>
<html>
<head>
    <title>Kick out</title>
</head>
<body>
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
<br>
<h3>Student info</h3>
<br>
<ft:user-info user="user"/>
<br>
<br>
<h2>Review</h2>
<br>
<form action="/PROJECT_EPAM_JWD_war/university">
    <input type="hidden" name="command" value="kick_student_process">
    <input type="hidden" name="student_id" value=<%= request.getParameter("student_id")%>>
    <input type="hidden" name="course_id" value=<%=request.getParameter("course_id")%>>
    <input type="hidden" name="teacher_id" value=<%= request.getSession().getAttribute("teacher_id")%>>
    <p>Student finished course fully</p>
    <br>
    <label>
        <input type="checkbox" checked name="finished">
    </label>
    <br>
    <p>Mark</p>
    <br>
    <label>
        <input type="number" name="mark">
    </label>
    <p>Review</p>
    <br>
    <label>
        <input type="text" name="review">
    </label>
    <br>
    <p>Got certificate</p>
    <br>
    <label>
        <input type="checkbox" checked name="get_certificate">
    </label>
    <br>
    <br>
    <input type="submit" value="Set review">
</form>
</body>
</html>
