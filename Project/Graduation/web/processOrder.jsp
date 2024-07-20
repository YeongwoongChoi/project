<%@ page import="java.util.Map" %>
<%@ page import="utils.DataBaseUtil" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.Arrays" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>a>
<%
    String id = (String) session.getAttribute("sessionID");
    if (id == null)
        response.sendRedirect("index.jsp");
    else {
        Map<String, String[]> result = request.getParameterMap();
        String dateTime = DataBaseUtil.getDateTimeFormat(result.get("selectedDate")[0], result.get("selectedTime")[0]);
        String[] dishIDs = result.get("dishID");
        String restaurantCode = result.get("rest")[0];
        int[] amounts = Arrays.stream(result.get("amount")).mapToInt(Integer::parseInt).toArray();

        try {
            Class.forName(DataBaseUtil.getDatabaseDriver());
            Connection conn = DataBaseUtil.getConnection();
            for (int i = 0; i < amounts.length; i++) {
                if (amounts[i] == 0)
                    continue;
                String sql = "insert into customerorder values(?, ?, ?, ?, STR_TO_DATE(?, '%Y-%m-%d %H:%i'));";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, id);
                ps.setString(2, restaurantCode);
                ps.setString(3, dishIDs[i]);
                ps.setInt(4, amounts[i]);
                ps.setString(5, dateTime);
                ps.executeUpdate();
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getStackTrace()[0]);
        }
%>
<head>
    <title>주문 중</title>
</head>
<body>
<script>
    alert('완료하였습니다.');
    parent.close();
    window.close();
    self.close();
</script>
</body>
</html>
<% } %>