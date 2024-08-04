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

    function createWindow(s, width = 640, height = 480) {
        const x = Math.ceil((window.screen.width - width) / 2);
        const y = Math.ceil((window.screen.height - height) / 2);
        window.open
        (s, 'findingWindow',
            'width=' + width + ', height=' + height + ', left=' + x + ', top=' + y + ', location=no, status=no, scrollbars=no');
    }
</script>
<body>
<div class="login-wrapper">
    <h2>예약 시스템</h2>
    <form method="post" id="login-form">
        <input type="text" name="userID" placeholder="Your ID">
        <input type="password" name="userPassword" placeholder="Password">
        <div class="buttons">
            <input type="submit" value="로그인" formaction="checkAccount.jsp">
            <input type="button" value="회원 정보 찾기" onclick="createWindow('findAccount.jsp', 800, 600)">
            <input type="submit" value="회원가입" formaction="signUp.jsp">
        </div>
    </form>
</div>
</body>
</html>
