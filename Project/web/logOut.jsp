<%--
  Created by IntelliJ IDEA.
  User: 278ch
  Date: 2024-04-30
  Time: 오후 4:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>로그아웃 중</title>
</head>
<body>
<%
    String id = (String) session.getAttribute("sessionID");
    if (id == null) {
        response.sendRedirect("index.jsp");
    } else {
%>
<script>
    alert("<%=id%>님 로그아웃 되었습니다.");
    <%
    session.removeAttribute("sessionID");
    session.invalidate();
    %>
    location.replace('index.jsp');
</script>
</body>
</html>
<% } %>