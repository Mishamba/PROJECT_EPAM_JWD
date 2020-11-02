<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %><%--
  Created by IntelliJ IDEA.
  User: mishamba
  Date: 10/4/20
  Time: 5:17 PM
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
    <title><%=resourceBundle.getString("sign_up_sign")%></title>
</head>
<body>
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
<form action="/PROJECT_EPAM_JWD_war/university" method="post">
<p><%=resourceBundle.getString("first_name_sign")%></p>
    <label>
        <input type="text" name="first_name">
    </label>
    <br>
<p><%=resourceBundle.getString("last_name_sign")%></p>
    <label>
        <input type="text" name="last_name">
    </label>
<p><%=resourceBundle.getString("email_sign")%></p>
<label>
    <input type="email" name="email">
</label>
<br>
<p><%=resourceBundle.getString("password_sign")%></p>
<label>
    <input type="password" name="password">
</label>
<br>
<p><%=resourceBundle.getString("birthday_sign") + "(enter like YYYY-MM-DD)"%></p>
<label>
    <input type="text" name="birthday">
</label>
<br>
    <% if (request.getSession().getAttribute("role").equals("admin")) {%>
        <p><%=resourceBundle.getString("role_definer_sign")%></p>
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
    <input type="submit" value=<%=resourceBundle.getString("sign_up_sign")%>>
</form>
</body>
</html>
