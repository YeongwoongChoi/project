<%--
  Created by IntelliJ IDEA.
  User: 278ch
  Date: 2024-05-18
  Time: 오후 4:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>비밀번호 변경</title>
    <script type="text/javascript" src="js/jquery-3.5.1.min.js"></script>
    <link href="css/font.css" rel="stylesheet"/>
</head>
<style>
    h2 {
        font-size: 24px;
        color: #6A24FE;
        margin-bottom: 20px;
    }

    #mainContents {
        width: auto;
        height: auto;
    }
    .passwordArea {
        display: grid;
        float: left;
        width: 80%;
        text-align: right;
    }
    .passwordArea > label {
        width: 80%;
        padding: 10px 10px;
    }
    #passwordForm {
        display: flex;
        width: 100%;
    }

    #passwordForm > label {
        padding: 5px;
    }

    #passwordForm > input[name=confirmModification] {
        width: 30%;
        height: 35px;
        background-color: #6A24FE;
        color: white;
        border: none;
        align-self: center;
    }

</style>
<script>
    function checkPassword() {
        const current = $('input[name=current]').val().trim();
        const modified = $('input[name=modified]').val().trim();
        if (current.length === 0 || modified.length === 0) {
            alert('비밀번호가 빈 문자열입니다!');
            return false;
        }

        if (current === modified) {
            alert('현재 비밀번호와 변경할 비밀번호가 동일합니다.');
            return false;
        }
        return true;
    }
</script>
<body>
<div id="mainContents">
    <h2>비밀번호 변경</h2>
    <form id="passwordForm" method="post" onsubmit="return checkPassword()">
        <div class="passwordArea">
            <label>현재 비밀번호 <input type="password" name="current"/></label>
            <label>변경할 비밀번호 <input type="password" name="modified"/></label>
        </div>

        <input type="submit" name="confirmModification" value="변경" formaction="checkModification.jsp?type=1">
    </form>
</div>

</body>
</html>
