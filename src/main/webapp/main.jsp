<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My University</title>
</head>
<body>
    <h1>Hello, User</h1>
    <br>
    <br>

    <h3>Sing In As Student</h3>
    <form action="/student_sign_in">
        <input type="submit" value="Student sign in">
    </form>
    <br>
    <br>

    <h3>Sign In As Teacher</h3>
    <form action="/teacher_sign_in">
        <input type="submit" value="Teacher sign in">
    </form>
    <br>
    <br>

    <h3>Sign In As Admin</h3>
    <form action="/admin_sign_in">
        <input type="submit" value="Admin sign in">
    </form>
    <br>
    <br>

    <h3>Menu</h3>
    <br>
    <form action="/courses_catalog">
        <input type="submit" value="Courses Catalog">
    </form>
    <br>
    <br>

    <h2>Course Add</h2>
    <br>
    ${courses_add}
</body>
</html>