<%@ page import="entity.Restaurant" %>
<%@ page import="java.sql.*" %>
<%@ page import="utils.DataBaseUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: 278ch
  Date: 2024-06-03
  Time: 오전 8:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<%
    String restaurantCode = request.getParameter("rest");
    String id = (String) session.getAttribute("sessionID");

    Restaurant r = (Restaurant) session.getAttribute(restaurantCode);
    ResultSet rs;
    if (id == null) {
        response.sendRedirect("index.jsp");
    } else {
        ResultSet orderSet = null;
        boolean hasReservation = true;
        boolean ordered = true;
        try {
            Class.forName(DataBaseUtil.getDatabaseDriver());
            Connection conn = DataBaseUtil.getConnection();
            String sql = "select * from reserve where customerIdentifier = ? and restaurantCode = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, restaurantCode);
            rs = ps.executeQuery();
            hasReservation = rs.next();

            sql = "select * from customerorder where customerIdentifier = ? and restaurantCode = ?;";
            ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, id);
            ps.setString(2, restaurantCode);
            orderSet = ps.executeQuery();
            ordered = orderSet.first();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
%>
<head>
    <title>리뷰 작성하기</title>
    <script src="js/jquery-3.5.1.min.js"></script>
    <link href="css/font.css" rel="stylesheet"/>
</head>
<style>
    h3 {
        font-size: 20px;
        color: #6A24FE;
        margin-bottom: 10px;
    }

    .mainContainer {
        width: 95%;
    }

    .mainContainer > form {
        display: grid;
        width: auto;
        grid-template-columns: 1fr;
    }

    form > * {
        margin: 10px;
    }

    .reviewText {
        padding: 5px;
        width: 100%;
        resize: vertical;
        max-height: 200px;
        box-sizing: border-box;
    }

    input[type=button], input[type=submit] {
        color: #fff;
        width: auto;
        height: 48px;
        font-size: 16px;
        background-color: #6A24FE;
        margin-top: 5px;
    }

</style>
<script>
    let resize = (e) => {
        e.style.height = 'auto';
        e.style.height = e.scrollHeight + 'px';
    };

    function closeAll() {
        parent.close();
        window.close();
        self.close();
    }

    let checkRating = () => $('input[name=rating]').val().trim().length > 0;
</script>
<body>
<h3>리뷰 작성하기</h3>
<% if (!(hasReservation || ordered)) { %>
<script>
    alert('예약 또는 주문 완료자만 리뷰 작성이 가능합니다.');
    closeAll();
</script>
<% } else { %>
<div class="mainContainer">
    <form method="post" onsubmit="return checkRating()">
        <input type="hidden" name="rest" value="<%=restaurantCode%>">
        <label>음식: <select name="dish">
            <%
                rs = r.getMenus();
                if (hasReservation) {
                    while (rs.next()) { %>
            <option value="<%=rs.getString(1)%>"><%=rs.getString(2)%>
            </option>
            <% }
            } else {
                do {
                    while (rs.next()) {
                        if (orderSet.getString(3).equals(rs.getString(1))) { %>
            <option value="<%=orderSet.getString(3)%>"><%=rs.getString(2)%>
            </option>
            <%
                            }
                        }
                        rs.first();
                    } while (orderSet.next());
                }
            %>
        </select></label>
        <label>평점: ⭐ <input type="number" name="rating" min="0.0" max="5.0" step="0.01"/> / 5.0</label>
        <p>리뷰:</p>
        <textarea class="reviewText" name="reviewText" onkeyup="resize(this)" onkeydown="resize(this)"></textarea>
        <p>리뷰를 작성하면, 100포인트를 드립니다.</p>
        <div class="buttons">
            <input type="submit" value="제출하기" formaction="uploadReview.jsp">
        </div>
    </form>
</div>
</body>
</html>
<% }
} %>