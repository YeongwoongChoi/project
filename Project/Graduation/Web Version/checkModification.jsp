<%--
  Created by IntelliJ IDEA.
  User: 278ch
  Date: 2024-05-19
  Time: 오후 7:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.sql.*" %>
<%@ page import="utils.DataBaseUtil" %>

<%
	final int MODIFY_RESERVATION = 3;
	final int MODIFY_ORDER = 2;
    final int MODIFY_PWD = 1;
    final int MODIFY_USER = 0;

    request.setCharacterEncoding("UTF-8");
    int type = Integer.parseInt(request.getParameter("type"));
    String id = (String) session.getAttribute("sessionID");

    if (id == null) {
        response.sendRedirect("index.jsp");
    } else {
        ResultSet rs;
        String sql, dateTime = "", restaurantCode;
        PreparedStatement ps;
        Class.forName(DataBaseUtil.getDatabaseDriver());
        Connection conn = DataBaseUtil.getConnection();

        boolean isValid = true;

        switch (type) {
            case MODIFY_USER:
                String sex = request.getParameter("sex");
                int age = Integer.parseInt(request.getParameter("age"));
                String phoneNumber = request.getParameter("phoneNumber");
                sql = "update customer set customerSex=?, customerAge=? where customerIdentifier=?;";

                try {
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, sex);
                    ps.setInt(2, age);
                    ps.setString(3, id);
                    isValid = ps.executeUpdate() > 0;
                } catch (SQLException e) {
                    System.out.println("Error occurred while updating customer\\'s information");
                }
                sql = "update customercontact set phoneNumber=? where phoneNumber=(select phoneNumber from (select * from customercontact) as temp where temp.customerIdentifier=?) and customerIdentifier=?;";

                try {
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, phoneNumber);
                    ps.setString(2, id);
                    ps.setString(3, id);
                    isValid = ps.executeUpdate() > 0;
                } catch (SQLException e) {
                    System.out.println("Error occurred while updating customer\\'s contact.");
                }
                break;
            case MODIFY_PWD:
                String currentPwd = DataBaseUtil.getHashedValue(request.getParameter("current"));
                String newPwd = DataBaseUtil.getHashedValue(request.getParameter("modified"));

                sql = "select customerPassword from customer where customerIdentifier=?;";
                ps = conn.prepareStatement(sql);
                ps.setString(1, id);
                rs = ps.executeQuery();
                rs.next();
                String pwdInDB = rs.getString(1);
                isValid = pwdInDB.equals(currentPwd);

                if (isValid) {
                    sql = "update customer set customerPassword=? where customerIdentifier=?;";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, newPwd);
                    ps.setString(2, id);
                    ps.executeUpdate();
                }
                break;
			case MODIFY_ORDER:
                restaurantCode = request.getParameter("restaurantCode");
                String dishIdentifier = request.getParameter("dishIdentifier");
				String orderedTime = request.getParameter("previousDateTime");
                
                dateTime = DataBaseUtil.getDateTimeFormat(request.getParameter("selectedDate"), request.getParameter("selectedTime"));
                String selectedDish = request.getParameter("selectedDish");
                int amount = Integer.parseInt(request.getParameter("amount"));

                sql = "update customerorder set dishIdentifier = ?, orderedTime = ?, totalDishes = ? where customerIdentifier = ? and restaurantCode = ? and dishIdentifier = ? and orderedTime = ?;";

                ps = conn.prepareStatement(sql);
                ps.setString(1, dishIdentifier);
                ps.setString(2, dateTime);
                ps.setInt(3, amount);
                ps.setString(4, id);
                ps.setString(5, restaurantCode);
                ps.setString(6, selectedDish);
                ps.setString(7, orderedTime);

                isValid = ps.executeUpdate() > 0; 
                break;

			case MODIFY_RESERVATION:
                restaurantCode = request.getParameter("restaurantCode");
                dateTime = DataBaseUtil.getDateTimeFormat(request.getParameter("selectedDate"), request.getParameter("selectedTime"));

                String reservedTime = request.getParameter("previousDateTime");
                int people = Integer.parseInt(request.getParameter("people"));

                sql = "update reserve set numberOfPeople = ?, reservedTime = ? where customerIdentifier = ? and restaurantCode = ? and reservedTime = ?;";

                ps = conn.prepareStatement(sql);
                ps.setInt(1, people);
                ps.setString(2, dateTime);
                ps.setString(3, id);
                ps.setString(4, restaurantCode);
                ps.setString(5, reservedTime);

                isValid = ps.executeUpdate() > 0;
				break;
        }
        conn.close();
%>
<!DOCTYPE html>
<html>
<head>
    <% switch (type) {
        case MODIFY_USER:
    %>
    <title>정보 변경중</title>
    <% break;
        case MODIFY_PWD:
    %>
    <title>비밀번호 변경중</title>
    <% break;
        case MODIFY_ORDER:
        case MODIFY_RESERVATION:
    %>
        <title>예약 변경중</title>
        <% break;
        } %>

</head>
<script>
    function closeAll() {
        opener.location.reload();
        parent.close();
        window.close();
        self.close();
    }
</script>
<body>
<%
    if (isValid) { %>
        <script> alert('변경에 성공하였습니다.'); </script>
    <% }
    } %>

    <script> closeAll(); </script>
</body>
</html>
