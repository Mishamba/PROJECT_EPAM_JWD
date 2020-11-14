<%--
  Created by IntelliJ IDEA.
  User: mishamba
  Date: 2.10.20
  Time: 18:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="bt" uri="button-tags"%>
<%@taglib prefix="ft" uri="former-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="signs.sign"/>
<html>
<head>
    <title><fmt:message key="my_hometasks_sign"/></title>
</head>
<body>
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
<h2><fmt:message key="hometasks_on_course_sign"/></h2>
<br>
<jsp:useBean id="hometasks" scope="request" type="com.mishamba.project.model.Hometask"/>
<c:forEach var="hometask" items="${hometasks}">
    <ft:hometask-for-list hometask="${hometask}"/>
    <br>
</c:forEach>
<br>
<br>
<bt:change-locale/>
</body>
</html>
