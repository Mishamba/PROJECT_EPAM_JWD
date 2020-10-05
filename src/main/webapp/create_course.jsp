<%--
  Created by IntelliJ IDEA.
  User: mishamba
  Date: 10/4/20
  Time: 6:18 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create course</title>
</head>
<body>
<p>Course name</p>
<form>
    <input type="text", name="course_name">
</form>
<br>
<p>Begin of course (enter like yyyy-mm-dd)</p>
<form>
    <input type="text" name="begin_of_course">
</form>
<br>
<p>End of course (enter like yyyy-mm-dd)</p>
<form>
    <input type="text" name="end_of_course">
</form>
<br>
<p>Enter teacher id</p>
<form>
    <input type="number" name="teacher_id">
</form>
<br>
<p>Max students quantity</p>
<form>
    <input type="number" name="max_students_quantity">
</form>
<br>
<p>Enter quantity of course program steps</p>
<form>
    <input type = "number" name="course_program_steps_quantity">
</form>
</body>
</html>
