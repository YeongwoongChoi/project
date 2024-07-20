<%--
  Created by IntelliJ IDEA.
  User: 278ch
  Date: 2024-06-03
  Time: 오후 2:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ page import="java.sql.*" %>
<%@ page import="utils.DataBaseUtil" %>
<%@ page import="java.io.Reader" %>
<%@ page import="java.io.StringWriter" %>
<!DOCTYPE html>
<html>
<%
    String restaurantCode = request.getParameter("rest");
    String id = (String) session.getAttribute("sessionID");
    Connection conn = null;
    String customerName;

    String sql;
    PreparedStatement ps;
    ResultSet rs = null;
    try {
        Class.forName(DataBaseUtil.getDatabaseDriver());
        conn = DataBaseUtil.getConnection();
        sql = "select customerIdentifier, dishIdentifier, rating, reviewText from review where restaurantCode = ?;";
        ps = conn.prepareStatement(sql);
        ps.setString(1, restaurantCode);
        rs = ps.executeQuery();
    } catch (ClassNotFoundException | SQLException e) {
        System.out.println(e.getStackTrace()[0]);
    }
%>
<head>
    <title>작성된 리뷰</title>
    <script type="text/javascript" src="js/jquery-3.5.1.min.js"></script>
    <link href="css/font.css" rel="stylesheet"/>
</head>
<style>
    h3 {
        font-size: 20px;
        color: #6A24FE;
        margin-bottom: 10px;
    }

    #review {
        width: 95%;
    }

    .section {
        width: auto;
        background-color: #b6d4fe;
        border-radius: 10px;
        display: flex;
        align-items: center;
        flex-direction: row;
        margin-bottom: 10px;
        padding: 5px;
    }

    .section * {
        margin: 5px;
    }

    .userInfo {
        float: left;
        width: 40%;
    }

    .reviewArea {
        float: right;
        width: 43%;
    }

    textarea {
        width: 100%;
        height: auto;
        resize: none;
    }

    input[type=submit] {
        position: absolute;
        color: #ffffff;
        height: auto;
        font-size: 16px;
        background-color: #a52834;
        margin: 10px 10px;
    }
</style>
<script>
    let moveTo = () => confirm('리뷰가 삭제됩니다.');
    const zip = (a, b) => a.map((x, i) => [x, b[i]]);

    $(document).ready(function () {
        $('textarea').each(function () {
            const s = $(this).val().trim();
            $(this).val(s);
        });

        const buttons = Array.from(document.getElementsByName("removal"));
        buttons.forEach(e => {
            const r = e.getBoundingClientRect();
            const x = window.scrollX + r.left;
            const y = window.scrollY + r.top;

            e.setAttribute('top', y + 'px');
            e.setAttribute('left', x + 200 + 'px');
        });
    });
</script>
<body>
<h3>모든 리뷰</h3>
<div id="review">
    <% ResultSet tmp;
        if (!rs.next()) {
    %>
    <section class="section">
        <p>아직 리뷰가 작성되지 않았습니다.</p>
    </section>
    <% } else {
        do { %>
    <section class="section">
        <%
            String reviewerID = rs.getString(1);
            sql = "select customerName from customer where customerIdentifier = ?;";
            ps = conn.prepareStatement(sql);
        %>
        <%
            ps.setString(1, reviewerID);
            String dishID = rs.getString(2);
            tmp = ps.executeQuery();
            tmp.next();
            customerName = tmp.getString(1);
        %>
        <%
            sql = "select dishName from dish where dishIdentifier = ?;";
            ps = conn.prepareStatement(sql);
            ps.setString(1, dishID);
            tmp = ps.executeQuery();
            tmp.next();
            String dishName = tmp.getString(1);
        %>
        <div class="userInfo">
            <p>작성자: <%=customerName%>
            </p>
            <p>음식 이름: <%=dishName%>
            </p>
            <p>평점: <%=rs.getDouble(3)%>
            </p>
        </div>
        <div class="reviewArea">
            <p>리뷰</p>
            <textarea class="reviewText" readonly>
                <%
                    Reader sr = rs.getCharacterStream(4);
                    StringWriter sw = new StringWriter();
                    sr.transferTo(sw);
                %>
                <%=sw.toString().trim()%>
                <% sw.close(); %>
            </textarea>
        </div>
        <% if (id.equals(reviewerID)) { %>
        <form method="post" onsubmit="return moveTo()">
            <input type="hidden" name="dishID" value="<%=rs.getString(2)%>">
            <input type="hidden" name="rest" value="<%=restaurantCode%>">
            <input type="submit" name="removal" value="삭제" formaction="processReview.jsp">
        </form>
        <% } %>
    </section>
    <% } while (rs.next());
    }%>
</div>
</body>
</html>
