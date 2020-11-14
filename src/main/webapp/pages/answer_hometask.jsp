<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="ft" uri="former-tags"%>
<%@ taglib prefix="bt" uri="button-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="signs.sign"/>
<html>
<head>
    <title><fmt:message key="hometask_sign"/></title>
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
<h3><fmt:message key="hometask_sign"/></h3>
<br>
<jsp:useBean id="hometask" scope="request" type="com.mishamba.project.model.Hometask"/>
<ft:hometask hometask="${hometask}"/>
<br>
<h3><fmt:message key="answer_sign"/></h3>
<br>
<form action="${pageContext.request.contextPath}/hometask" method="get">
    <label>
        <input type="text" name="answer">
    </label>
    <input type="hidden" name="hometask_id" value=<%= request.getParameter("hometask_id")%>>
    <input type="hidden" name="command" value="enter_hometask_answer">
    <input type="submit" value=<fmt:message key="send_answer_sign"/>>
</form>
<br>
<br>
<bt:change-locale/>
</body>
</html>
