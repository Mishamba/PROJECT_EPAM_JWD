<%--
  Created by IntelliJ IDEA.
  User: mishamba
  Date: 10/4/20
  Time: 6:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="ft" uri="former-tags"%>
<%@taglib prefix="bt" uri="button-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="signs.sign"/>
<html>
<head>
    <title><fmt:message key="check_hometask_sign"/></title>
    <style>
        h2 {
            font-size: 28px;
            font-family: 'Courier New', Courier, monospace;
            font-weight: bold;
            color: #051C0E;
        }

        h3 {
            font-size: 28px;
            font-family: 'Courier New', Courier, monospace;
            font-weight: bold;
            color: #051C0E;
        }

        h2 form input {
            background-color: #8aa54d;
            height: 60px;
            width: 140px;
            font-size: 20px;
            font-family: 'Courier New', Courier, monospace;
            font-weight: bold;
            color: #051C0E;
            border: 2px solid rgb(76, 102, 45);
            border-radius: 5px;
            position: relative;
        }

        form input {
            background-color: #8aa54d;
            height: 60px;
            width: 140px;
            font-size: 20px;
            font-family: 'Courier New', Courier, monospace;
            font-weight: bold;
            color: #051C0E;
            border: 2px solid rgb(76, 102, 45);
            border-radius: 5px;
            position: relative;
        }
    </style>
</head>
<body>
<h3><fmt:message key="menu_sign"/></h3>
<br>

<%-- forming menu --%>
<% String role = (String) request.getSession().getAttribute("role"); %>
<% if (role == null) {
    role = "anonym";
}%>
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

<h3><fmt:message key="hometask_data_sign"/></h3>
<br>
<jsp:useBean id="hometask" scope="request" type="com.mishamba.project.model.Hometask"/>
<ft:hometask hometask="${hometask}"/>
<ft:hometask-answer hometaskResponse="${hometask.response}"/>
<br>
<form action="${pageContext.request.contextPath}/hometask">
    <label>
        <input type="number" name="mark">
    </label>
    <br>
    <input type="hidden" name="command" value="set_hometask_mark">
    <input type="hidden" name="student_id" value=<%=request.getParameter("student_id")%>>
    <input type="hidden" name="hometask_id" value=<%=request.getParameter("hometask_id")%>>
    <input type="submit" value=<fmt:message key="set_mark_sign"/>>
</form>
<br>
<br>
<bt:change-locale/>
</body>
</html>
