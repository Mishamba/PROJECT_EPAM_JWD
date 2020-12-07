<%--
  Created by IntelliJ IDEA.
  User: mishamba
  Date: 10/19/20
  Time: 4:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="bt" uri="button-tags"%>
<%@taglib prefix="ft" uri="former-tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="sings.sign"/>
<html>
<head>
    <title><fmt:message key="kick_out_sign"/></title>
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
<br>
<h3><fmt:message key="student_info_sign"/></h3>
<br>
<jsp:useBean id="student" scope="request" type="com.mishamba.project.model.User"/>
<ft:user-info user="${student}"/>
<br>
<br>
<h2><fmt:message key="review_sign"/></h2>
<br>
<form action="${pageContext.request.contextPath}/university">
    <input type="hidden" name="command" value="kick_student_process">
    <input type="hidden" name="student_id" value=<%= request.getParameter("student_id")%>>
    <input type="hidden" name="course_id" value=<%=request.getParameter("course_id")%>>
    <p><fmt:message key="student_finished_course_successfully_sign"/></p>
    <br>
    <label>
        <input type="checkbox" checked name="finished">
    </label>
    <br>
    <p><fmt:message key="mark_sign"/></p>
    <br>
    <label>
        <input type="number" name="mark">
    </label>
    <p><fmt:message key="review_sign"/></p>
    <br>
    <label>
        <input type="text" name="review">
    </label>
    <br>
    <p><fmt:message key="got_certificate_sign"/></p>
    <br>
    <label>
        <input type="checkbox" checked name="got_certificate">
    </label>
    <br>
    <br>
    <input type="submit" value="Set review">
</form>
<br>
<br>
<bt:change-locale/>
</body>
</html>
