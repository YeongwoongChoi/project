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
    final int MODIFY_PWD = 1;
    final int MODIFY_OTHERS = 0;

    request.setCharacterEncoding("UTF-8");
    int type = Integer.parseInt(request.getParameter("type"));
    String id = (String) session.getAttribute("sessionID");

    if (id == null) {
        response.sendRedirect("index.jsp");
    } else {
        ResultSet rs;
        String sql;
        PreparedStatement ps;
        Class.forName(DataBaseUtil.getDatabaseDriver());
        Connection conn = DataBaseUtil.getConnection();

        boolean[] isValid = {true, true};

        switch (type) {
            case MODIFY_OTHERS:
                String sex = request.getParameter("sex");
                int age = Integer.parseInt(request.getParameter("age"));
                String phoneNumber = request.getParameter("phoneNumber");
                sql = "update customer set customerSex=?, customerAge=? where customerIdentifier=?;";

                try {
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, sex);
                    ps.setInt(2, age);
                    ps.setString(3, id);
                    ps.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("Error occurred while updating customer\\'s information");
                }
                sql = "update customercontact set phoneNumber=? where phoneNumber=(select phoneNumber from (select * from customercontact) as temp where temp.customerIdentifier=?) and customerIdentifier=?;";

                try {
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, phoneNumber);
                    ps.setString(2, id);
                    ps.setString(3, id);
                    if (ps.executeUpdate() == 0)
                        isValid[MODIFY_OTHERS] = false;
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
                if (pwdInDB.equals(currentPwd)) {
                    sql = "update customer set customerPassword=? where customerIdentifier=?;";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, newPwd);
                    ps.setString(2, id);
                    ps.executeUpdate();
                } else
                    isValid[MODIFY_PWD] = false;
                break;
        }
%>
<!DOCTYPE html>
<html>
<head>
    <% switch (type) {
        case MODIFY_OTHERS:
    %>
    <title>정보 변경중</title>
    <% break;
        case MODIFY_PWD:
    %>
    <title>비밀번호 변경중</title>
    <% break;
    } %>

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
    switch (type) {
        case MODIFY_OTHERS:
            if (isValid[MODIFY_OTHERS]) { %>
<script>
    alert('정보 변경에 성공하였습니다.');
</script>
<% } else { %>
<script>
    alert('전화번호가 일치하지 않습니다.');
</script>
<% } %>
<% break;
    case MODIFY_PWD:
        if (isValid[MODIFY_PWD]) { %>
<script>
    alert('비밀번호 변경에 성공하였습니다.');
</script>
<% } else { %>
<script>
    alert('현재 비밀번호를 올바르게 입력하세요.');
</script>
<% } %>
<script> closeAll(); </script>
<% break;
} %>
<script>
    location.href = document.referrer;
</script>
</body>
</html>
<% } %>
