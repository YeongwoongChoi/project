<%--
  Created by IntelliJ IDEA.
  User: 278ch
  Date: 2024-04-16
  Time: 오후 3:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="utils.DataBaseUtil" %>
<%
    request.setCharacterEncoding("UTF-8");
    String id = request.getParameter("userID");
    String password = request.getParameter("userPassword");
    ResultSet rs;
    boolean canLogin = true, exists = true;

    try {
        Class.forName(DataBaseUtil.getDatabaseDriver());
        Connection conn = DataBaseUtil.getConnection();
        String sql = "select customerPassword from customer where customerIdentifier = ?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, id);
        rs = ps.executeQuery();

        if (rs.next()) {
            String passwordInDB = rs.getString(1);
            if (passwordInDB.contentEquals(DataBaseUtil.getHashedValue(password))) {
                session.setAttribute("sessionID", id);
                response.sendRedirect("main.jsp");
            }
            else
                canLogin = false;
        }
        else
            exists = false;

    } catch (ClassNotFoundException ce) {
        System.out.println("check your JDBC");
    } catch (SQLException se) {
        se.printStackTrace();
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>로그인 중</title>
    <link href="css/font.css" rel="stylesheet"/>
</head>
<body>
<%
    if (!(canLogin && exists)) { %>
<script>
    alert('아이디나 비밀번호를 다시 확인해주세요.');
    history.back();
</script>
<% } %>
</body>
</html>
