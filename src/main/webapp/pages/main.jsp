<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="bt" uri="button-tags"%>
<%@taglib prefix="ft" uri="former-tags"%>
<html>
<head>
    <title>My University</title>
</head>
<body>
<h2>User info</h2>
<br>
<% if (request.getSession().getAttribute("id") != null) {%>
    <jsp:useBean id="user" scope="request" type="com.mishamba.project.model.User"/>
    <ft:user-info user="${user}"/>
<br>
<%} else {%>
<bt:sign-in/>
<br>
<bt:sign-up/>
<br>
<%}%>
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
<h2>Course Add</h2>
<br>
<c:forEach var="course" items="course_add">
    <ft:course-list course="${course}"/>
</c:forEach>
</body>
</html>