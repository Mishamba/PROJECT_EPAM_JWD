<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My University</title>
</head>
<body>
    <h1>Hello, User</h1>
    <br>
    <br>

    <h2>Sing In</h2>
    <form action="/PROJECT_EPAM_JWD_war/sign_in?command=authorization">
        <input type="submit" value="Sign in">
    </form>
    <h2>Sing Up</h2>
    <form action="/PROJECT_EPAM_JWD_war/sign_up?command=registration">
        <input type="submit" value="Sign up">
    </form>

    <h2>Menu</h2>
    <br>
    <form action="/PROJECT_EPAM_JWD_war/courses_catalog?command=courses_catalog">
        <input type="submit" value="Courses Catalog">
    </form>
    <br>
    <br>

    <h2>Course Add</h2>
    <br>

    ${courses_add}

</body>
</html>