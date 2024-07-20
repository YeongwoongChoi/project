<%--
  Created by IntelliJ IDEA.
  User: 278ch
  Date: 2024-04-16
  Time: 오후 6:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="css/signUpPage.css">
    <link href="css/font.css" rel="stylesheet"/>
    <title>회원가입</title>
</head>
<script type="text/javascript">
    function equals() {
        const password = document.getElementsByName("userPassword")[0];
        const confirm = document.getElementsByName("confirmPassword")[0];
        let confirmLabel = document.getElementById("confirm")

        if (password.value === confirm.value) {
            confirmLabel.style.color = "#0000ff";
            confirmLabel.innerHTML = "비밀번호가 일치합니다."
        }
        else {
            confirmLabel.style.color = "#ff0000";
            confirmLabel.innerHTML = "비밀번호가 일치하지 않습니다.";
        }
    }

    function containsAllElements() {
        const agreed = document.getElementById("agreement");
        const formElements = document.getElementsByTagName("input");
        const phoneNumber = /^01([0|1|6|7|8|9])-([0-9]{3,4})-([0-9]{4})$/;

        for (let i = 0; i < formElements.length - 1; i++) {
            if (formElements[i].value.trim().length === 0) {
                alert("필수 입력 요소입니다.");
                return false;
            }
        }

        if (!phoneNumber.test(formElements[4].value)) {
            alert('전화번호를 형식에 맞게 써주세요.');
            return false;
        }
        if (!agreed.checked) {
            alert("동의해야 가입이 가능합니다.");
            return false;
        }
        if (parseInt(formElements[6].value) < 19) {
            alert('19세 이상만 가입 가능합니다.');
            return false;
        }
        return true;
    }
</script>
<body>
    <div class="signUp-wrapper">
        <h2>회원 가입</h2>
        <form method="post" onsubmit="return containsAllElements()" id="signUp-form">
            <input type="text" name="userName" placeholder="Your name">
            <input type="text" name="userID" placeholder="Your ID">
            <input type="password" name="userPassword" placeholder="Password">
            <input type="password" name="confirmPassword" placeholder="Confirm Password" onchange="equals()">
            <label id="confirm"></label>
            <input type="tel" name="phoneNumber" placeholder="Phone Number(ex. 010-1234-5678)">
            <select name="sex">
                <option value="Male">Male</option>
                <option value="Female">Female</option>
            </select>
            <input type="text" name="age" placeholder="Your Age(19세 이상만 가입 가능)">
            <div class="userAgreement">
                <input type="checkbox" id="agreement" name="agreement">
                <label for="agreement">회원 정보 제공에 동의합니다.</label>
            </div>
            <div class="buttons">
                <input type="button" value="뒤로 가기" onclick="location.href = 'index.jsp'">
                <input type="submit" value="가입 완료" formaction="checkSignUp.jsp">
            </div>
        </form>
    </div>
</body>
</html>
