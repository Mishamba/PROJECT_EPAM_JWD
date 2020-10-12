<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta charset="UTF-8">
    <title>Create Hometask</title>
</head>
<body>
<form action="/PROJECT_EPAM_JWD_war/hometask">
<p>Enter title</p>
<br>
    <label>
        <input type="text" name="hometask_title">
    </label>
    <br>
<p>Enter description</p>
<br>
    <label>
        <input type="text" name="hometask_description">
    </label>
    <br>
<p>Enter deadline(YYYY-MM-DD)</p>
    <label>
        <input type="text" name="deadline">
    </label>
    <input type="hidden" name="command" value="create_hometask">
    <input type="submit" value="Send hometask">
</form>
</body>