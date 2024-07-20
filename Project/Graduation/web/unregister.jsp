<%--
  Created by IntelliJ IDEA.
  User: 278ch
  Date: 2024-05-28
  Time: 오후 2:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <script type="text/javascript" src="js/jquery-3.5.1.min.js"></script>
    <link href="css/font.css" rel="stylesheet"/>
    <title>회원 탈퇴</title>
</head>
<style>
    h3 {
        font-size: 20px;
        color: #6A24FE;
        margin-bottom: 10px;
    }
    div {
        width: 100%;
        height: auto;
        margin-top: 10px;
    }
    .description {
        font-size: 13px;
    }
</style>
<script>
    function isFilled() {
        const password = $('input[type=password]').val();
        if (password.trim().length === 0) {
            alert('비밀번호를 입력해야 합니다.');
            return false;
        }
        const confirm = document.getElementById("confirm");
        if (!confirm.checked) {
            alert('체크란에 체크되어 있지 않습니다.');
            return false;
        }
        return true;
    }
</script>
<body>
    <div class="mainContainer">
        <form method="post" onsubmit="return isFilled()">
            <h3>본인 확인을 위해 비밀번호를 입력해주세요.</h3>
            <input type="password" name="userPassword">
            <input type="submit" value="회원 탈퇴" formaction="requestUnregister.jsp">
        </form>
        <div class="description">
            <p>탈퇴할 경우, 모든 예약은 자동으로 취소되고 회원 정보가 소멸됩니다.</p>
            <input type="checkbox" id="confirm" name="confirm">
            <label for="confirm">모든 내용을 이해했습니다.</label>
        </div>
    </div>
</body>
</html>
