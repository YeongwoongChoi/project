<%--
  Created by IntelliJ IDEA.
  User: 278ch
  Date: 2024-05-01
  Time: 오후 3:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.sql.*" %>
<%@ page import="utils.DataBaseUtil" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    request.setCharacterEncoding("UTF-8");
    String userID = (String) session.getAttribute("sessionID");
    if (userID == null) {
        response.sendRedirect("index.jsp");
    } else {
        String restaurant = request.getParameter("rest");
        int people = Integer.parseInt(request.getParameter("people"));

        String dateTime = DataBaseUtil.getDateTimeFormat
                (request.getParameter("selectedDate"), request.getParameter("selectedTime"));
        boolean canBook = true;

        try {
            Class.forName(DataBaseUtil.getDatabaseDriver());
            Connection conn = DataBaseUtil.getConnection();
            String sql = "select * from reserve where customerIdentifier=? and restaurantCode=? and reservedTime=STR_TO_DATE(?, '%Y-%m-%d %H:%i')";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userID);
            ps.setString(2, restaurant);
            ps.setString(3, dateTime);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                canBook = false;
            else {
                sql = "insert into reserve values(?, ?, STR_TO_DATE(?, '%Y-%m-%d %H:%i'), ?);";
                ps = conn.prepareStatement(sql);
                ps.setString(1, userID);
                ps.setString(2, restaurant);
                ps.setString(3, dateTime);
                ps.setInt(4, people);
                ps.executeUpdate();
            }
        } catch (ClassNotFoundException ce) {
            System.out.println("check your JDBC");
        } catch (SQLException se) {
            System.out.println("Internal SQL Error");
        }
%>
<!DOCTYPE html>
<html>
<head>
    <title>예약 중</title>
</head>
<script>
    function closeAll() {
        parent.close();
        window.close();
        self.close();
    }
</script>
<body>
<% if (canBook) { %>
<script> alert('예약이 완료되었습니다.'); </script>
<% } else { %>
<script> alert('이미 해당 식당에 대한 동일시간 예약이 존재합니다.'); </script>
<% } %>
<script> closeAll(); </script>
</body>
</html>
<% } %>