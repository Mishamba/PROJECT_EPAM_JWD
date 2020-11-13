<jsp:useBean id="course" scope="request" type="com.mishamba.project.model.Course"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="bt" uri="button-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="signs.sign"/>
<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="create_hometask_sign"/></title>
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
<form action="${pageContext.request.contextPath}/hometask">
<p><fmt:message key="enter_title_sign"/></p>
<br>
    <label>
        <input type="text" name="title">
    </label>
    <br>
<p><fmt:message key="enter_description_sign"/></p>
<br>
    <label>
        <input type="text" name="description">
    </label>
    <br>
<p><fmt:message key="enter_deadline_sign"/>" (YYYY-MM-DD)"></p>
    <label>
        <input type="text" name="deadline">
    </label>
    <input type="hidden" name="command" value="check_create_hometask">
    <input type="hidden" name="course_id" value="${course.id}">
    <input type="submit" value=<fmt:message key="send_hometask_sign"/>>
</form>
</body>
</html>