<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <script type="text/javascript" src="js/jquery-3.5.1.min.js"></script>
    <link href="css/font.css" rel="stylesheet"/>
    <title>회원 정보 찾기</title>
</head>
<style>
    h2 { margin-bottom: 0.2em; }
    h3 {
        font-size: 20px;
        color: #6A24FE;
        margin-bottom: 10px;
    }
    .mainContainer {
        display: inline-flex;
        width: 100%;
        height: auto;
        margin-top: 10px;
    }
    .subContainer {
        width: 50%;
        height: auto;
        margin: 0.3em;
        padding: 0.3em;
    }
    .inputArea {
        display: grid;
        grid-template-columns: 1fr 4fr;
        grid-row-gap: 0.7em;
        width: 100%;
        padding: 0.2em 0;
    }

    .inputArea > * {
        padding: 0.5em 0;
        width: auto; 
    }

    .subContainer > form {
        text-align: center;
    }

    input[type=submit] {
        margin-top: 10px;
        width: 50%;
        text-align: center;
    }
    
    label {
	    font-weight: bold;
    }

    .description {
        font-size: 13px;
    }
</style>
<script>
    function isFilled(i) {
        const phoneNumber = /^01([0|1|6|7|8|9])-([0-9]{3,4})-([0-9]{4})$/;
        const inputValue = $('input[name=phoneNumber]').eq(i).val();

        if (!phoneNumber.test(inputValue)) {
            alert('전화번호를 형식에 맞게 써주세요.');
            return false;
        }
        const confirm = $('input[name=confirm]').eq(i).is(':checked');
        if (!confirm) {
            alert('체크란에 체크되어 있지 않습니다.');
            return false;
        }
        return true;
    }
</script>
<body>
<!-- 기능 구현 중 -->
    <h2>회원 정보 찾기</h2>
    <div class="mainContainer">
        <div class="subContainer">
        <h3>아이디 찾기</h3>
        <form method="post" onsubmit="return isFilled(0)">
            <div class="inputArea">
            <label>이름</label><input type="text" name="userName" required>
            <label>성별</label> <select name="sex" required>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
            </select>

            <label>나이</label><input type="text" name="userAge" required>
            <label>전화번호</label><input type="tel" id="findId"  name="phoneNumber" placeholder="(ex. 010-1111-2222)" required>
            </div>
            <input type="submit" value="아이디 찾기" formaction="requestFinder.jsp?type=0">
        </form>
        <div class="description">
            <p>가입시 입력했던 전화번호를 입력해야 합니다.</p>
            <input type="checkbox" name="confirm">
            <label for="confirm">모든 내용을 이해했습니다.</label>
        </div>
        </div>
        <div class="subContainer">
        <h3>비밀번호 찾기</h3>
        <form method="post" onsubmit="return isFilled(1)">
            <div class="inputArea">
            <label>아이디</label> <input type="text" name="userId" required>
            <label>이름</label><input type="text" name="userName" required>
            <label>성별</label><select name="sex" required>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
            </select>
            <label>나이</label><input type="text" name="userAge" required>
            <label>전화번호</label><input type="tel" id="findPassword" name="phoneNumber" placeholder="(ex. 010-1111-2222)" required>
            </div>
            <input type="submit" value="임시 비밀번호 발급" formaction="requestFinder.jsp?type=1">
        </form>
        <div class="description">
            <p>임시 비밀번호이므로 가능한 빨리 변경하시기 바랍니다.</p>
            <input type="checkbox" name="confirm">
            <label for="confirm">모든 내용을 이해했습니다.</label>
        </div>
    </div>

    </div>
    
</body>
</html>

