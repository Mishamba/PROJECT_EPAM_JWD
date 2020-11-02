<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>
<jsp:useBean id="course" scope="request" type="com.mishamba.project.model.Course"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="bt" uri="button-tags"%>
<%Locale locale = (Locale) request.getSession().getAttribute("locale");
    ResourceBundle resourceBundle = ResourceBundle.getBundle("signs.sign", locale);
%>
<head>
    <meta charset="UTF-8">
    <title><%=resourceBundle.getString("create_hometask_sign")%></title>
</head>
<body>
<h3><%=resourceBundle.getString("menu_sign")%></h3>
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
<form action="/PROJECT_EPAM_JWD_war/hometask">
<p><%=resourceBundle.getString("enter_title_sign")%></p>
<br>
    <label>
        <input type="text" name="title">
    </label>
    <br>
<p><%=resourceBundle.getString("enter_description_sign")%></p>
<br>
    <label>
        <input type="text" name="description">
    </label>
    <br>
<p><%=resourceBundle.getString("enter_deadline_sign") + " (YYYY-MM-DD)"%></p>
    <label>
        <input type="text" name="deadline">
    </label>
    <input type="hidden" name="command" value="check_create_hometask">
    <input type="hidden" name="course_id" value="${course.id}">
    <input type="submit" value=<%=resourceBundle.getString("send_hometask_sign")%>>
</form>
</body>