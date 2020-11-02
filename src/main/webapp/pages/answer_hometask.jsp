<%@ page import="com.mishamba.project.model.Hometask" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="ft" uri="former-tags"%>
<%@ taglib prefix="bt" uri="button-tags"%>
<% Locale locale = (Locale) request.getSession().getAttribute("locale");
    ResourceBundle resourceBundle = ResourceBundle.getBundle("signs.sign", locale);
%>
<html>
<head>
    <title><%=resourceBundle.getString("hometask")%></title>
</head>
<body>
<h3><%=resourceBundle.getString("menu_sign")%>></h3>
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
<h3><%=resourceBundle.getString("hometask_sign")%></h3>
<br>
<jsp:useBean id="hometask" scope="request" type="com.mishamba.project.model.Hometask"/>
<ft:hometask hometask="${hometask}"/>
<br>
<h3><%=resourceBundle.getString("answer_sign")%></h3>
<br>
<form action="${pageContext.request.contextPath}/hometask" method="get">
    <label>
        <input type="text" name="answer">
    </label>
    <input type="hidden" name="hometask_id" value=<%= request.getParameter("hometask_id")%>>
    <input type="hidden" name="command" value="enter_hometask_answer">
    <input type="submit" value=<%=resourceBundle.getString("send_answer_sign")%>>
</form>
</body>
</html>
