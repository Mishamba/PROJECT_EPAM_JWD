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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="signs.sign"/>
<html>
<head>
    <title><fmt:message key="courses_catalog_sign"/></title>
</head>
<body>
<h2><fmt:message key="user_info_sign"/></h2>
<br>
<jsp:useBean id="user" scope="request" type="java.util.Optional"/>
<c:if test="${user.isPresent()}">
    <ft:user-info user="${user}"/>
</c:if>
<br>

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

<h1><fmt:message key="our_courses_sign"/></h1>
<jsp:useBean id="courses" scope="request" type="java.util.List"/>
<c:forEach var="course" items="${courses}">
    <ft:course-list course="${course}"/>
</c:forEach>
<br>
<br>
<bt:change-locale/>
</body>
</html>
