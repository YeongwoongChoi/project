<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<%@ page import="utils.DataBaseUtil" %>

<!DOCTYPE html>
<html>
<head>
<link href="css/font.css" rel="stylesheet"/>
<title>정보 확인 중</title>
<style>
    h3 {
        font-size: 20px;
        color: #6A24FE;
        margin-bottom: 10px;
    }

    input {
        color: #fff;
        width: 45%;
        height: 48px;
        display: inline-block;
        font-size: 16px;
        background-color: #6A24FE;
        margin-top: 20px; 
    }
    .contents {
        margin: 0.5em;
        padding: 0.5em;
    }
    p span {
        font-weight: 700;
    }
</style>
</head>
<%
request.setCharacterEncoding("UTF-8");
final int FINDING_ID = 0;
final int FINDING_PASSWORD = 1;

int findingType = Integer.parseInt(request.getParameter("type"));

Class.forName(DataBaseUtil.getDatabaseDriver());
Connection conn = DataBaseUtil.getConnection();
    
String name = request.getParameter("userName");
String sex = request.getParameter("sex");
int age = Integer.parseInt(request.getParameter("userAge"));
String phoneNumber = request.getParameter("phoneNumber");

PreparedStatement ps;
ResultSet rs = null;
String id = "", password = "";
boolean isFound = false;

String sql = "select customerIdentifier from customer where customerName = ? and customerSex = ? and customerAge = ?;";
ps = conn.prepareStatement(sql);
ps.setString(1, name);
ps.setString(2, sex);
ps.setInt(3, age);
rs = ps.executeQuery();

switch (findingType) {
    case FINDING_ID:
        if (rs.next()) {
            id = rs.getString(1);
            sql = "select phoneNumber from customercontact where customerIdentifier = ?;";
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);

            rs = ps.executeQuery();
            while (rs.next()) {
                if (phoneNumber.contentEquals(rs.getString(1))) {
                    isFound = true;
                    break;
                }
            }
        }
        break;
    case FINDING_PASSWORD:
        if (rs.next()) {
            id = rs.getString(1);

            sql = "select customerPassword from customer where customerIdentifier = ?;";
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);

            rs = ps.executeQuery();
            
            if (rs.next()) {
                isFound = true;
                password = DataBaseUtil.getInitializedPassword();
                sql = "update customer set customerPassword = ? where customerIdentifier = ?;";
                ps = conn.prepareStatement(sql);
                ps.setString(1, DataBaseUtil.getHashedValue(password));
                ps.setString(2, id);
                ps.executeUpdate();
            }
        }
        break;
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
if (isFound) {
    if (findingType == FINDING_ID) { %>

    <h3>아이디 조회 결과</h3>
    <div class="contents">
    <p>당신의 ID는 <span><%=id%></span> 입니다. </p>
    </div>
    <% } else { %>

    <h3>임시 비밀번호 안내</h3>
    <div class="contents">
    <p>임시 비밀번호가 발급 되었습니다.</p>
    <p>즉시 로그인 후 변경하시기 바랍니다.</p>
    <p>비밀번호: <span><%=password%></span></p>

    <% } %>
    <input type="button" value="창 닫기" onclick="closeAll();">
<% } else { %>
    <script>
        alert('입력한 조건의 계정을 찾을 수 없습니다.');
        location.href = document.referrer;
    </script>
<% } %>
</body>
</html>
