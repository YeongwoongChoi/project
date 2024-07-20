<%@ page import="entity.Restaurant" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String restaurantCode = request.getParameter("rest");
    Restaurant r = (Restaurant) session.getAttribute(restaurantCode);
%>
<!DOCTYPE html>
<html>
<head>
    <title>Detailed Info for <%=r.getHTMLParsedName()%>
    </title>
    <script type="text/javascript" src="js/jquery-3.5.1.min.js"></script>
    <link href="css/font.css" rel="stylesheet"/>
</head>
<style>
    h2 {
        font-size: 20px;
        color: #6A24FE;
        margin: 5px 5px;
    }

    body {
        width: 97%;
        height: 75%;
    }

    #mainContainer {
        width: 100%;
        height: auto;
        display: flex;
    }

    #imageContainer {
        display: inline-block;
        float: left;
        width: 45%;
        height: 100%;
        padding: 0.5em;
    }

    #queryContainer {
        display: flex;
        width: 100%;
        height: auto;
        align-items: center;
    }

    .restaurantImage {
        width: 100%;
        height: 350px;
        margin-bottom: 0.5em;
    }

    #detailedInfo {
        background-color: #eee;
        float: right;
        border-radius: 20px;
        width: 50%;
        height: auto;
        padding: 0.5em;
    }

    .info {
        padding: 10px 10px;
        height: auto;
    }
    .info > p {
        text-align: center;
    }

    .description {
        padding: 10px 10px;
    }

    .reviewButton {
        display: flex;
        width: auto;
        justify-content: space-evenly;
    }
    .description > p {
        padding-bottom: 10px;
    }

    .description > #title {
        text-align: center;
    }

    #queryContainer > form {
        width: 100%;
        display: flex;
    }

    #dateSelection {
        display: flex;
        flex-direction: column;
        float: left;
        height: auto;
        margin: 0.5em;
    }

    #today {
        float: left;
        padding: 10px;
    }

    #dateSelection > label {
        padding: 10px;
    }

    #submitButtons {
        text-align: center;
        align-content: center;
        float: right;
        display: grid;
        grid-template-columns: repeat(2, 1fr);
        grid-column-gap: 1em;
        width: 40%;
        height: auto;
        padding: 0.7em;
    }

    input[type=button], input[type=submit] {
        color: #fff;
        width: auto;
        height: 48px;
        font-size: 16px;
        background-color: #6A24FE;
        margin-top: 5px;
    }

</style>
<script type="text/javascript">
    window.onload = function () {
        $('#date').attr('required', true);
        $('#time').attr('required', true);
        $('#people').attr('required', true);
        getDate();
        setTime();
    }

    function closeAll() {
        $('form[name=form]').prop('onsubmit', null);
        $('#date').attr('required', false);
        $('#time').attr('required', false);
        $('#people').attr('required', false);
        parent.close();
        window.close();
        self.close();
    }

    function setDefaultImage(element) {
        element.src = "img/restaurant_alter.jpg";
    }

    function checkAllElements() {
        let people = parseInt($('#people').val());
        let date = new Date($('#date').val() + ' ' + $('#time').val());

        if (people <= 0) {
            alert('올바른 인원 수를 입력하세요.');
            return false;
        }
        if (today.getTime() >= date.getTime()) {
            alert('예약 시간은 현재 시간 이후여야 합니다.');
            return false;
        }
        return true;
    }

    let today = new Date();
    const time = (today.getHours() + 1) % 24 + ":00";

    function getDate() {
        const year = today.getFullYear();
        const month = (today.getMonth() + 1).toString().padStart(2, '0');
        const day = today.getDate().toString().padStart(2, '0');
        let x = document.getElementById("today");
        const fullDate = year + '-' + month + '-' + day;
        x.innerText = "오늘 날짜: " + fullDate;
        $('#date').prop({"min": fullDate, "value": fullDate});
    }

    function setTime() {
        $('#time').prop("value", time);
    }

    function createWindow(s, width = 640, height = 480) {
        const x = Math.ceil((window.screen.width - width) / 2);
        const y = Math.ceil((window.screen.height - height) / 2);
        const params = '?rest=' + '<%=restaurantCode%>';
        window.open
        (s + params,
            'writingReview',
            'width=' + width + ', height=' + height + ', left=' + x + ', top=' + y + ', location=no, status=no, scrollbars=yes');
    }

</script>

<body>
<h2>식당 상세정보</h2>
<div id="mainContainer">
    <div id="imageContainer">
        <img class="restaurantImage" src="img/<%=r.getRestaurantCode()%>.jpg" onerror="setDefaultImage(this)"/>
        <div id="queryContainer">
            <form method="post" name="form" onsubmit="return checkAllElements()">
                <div id="dateSelection">
                    <p id="today"></p>
                    <label>예약 날짜: <input type="date" id="date" name="selectedDate"/></label>
                    <label>예약 시각: <input type="time" id="time" name="selectedTime" max="21:00" step="1800"/></label>
                    <label>예약 인원: <input type="number" id="people" name="people"/></label>
                </div>
                <div id="submitButtons">
                    <input type="button" value="리뷰 작성" onclick="createWindow('writeReview.jsp')">
                    <input type="submit" value="예약하기" formaction="checkReservation.jsp?rest=<%=restaurantCode%>">
                </div>
            </form>
        </div>
    </div>
    <div id="detailedInfo">
        <div class="info">
            <p>기본 정보</p>
            <table>
                <tr>
                    <td><%=r.getRestaurantName()%>
                    </td>
                </tr>
                <tr>
                    <td><%=r.getTypeOfDishes()%> Restaurant</td>
                </tr>
                <tr>
                    <td><%=r.getPhoneNumber()%>
                    </td>
                </tr>
                <tr>
                    <td><%=r.getLocation()%>
                    </td>
                </tr>
            </table>
        </div>
        <div class="description">
            <p id="title">설명</p>
            <p><%=r.getDescription()%>
            </p>
            <%
                ResultSet rs = r.getMenus();
                if (rs != null) { %>
            <div class="menu">
            <table>
                <thead>
                <tr>
                    <th>메뉴</th>
                    <th>가격($)</th>
                </tr>
                </thead>
                <tbody>
                <%
                    while (rs.next()) { %>
                <tr>
                    <td><%=rs.getString(2)%>
                    </td>
                    <td><%=rs.getString(3)%>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
            </div>
            <div class="reviewButton">
                <p>평점: ⭐ <%=String.format("%.2f", r.getRating())%> / 5.0</p>
                <input type="button" value="리뷰 보기" onclick="createWindow('review.jsp')"/>
            </div>
            <% } %>
        </div>
    </div>


</div>
</body>
</html>
