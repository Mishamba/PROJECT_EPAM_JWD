<%--
  Created by IntelliJ IDEA.
  User: mishamba
  Date: 10/6/20
  Time: 12:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="bt" uri="button-tags"%>
<%@taglib prefix="ft" uri="former-tags"%>
<html>
<head>
    <title>Course</title>
</head>
<body>
<h3>Menu</h3>
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
<h3>Course info</h3>
<br>
<jsp:useBean id="course" scope="request" type="com.mishamba.project.model.Course"/>
<jsp:useBean id="students_on_course_quantity" scope="request" type="java.lang.Integer"/>
<% boolean participation = (boolean) request.getAttribute("participation"); %>
<ft:course-info course="${course}" studentsOnCourseQuantity="${students_on_course_quantity}"/>
<br>
<% if (role.equals("teacher") && course.getTeacher() == null) {%>
<bt:append-teacher-on-course courseId="${course.id}"/>
<br>
<%}%>
<% if (role.equals("teacher") && participation) {%>
<bt:course-hometask courseId="${course.id}"/>
<br>
<bt:create-hometask courseId="${course.id}"/>
<br>
<bt:students-list courseId="${course.id}"/>
<br>
<bt:finish-course courseId="${course.id}"/>
<br>
<%}%>
<% if (role.equals("student") && participation) {%>
<bt:course-hometask courseId="${course.id}"/>
<br>
<%} else if (role.equals("student")) {%>
<bt:student-sign-up-form-course courseId="${course.id}"/>
<br>
<%}%>
<
</body>
</html>