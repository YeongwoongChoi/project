<%--
  Created by IntelliJ IDEA.
  User: 278ch
  Date: 2024-05-28
  Time: 오후 2:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<%@ page import="utils.DataBaseUtil" %>

<!DOCTYPE html>
<html>
<head>
    <title>탈퇴 처리 중</title>
</head>
<%
    String id = (String) session.getAttribute("sessionID");
    if (id == null) {
        response.sendRedirect("index.jsp");
    } else {
        String password = DataBaseUtil.getHashedValue(request.getParameter("userPassword"));
        Class.forName(DataBaseUtil.getDatabaseDriver());
        Connection conn = DataBaseUtil.getConnection();

        String sql = "select customerPassword from customer where customerIdentifier=?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();

        String existingPassword = rs.getString(1);
        boolean succeed = password.equals(existingPassword);
        if (succeed) {
            sql = "delete from customer where customerIdentifier=?;";
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            int affected = ps.executeUpdate();
            succeed = affected > 0;
        }
%>
<script>
    function closeAll() {
        opener.location.href = 'index.jsp';
        parent.close();
        window.close();
        self.close();
    }
</script>
<body>
<%
    if (succeed) { %>
<script>
    alert('탈퇴에 성공하였습니다.');
    closeAll();
</script>
<% } else { %>
<script>
    alert('비밀번호를 확인해주세요.');
    location.href = document.referrer;
</script>
<% } %>
</body>
</html>
<% } %>