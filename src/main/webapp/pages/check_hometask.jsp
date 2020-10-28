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
<html>
<head>
    <title>Check hometask</title>
</head>
<body>
<h3>Menu</h3>
<br>

<%-- forming menu --%>
<% String role = (String) request.getSession().getAttribute("role"); %>
<% if ()
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

<h3>Hometask data</h3>
<br>
<jsp:useBean id="hometask" scope="request" type="com.mishamba.project.model.Hometask"/>
<ft:hometask-for-check hometask="${hometask}"/>
<br>
<form action="${pageContext.request.contextPath}/hometask">
    <label>
        <input type="number" name="mark">
    </label>
    <br>
    <input type="hidden" name="command" value="set_hometask_mark">
    <input type="hidden" name="student_id" value=<%=request.getParameter("student_id")%>>
    <input type="hidden" name="hometask_id" value=<%=request.getParameter("hometask_id")%>>
    <input type="submit" value="Set mark">
</form>
</body>
</html>
