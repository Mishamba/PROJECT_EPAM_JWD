<%--
  Created by IntelliJ IDEA.
  User: mishamba
  Date: 30.09.20
  Time: 22:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="ft" uri="former-tags"%>
<%@taglib prefix="bt" uri="button-tags"%>
<html>
<head>
    <title>Courses Catalog</title>
</head>
<body>
<h2>User Info</h2>
<br>
<jsp:useBean id="user" scope="request" type="com.mishamba.project.model.User"/>
<ft:user-info user="${user}"/>
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

<h1>Our courses:</h1>
<jsp:useBean id="courses" scope="request" type="java.util.List"/>
<c:forEach var="course" items="${courses}">
    <ft:course-list course="${course}"/>
</c:forEach>
</body>
</html>
