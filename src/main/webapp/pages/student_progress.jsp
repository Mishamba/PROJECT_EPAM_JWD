<%@taglib prefix="bt" uri="button-tags"%>
<%@taglib prefix="ft" uri="former-tags"%>
<%--
  Created by IntelliJ IDEA.
  User: mishamba
  Date: 10/14/20
  Time: 7:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="signs.sign"/>
<html>
<head>
    <title><fmt:message key="student_progress_sign"/></title>
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
<h3><fmt:message key="student_info_sign"/></h3>
<br>
<jsp:useBean id="student" scope="request" type="com.mishamba.project.model.User"/>
<ft:user-info user="${student}"/>
<br>
<h3><fmt:message key="hometasks_sign"/></h3>
<br>
<jsp:useBean id="hometasks" scope="request" type="java.util.List"/>
<c:forEach var="hometask" items="${hometasks}">
    <ft:hometask hometask="${hometask}"/>
    <br>
    <c:choose>
        <c:when test="${not empty hometask.response && hometask.response.mark == 0}">
            <bt:check-hometask hometaskId="${hometask.id}" studentId="${student.id}"/>
        </c:when>
        <c:when test="${not empty hometask.response && hometask.response.mark > 0}">
            <ft:hometask-response hometask="${hometask}"/>
        </c:when>
    </c:choose>
</c:forEach>
<br>
<br>
<form action="${pageContext.request.contextPath}/university">
    <input type="hidden" name="command" value="kick_student_page">
    <input type="hidden" name="course_id" value=<%=request.getParameter("course_id")%>>
    <input type="hidden" name="student_id" value=<%=request.getParameter("student_id")%>>
    <input type="submit" value=<fmt:message key="kick_out_sign"/>>
</form>
<br>
<br>
<bt:change-locale/>
</body>
</html>
