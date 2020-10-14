<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta charset="UTF-8">
    <title>Create Hometask</title>
</head>
<body>
<h3>Menu</h3>
<br>
${menu}
<br>
<form action="/PROJECT_EPAM_JWD_war/hometask">
<p>Enter title</p>
<br>
    <label>
        <input type="text" name="title">
    </label>
    <br>
<p>Enter description</p>
<br>
    <label>
        <input type="text" name="description">
    </label>
    <br>
<p>Enter deadline(YYYY-MM-DD)</p>
    <label>
        <input type="text" name="deadline">
    </label>
    <input type="hidden" name="command" value="check_create_hometask">
    <input type="hidden" name="course_id" value="${course_id}">
    <input type="submit" value="Send hometask">
</form>
</body>