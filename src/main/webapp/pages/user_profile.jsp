<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
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
<%
    Locale locale = (Locale) request.getSession().getAttribute("locale");
    ResourceBundle resourceBundle = ResourceBundle.getBundle("signs.sign", locale);
%>
<html>
<head>
    <title><%=resourceBundle.getString("profile_sign")%></title>
</head>
<body>
<h3><%=resourceBundle.getString("role_sign")%></h3>
<br>
${user.role}
<br>
<h3><%=resourceBundle.getString("first_name_sign")%></h3>
<br>
${user.firstName}
<br>
<h3><%=resourceBundle.getString("last_name_sign")%></h3>
<br>
${user.lastName}
<br>
<h3><%=resourceBundle.getString("birthday_sign")%></h3>
<br>
${user.birthday}
<br>
<h3>Email</h3>
<br>
${user.email}
<br>
<h3><%=resourceBundle.getString("courses_you_participated_sign")%></h3>
<br>
<% if (request.getSession().getAttribute("role").equals("teacher")) {%>
<form action="/PROJECT_EPAM_JWD_war/active_courses">
    <input type="hidden" name="command" value="user_courses">
    <input type="hidden" name="finished" value="false">
    <input type="submit" value=<%=resourceBundle.getString("managing_courses_sign")%>>
</form>
<br>
<form action="/PROJECT_EPAM_JWD_war/passed_courses">
    <input type="hidden" name="command" value="user_courses">
    <input type="hidden" name="finished" value="true">
    <input type="submit" value=<%=resourceBundle.getString("managed_courses_sign")%>>
</form>
<br>
<%} else if (request.getSession().getAttribute("role").equals("student")) {%>
<form action="/PROJECT_EPAM_JWD_war/active_courses">
    <input type="hidden" name="command" value="user_courses">
    <input type="hidden" name="finished" value="false">
    <input type="submit" value=<%=resourceBundle.getString("active_courses_sign")%>>
</form>
<br>
<form action="/PROJECT_EPAM_JWD_war/passed_courses">
    <input type="hidden" name="command" value="user_courses">
    <input type="hidden" name="finished" value="true">
    <input type="submit" value=<%=resourceBundle.getString("passed_courses_sign")%>>
</form>
<br>
<%}%>
<br>
<h3><%=resourceBundle.getString("menu_sign")%></h3>
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
