<%--
  Created by IntelliJ IDEA.
  User: mishamba
  Date: 10/6/20
  Time: 12:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="bt" uri="button-tags"%>
<%@taglib prefix="ft" uri="former-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="signs.sign"/>
<html>
<head>
    <title><fmt:message key="course_sign"/></title>
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
<h3><fmt:message key="course_info_sign"/></h3>
<br>
<jsp:useBean id="course" scope="request" type="com.mishamba.project.model.Course"/>
<jsp:useBean id="students_on_course_quantity" scope="request" type="java.lang.Integer"/>
<ft:course-info course="${course}" studentsOnCourseQuantity="${students_on_course_quantity}"/>

<br>
<c:if test="${sessionScope.role eq 'teacher' and !requestScope.participated}">
<bt:append-teacher-on-course courseId="${course.id}"/>
<br>
</c:if>
<c:if test="${sessionScope.role eq 'teacher'}">
<bt:course-hometask courseId="${course.id}"/>
<br>
<bt:create-hometask courseId="${course.id}"/>
<br>
<bt:students-list courseId="${course.id}"/>
<br>
<bt:finish-course courseId="${course.id}"/>
<br>
</c:if>
<c:if test="${sessionScope.role eq 'student' and requestScope.participated}">
<bt:course-hometask courseId="${course.id}"/>
<br>
</c:if>
<c:if test="${sessionScope.role eq 'student' and not requestScope.participated}">
<bt:student-sign-up-form-course courseId="${course.id}"/>
<br>
</c:if>
<br>
<bt:change-locale/>
</body>
</html>
