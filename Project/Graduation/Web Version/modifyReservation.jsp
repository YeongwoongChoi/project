<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<%@ page import="entity.Restaurant" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="utils.DataBaseUtil" %>
<%@ page import="java.util.LinkedHashMap" %>
<!DOCTYPE html>
<html>
<%
    request.setCharacterEncoding("UTF-8");
	String id = (String) session.getAttribute("sessionID");
	int modifyingType = Integer.parseInt(request.getParameter("type"));
	String restaurantCode = request.getParameter("rest");

	final int MODIFY_ORDER = 0;
	final int MODIFY_RESERVATION = 1;
	Restaurant r = (Restaurant) session.getAttribute(restaurantCode);
    
    if (id == null || r == null) { %>
    <script>closeAll();</script>
    <% }
    String dateTime = request.getParameter("date");
    ResultSet rs = null;
    Class.forName(DataBaseUtil.getDatabaseDriver());

    PreparedStatement ps = null;
    Connection conn = DataBaseUtil.getConnection();
    String sql = "";
    int amount = 0;
%>
<head>
    <title>예약 수정</title>
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
    #description {
        display: grid;
        grid-template-columns: 1fr 3fr;
        grid-column-gap: 10px;
        grid-row-gap: 10px;
        padding: 10px;
    }
    #dateSelection {
        display: flex;
        background-color: #b6d4fe;
        margin-top: 10px;
    }
    #dateSelection > * {
        padding: 10px 10px;
    }
    #dateSelection > section {
        display: grid;
        width: 50%;
        grid-row-gap: 10px;
    }
    .fieldArea {
        display: grid;
        float: left;
        width: 80%;
        margin: 10px;
    }
    .fieldArea > label {
        width: 80%;
        padding: 10px 10px;
    }
    #form {
        display: flex;
        width: 100%;
    }

    #form > label {
        padding: 5px;
    }

    input[name=confirmModification] {
        width: 50%;
        height: 35px;
        background-color: #6A24FE;
        color: white;
        border: none;
        align-self: center;
    }

</style>
<script>

    const type = <%=modifyingType%>;
    
    window.onload = function () {
        if (type === 0)
            setValues($('select[name=dish]').val());

        $('#date').attr('required', true);
        $('#time').attr('required', true);
        $('input[type=number]').attr('required', true);
        $('input[type=submit]').attr('formaction', 'checkModification.jsp?type=' + (type + 2));
    }

    function check() {
        let date = new Date($('#date').val() + ' ' + $('#time').val());
        let today = new Date();
        
        if (today.getTime() > date.getTime()) {
            alert('예약 시각은 현재 시간 이후여야 합니다.');
            return false;
        }
        
        const amount = Number($('input[type=number]').val());
        if (amount <= 0) {
            alert((type === 0 ? '수량이 ': '인원 수가 ') + '올바르지 않습니다.');
            return false;
        }
        return true;
    }

    var setValues = function (x) {
        $('input[name=price]').val($('input[id=' + x + ']').val());
        $('input[name=dishIdentifier]').val(x);
    }

</script>
<body>
<div id="mainContents">
    <h2>예약 수정</h2>
    <form id="form" method="post" onsubmit="return check()">
        <div class="fieldArea">
            <input type="hidden" name="restaurantCode" value="<%=restaurantCode%>"/>
            <div id="description">
            <label>식당 이름</label><input type="text" name="restaurantName" value="<%=r.getRestaurantName()%>" style="background-color: rgb(223, 223, 223); border: 1px solid;" readonly/>
			<% switch (modifyingType) {
			case MODIFY_ORDER:
                ResultSet servedDishes = r.getMenus();
                LinkedHashMap <String, Double> dishToPrice = new LinkedHashMap<>();
                HashMap <String, String> dishIdToName = new HashMap<>();
                String dishIdentifier = request.getParameter("did");

                while (servedDishes.next()) {
                    dishIdToName.put(servedDishes.getString(1), servedDishes.getString(2));
                    dishToPrice.put(servedDishes.getString(1), servedDishes.getDouble(3));
                }
                sql = "select totalDishes from customerorder where customerIdentifier=? and restaurantCode=? and dishIdentifier=? and orderedTime=?;";
                
                try {
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, id);
                    ps.setString(2, restaurantCode);
                    ps.setString(3, dishIdentifier);
                    ps.setString(4, dateTime);
                    rs = ps.executeQuery();
                    if (rs.next())
                        amount = rs.getInt(1);                   
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            %>
            <label>주문할 음식</label>
            <input type="hidden" name="dishIdentifier">
            <input type="hidden" name="selectedDish" value="<%=dishIdentifier%>">
            <select name="dish" onchange="setValues(this.value)">
            <% for (Map.Entry<String, String> e: dishIdToName.entrySet()) {
                   if (e.getKey().equals(dishIdentifier)) {  %>
                    <option value="<%=e.getKey()%>" selected><%=e.getValue()%></option>
            <%     } else { %>
                    <option value="<%=e.getKey()%>"><%=e.getValue()%></option>
            <%  } 

                }%>
            </select>
            <% for (String k: dishIdToName.keySet()) { %>
            <input type="hidden" id="<%=k%>" value="<%=dishToPrice.get(k)%>">
            <% } %>
            <label>가격($)</label>
            <input type="text" name="price" readonly>
            <label>수량</label>
            <input type="number" name="amount" value="<%=amount%>" required>
            <%
			break;
			case MODIFY_RESERVATION:
                sql = "select numberOfPeople from reserve where customerIdentifier=? and restaurantCode=? and reservedTime=?;";
                try {
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, id);
                    ps.setString(2, restaurantCode);
                    ps.setString(3, dateTime);
                    rs = ps.executeQuery();

                    if (rs.next())
                        amount = rs.getInt(1);

                } catch (SQLException e) {
                    e.printStackTrace();
                  }

            %>
                <label>예약 인원</label><input type="number" name="people" value="<%=amount%>">
		    <% break;
            }
            conn.close();
            %>
            </div>
            <input type="hidden" name="previousDateTime" value="<%=dateTime%>">
            <div id="dateSelection">
                <section>
                <p>방문 예정 시각</p>
            <%
                String [] splitted = dateTime.split(" ");
            %>
                <label>날짜: <input type="date" id="date" name="selectedDate" value="<%=splitted[0]%>"></label>
                <label>시각: <input type="time" id="time" name="selectedTime" value="<%=splitted[1]%>" max="21:00" step="600"></label>
                </section>
                <section>
                <input type="submit" name="confirmModification" value="변경">
                </section>
            </div>
        </div>
    </form>
</div>

</body>
</html>
