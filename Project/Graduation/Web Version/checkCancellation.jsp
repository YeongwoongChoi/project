<%--
Created by IntelliJ IDEA.
User: 278ch
Date: 2024-05-15
Time: 오후 1:25
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<%@ page import="utils.DataBaseUtil" %>
<%
request.setCharacterEncoding("UTF-8");
String restaurantCode = request.getParameter("rest");
String id = (String) session.getAttribute("sessionID");

if (id == null) {
    response.sendRedirect("index.jsp");
} else {
    String dateTime = request.getParameter("date").replaceAll("\\u00A0", " ");
    int type = Integer.parseInt(request.getParameter("type"));
    int res = 0;

    Class.forName(DataBaseUtil.getDatabaseDriver());
    Connection conn = DataBaseUtil.getConnection();
    
    switch (type) {
        case 0:
            String dishID = request.getParameter("did");
            try {
                String sql = "delete from customerorder where customerIdentifier=? and restaurantCode=? and dishIdentifier=? and orderedTime=?;";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, id);
                ps.setString(2, restaurantCode);
                ps.setString(3, dishID);
                ps.setString(4, dateTime);         
                res = ps.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getStackTrace()[0]);
            }
            break;
        case 1:
            try {
                String sql = "delete from reserve where customerIdentifier=? and restaurantCode=? and reservedTime=?;";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, id);
                ps.setString(2, restaurantCode);
                ps.setString(3, dateTime);
                res = ps.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getStackTrace()[0]);
            }
            break;
    }
    conn.close();
    %>
        <!DOCTYPE html>
        <html>
        <head>
        <title>Title</title>
        </head>
        <body>
        <% if (res > 0) { %>
            <script>
                alert('취소 되었습니다.');
            location.href = document.referrer;
            </script>
                <% } else { %>
                    <script>
                        alert('이게 왜 안돼');
                    </script>
                        <% } %>
                        </body>
                        </html>
                        <% } %>
