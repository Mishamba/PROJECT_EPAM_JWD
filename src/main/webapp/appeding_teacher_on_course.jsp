<jsp:useBean id="teachers" scope="request" type="com.mishamba.project.model.User"/>
<%@ page import="com.mishamba.project.model.User" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: mishamba
  Date: 10/7/20
  Time: 10:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="ft" uri="former-tags"%>
<%@taglib prefix="bt" uri="button-tags"%>
<html>
<head>
    <title>Appoint page</title>
</head>
<body>
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

<h3>Teachers list</h3>
<br>
<c:forEach var="teacher" items="${teachers}">
    <ft:teacher-for-list teacher="${teacher}"/>
</c:forEach>
    <form action="${pageContext.request.contextPath}/appoint" method="get">
        <label>
            <input type="number" name="teacher_id">
        </label>
        <input type="hidden" name="command" value="appoint_teacher">
        <input type="submit" value="Appoint">
    </form>
</body>
</html>
