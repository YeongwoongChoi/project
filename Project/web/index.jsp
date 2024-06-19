<%--
  Created by IntelliJ IDEA.
  User: 278ch
  Date: 2024-04-16
  Time: 오후 3:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="css/loginPage.css">
    <link href="css/font.css" rel="stylesheet"/>
    <title>예약 시스템</title>
</head>
<script>
    $(document).ready(function() {
      location.reload();
    });
</script>
<body>
<div class="login-wrapper">
    <h2>예약 시스템</h2>
    <form method="post" id="login-form">
        <input type="text" name="userID" placeholder="Your ID">
        <input type="password" name="userPassword" placeholder="Password">
        <div class="buttons">
            <input type="submit" value="로그인" formaction="checkAccount.jsp">
            <input type="submit" value="회원가입" formaction="signUp.jsp">
        </div>
    </form>
</div>
</body>
</html>
