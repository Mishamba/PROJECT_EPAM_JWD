<%@ page import="com.mishamba.project.service.ServiceFactory" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>
<%@taglib prefix="bt" uri="button-tags"%>
<%@taglib prefix="ft" uri="former-tags"%>
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
<%
    Locale locale = (Locale) request.getSession().getAttribute("locale");
    ResourceBundle resourceBundle = ResourceBundle.getBundle("signs.sign", locale);
%>
<html>
<head>
    <title><%=resourceBundle.getString("student_progress_sign")%></title>
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
<h3><%=resourceBundle.getString("student_info_sign")%></h3>
<br>
<jsp:useBean id="user" scope="request" type="com.mishamba.project.model.User"/>
<ft:user-info user="${user}"/>
<br>
<h3><%=resourceBundle.getString("hometasks_sign")%></h3>
<br>
<ft:hometask hometask="${hometask}"/>
<br>
<br>
<form action="/PROJECT_EPAM_JWD_war/university">
    <input type="hidden" name="command" value="kick_student_page">
    <input type="hidden" name="course_id" value=<%=request.getParameter("course_id")%>>
    <input type="hidden" name="student_id" value=<%=request.getParameter("student_id")%>>
    <input type="submit" value=<%=resourceBundle.getString("kick_out_sign")%>>
</form>
</body>
</html>
