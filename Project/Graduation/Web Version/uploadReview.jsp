<%--
  Created by IntelliJ IDEA.
  User: 278ch
  Date: 2024-06-03
  Time: 오후 2:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<%@ page import="utils.DataBaseUtil" %>
<%@ page import="java.io.StringReader" %>
<%@ page import="entity.Restaurant" %>
<!DOCTYPE html>
<html>
<head>
    <title>리뷰 업로드 중</title>
</head>
<%
    String id = (String) session.getAttribute("sessionID");
    String restaurantCode = request.getParameter("rest");
    String dishID = request.getParameter("dish");
    double userRating = Double.parseDouble(request.getParameter("rating"));
    StringReader reviewText = new StringReader(request.getParameter("reviewText"));
    boolean isUpdated = false;
    
    try {
        Class.forName(DataBaseUtil.getDatabaseDriver());
        Connection conn = DataBaseUtil.getConnection();
        String sql = "insert into review values(?, ?, ?, ?, ?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, id);
        ps.setString(2, restaurantCode);
        ps.setString(3, dishID);
        ps.setDouble(4, userRating);
        ps.setCharacterStream(5, reviewText);
        isUpdated = ps.executeUpdate() > 0;

        sql = "update customer set earnedPoint = ((select earnedPoint from (select * from customer) as c where c.customerIdentifier = ?) + ?) where customerIdentifier = ?;";
        ps = conn.prepareStatement(sql);
        ps.setString(1, id);
        ps.setInt(2, DataBaseUtil.REVIEW_POINT);
        ps.setString(3, id);
        ps.executeUpdate();

        Restaurant r = (Restaurant) session.getAttribute(restaurantCode);
        isUpdated = r.updateRating(userRating);
    } catch (ClassNotFoundException | SQLException e) {
        System.out.println(e.getStackTrace()[0]);
    }
%>
<script>
    function closeAll() {
        parent.close();
        window.close();
        self.close();
    }
</script>
<body>
<%
    if (isUpdated) { %>
<script>
    alert('리뷰 작성을 완료하였습니다.');
</script>
<% } else { %>
<script>
    alert('이미 리뷰를 작성하셨습니다.');
</script>
<% } %>
<script>
    closeAll();
</script>
</body>
</html>