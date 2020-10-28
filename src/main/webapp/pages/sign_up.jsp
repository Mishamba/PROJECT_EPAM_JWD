<%--
  Created by IntelliJ IDEA.
  User: mishamba
  Date: 10/4/20
  Time: 5:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="bt" uri="button-tags"%>
<html>
<head>
    <title>Sign Up</title>
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
<form action="/PROJECT_EPAM_JWD_war/university" method="post">
<p>first name</p>
    <label>
        <input type="text" name="first_name">
    </label>
    <br>
<p>last name</p>
    <label>
        <input type="text" name="last_name">
    </label>
<p>email</p>
<label>
    <input type="email" name="email">
</label>
<br>
<p>password</p>
<label>
    <input type="password" name="password">
</label>
<br>
<p>birthday (enter like YYYY-MM-DD)</p>
<label>
    <input type="text" name="birthday">
</label>
<br>
    <% if (request.getSession().getAttribute("role").equals("admin")) {%>
        <p>Role definer</p>
        <br>
        <label>
            <input type="text" name="role">
        </label>
        <br>
    <%} else {%>
        <input type="hidden" name="role" value="student">
        <br>
    <%}%>
<br>
    <input type="hidden" name="command" value="sign_up_check">
    <input type="submit" value="Sign Up">
</form>
</body>
</html>
