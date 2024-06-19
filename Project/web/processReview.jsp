<%@ page import="entity.Restaurant" %>
<%@ page import="utils.DataBaseUtil" %><%--
  Created by IntelliJ IDEA.
  User: 278ch
  Date: 2024-06-04
  Time: 오후 1:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<%
    String id = (String) session.getAttribute("sessionID");
    String restaurantCode = request.getParameter("rest");
    String dishID = request.getParameter("dishID");
    Restaurant r = (Restaurant) session.getAttribute(restaurantCode);

    boolean isRemoved = true;
    try {
        Class.forName(DataBaseUtil.getDatabaseDriver());
        Connection conn = DataBaseUtil.getConnection();
        String sql = "delete from review where customerIdentifier = ? and restaurantCode = ? and dishIdentifier = ?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, id);
        ps.setString(2, restaurantCode);
        ps.setString(3, dishID);
        isRemoved = ps.executeUpdate() > 0;

        sql = "update customer set earnedPoint = ((select earnedPoint from (select * from customer) as c where c.customerIdentifier = ?) - ?) where customerIdentifier = ?;";
        ps = conn.prepareStatement(sql);
        ps.setString(1, id);
        ps.setInt(2, DataBaseUtil.REVIEW_POINT);
        ps.setString(3, id);
        ps.executeUpdate();

        sql = "select count(*) from review where restaurantCode = ?;";
        ps = conn.prepareStatement(sql);
        ps.setString(1, restaurantCode);
        ResultSet rs = ps.executeQuery(); rs.next();
        int reviews = rs.getInt(1);
        double rating = 0.0d;

        sql = "select rating from review where restaurantCode = ?;";
        ps = conn.prepareStatement(sql);
        ps.setString(1, restaurantCode);
        rs = ps.executeQuery();
        while (rs.next())
            rating += rs.getDouble(1);
        rating /= reviews;

        sql = "update restaurant set rating = ? where restaurantCode = ?;";
        ps = conn.prepareStatement(sql);
        ps.setDouble(1, rating);
        ps.setString(2, restaurantCode);
        ps.executeUpdate();
        r.setRating(rating);
    } catch (ClassNotFoundException | SQLException e) {
        System.out.println(e.getMessage());
    }
%>
<head>
    <title>삭제 중</title>
</head>
<script>
    function closeAll() {
        parent.close();
        window.close();
        self.close();
    }
</script>
<body>
<%
    if (isRemoved) {
%>
<script>
    alert('제거하였습니다.');
    closeAll();
</script>
<% } %>
</body>
</html>
