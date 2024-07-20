<%--
  Created by IntelliJ IDEA.
  User: 278ch
  Date: 2024-05-30
  Time: 오전 10:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entity.Restaurant" %>
<%@ page import="java.sql.ResultSet" %>
<!DOCTYPE html>
<html>
<%
    String restaurantCode = request.getParameter("rest");
    Restaurant r = (Restaurant) session.getAttribute(restaurantCode);
%>

<head>
    <title><%=r.getHTMLParsedName()%>
    </title>
    <script type="text/javascript" src="js/jquery-3.5.1.min.js"></script>
    <link href="css/orderDishes.css" rel="stylesheet"/>
    <link href="css/font.css" rel="stylesheet"/>
</head>


<script type="text/javascript">
    window.onload = function () {
        $('#date').attr('required', true);
        $('#time').attr('required', true);
        getDate();
        setTime();
    }

    function closeAll() {
        $('form[name=orderForm]').prop('onsubmit', null);
        $('#date').attr('required', false);
        $('#time').attr('required', false);
        parent.close();
        window.close();
        self.close();
    }

    function setDefaultImage(element) {
        element.src = "img/restaurant_alter.jpg";
    }

    function checkDateElements() {
        let date = new Date($('#date').val() + ' ' + $('#time').val());
        return today.getTime() < date.getTime();
    }

    function checkOrderElements() {
        let elements = $('form[name=orderForm] input[type=number]');
        let flag = true;
        $.each(elements, function (i, v) {
            const x = Number($(v).val());
            if (x < 0)
                flag = false;
            else if (x === 0)
                $(v).val(x);
        });
        if (!flag)
            alert('올바른 수량을 입력하세요.');
        return flag;
    }

    let check = () => checkDateElements() && checkOrderElements();

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

</script>

<body>
<h2>메뉴 예약 주문</h2>
<div id="mainContainer">
    <div id="imageContainer">
        <img class="restaurantImage" src="img/<%=r.getRestaurantCode()%>.jpg" onerror="setDefaultImage(this)"/>
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
        </div>
    </div>
    <div id="queryContainer">
        <form method="post" name="orderForm" onsubmit="return check()">
            <div id="dateSelection">
                <p id="today"></p>
                <label>예약 날짜: <input type="date" id="date" name="selectedDate"/></label>
                <label>방문 시각: <input type="time" id="time" name="selectedTime" max="21:00" step="600"/></label>
            </div>
            <%
                ResultSet rs = r.getMenus();
                if (rs != null) { %>
            <div class="description">
                <table>
                    <thead>
                    <tr>
                        <th>메뉴</th>
                        <th>가격($)</th>
                        <th>주문할 수량</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        while (rs.next()) { %>
                    <tr>
                        <input type="hidden" name="dishID" value="<%=rs.getString(1)%>">
                        <td><%=rs.getString(2)%>
                        </td>
                        <td><%=rs.getString(3)%>
                        </td>
                        <td><input type="number" name="amount"></td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
                <div id="submitButtons">
                    <input type="submit" value="주문하기" formaction="processOrder.jsp?rest=<%=restaurantCode%>">
                </div>
            </div>
        </form>
    </div>
    <% } %>
</div>
</body>
</html>