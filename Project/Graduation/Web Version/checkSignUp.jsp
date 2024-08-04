<%--
  Created by IntelliJ IDEA.
  User: 278ch
  Date: 2024-04-16
  Time: 오후 7:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collections" %>
<%@ page import="utils.DataBaseUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%! String getValue(HttpServletRequest request, final String key) {
    return request.getParameterValues(key)[0];
} %>
<%
    request.setCharacterEncoding("UTF-8");
    ArrayList <String> paramKeys = Collections.list(request.getParameterNames());
    boolean canSignUp = true;

    ResultSet rs;
    try {
        Class.forName(DataBaseUtil.getDatabaseDriver());
        Connection conn = DataBaseUtil.getConnection();
        String id = getValue(request, paramKeys.get(1));
        String sql = "select * from customer where customerIdentifier=?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, id);
        rs = ps.executeQuery();
        if (rs.next())
            canSignUp = false;
        else {
            sql = "insert into customer values(?, ?, ?, ?, ?, 0);";
            String name = getValue(request, paramKeys.get(0));
            String phoneNumber = getValue(request, paramKeys.get(4));
            String sex = getValue(request, paramKeys.get(5));
            int age = Integer.parseInt(getValue(request, paramKeys.get(6)));
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, DataBaseUtil.getHashedValue(getValue(request, paramKeys.get(2))));
            ps.setString(3, name);
            ps.setString(4, sex);
            ps.setInt(5, age);
            ps.executeUpdate();

            sql = "insert into customercontact values(?, ?);";
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, phoneNumber);
            ps.executeUpdate();
        }

    } catch (ClassNotFoundException ce) {
        System.out.println("check your JDBC");
    } catch (SQLException se) {
        System.out.println("SQL Error");
    }

%>
<!DOCTYPE html>
<html>
<head>
    <title>회원가입 중</title>
</head>
<body>
    <% if (canSignUp)  { %>
    <script>
        alert('회원 가입이 완료되었습니다.');
        location.href = 'index.jsp';
    </script>
    <% } else { %>
    <script>
        alert('중복된 아이디입니다.');
        location.href = 'signUp.jsp';
    </script>
    <% } %>
</body>
</html>