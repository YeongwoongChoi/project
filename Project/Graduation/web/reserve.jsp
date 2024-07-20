<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="utils.DataBaseUtil" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="entity.Restaurant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding("UTF-8");
    String id = (String) session.getAttribute("sessionID");
    if (id == null) {
        response.sendRedirect("index.jsp");
    } else {
        int type = Integer.parseInt(request.getParameter("type"));

        PreparedStatement ps;
        Class.forName(DataBaseUtil.getDatabaseDriver());
        Connection conn = DataBaseUtil.getConnection();
        String sql = "";
        double rating = 0.0d;

        switch (type) {
            case DataBaseUtil.RESERVE_VIEW_DEFAULT:
            case DataBaseUtil.RESERVE_VIEW_DISHES:
                sql = "select * from restaurant;";
                break;
            case DataBaseUtil.RESERVE_VIEW_RATING:
                sql = "select * from restaurant where rating >= ?;";
                rating = Double.parseDouble(request.getParameter("rating"));
                break;
            case DataBaseUtil.RESERVE_VIEW_VISITORS:
                sql = "select restaurantCode, typeOfDishes, restaurantName, location, rating, phoneNumber, count(*) as c from restaurant join reserve using (restaurantCode) group by restaurantCode order by c desc, restaurantCode;";
                break;
        }
        ps = conn.prepareStatement(sql);
        if (type == DataBaseUtil.RESERVE_VIEW_RATING)
            ps.setDouble(1, rating);
        ResultSet rs = ps.executeQuery();
        final int columns = rs.getMetaData().getColumnCount();
        String[] row = new String[columns];
%>

<%!
    boolean needsToExpandColumn(final int type) {
        return type == DataBaseUtil.RESERVE_VIEW_VISITORS;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>예약 페이지</title>
    <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet"/>
    <link href="css/tableDesign.css" rel="stylesheet"/>
    <link href="css/styles.css" rel="stylesheet"/>
    <link href="css/font.css" rel="stylesheet"/>
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
</head>
<style> tr, th {
    padding-left: 1.8em;
    padding-right: 1.8em;
    text-align: center;
}

input[type=button] {
    width: auto;
    height: auto;
    border: 0;
    background-color: #E4EBF5;
    border-radius: 10px;
    box-shadow: .4rem .3rem 0.7rem #BEC5D0,
    -.2rem -.2rem .4rem #FBFBFB;

    color: #333333;
    font-size: 1em;
    font-weight: bold;
}

input[type=button]:active {
    box-shadow: inset -.3rem -.1rem 1.4rem #FBFBFB,
    inset .3rem .4rem .8rem #BEC5D0;
    cursor: pointer;
}
</style>
<body class="sb-nav-fixed">
<nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
    <!-- Navbar Brand-->
    <a class="navbar-brand ps-3" href="main.jsp">식당 예약 시스템</a>
    <!-- Sidebar Toggle-->
    <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle" href="#!"><i
            class="fas fa-bars"></i></button>
    <!-- Navbar Search-->
    <form class="d-none d-md-inline-block form-inline ms-auto me-0 me-md-3 my-2 my-md-0">
    </form>
    <!-- Navbar-->
    <ul class="navbar-nav ms-auto ms-md-0 me-3 me-lg-4">
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown"
               aria-expanded="false"><i class="fas fa-user fa-fw"></i></a>
            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                <li><a class="dropdown-item" href="myPage.jsp">Settings</a></li>
                <li>
                    <hr class="dropdown-divider"/>
                </li>
                <li><a class="dropdown-item" href="logOut.jsp">Logout</a></li>
            </ul>
        </li>
    </ul>
</nav>
<div id="layoutSidenav">
    <div id="layoutSidenav_nav">
        <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
            <div class="sb-sidenav-menu">
                <div class="nav">
                    <div class="sb-sidenav-menu-heading">Core</div>
                    <a class="nav-link" href="main.jsp">
                        <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                        메인 페이지
                    </a>
                    <div class="sb-sidenav-menu-heading">Interface</div>
                    <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapsePages"
                       aria-expanded="false" aria-controls="collapsePages">
                        <div class="sb-nav-link-icon"><i class="fas fa-book-open"></i></div>
                        목록
                        <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                    </a>
                    <div class="collapse" id="collapsePages" aria-labelledby="headingTwo"
                         data-bs-parent="#sidenavAccordion">
                        <nav class="sb-sidenav-menu-nested nav accordion" id="sidenavAccordionPages">
                            <a class="nav-link" href="reserve.jsp?type=0">식당 방문 예약</a>
                            <a class="nav-link" href="reserve.jsp?type=3">예약 주문</a>
                        </nav>
                    </div>
                    <div class="sb-sidenav-menu-heading">Addons</div>
                    <!--<a class="nav-link" href="charts.html">
                        <div class="sb-nav-link-icon"><i class="fas fa-chart-area"></i></div>
                        Charts
                    </a>
                    -->
                    <a class="nav-link" href="myPage.jsp">
                        <div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
                        마이 페이지
                    </a>
                </div>
            </div>
            <div class="sb-sidenav-footer">
                <div class="small">Logged in as: <a href="myPage.jsp"><%=id%>
                </a>
                </div>
            </div>
        </nav>
    </div>
    <div id="layoutSidenav_content">
        <main>
            <div class="container-fluid px-4">
                <h1 class="mt-4">예약 페이지</h1>
                <ol class="breadcrumb mb-4">
                    <% if (type == DataBaseUtil.RESERVE_VIEW_DISHES) { %>
                    <li class="breadcrumb-item active">식당별 예약 주문</li>
                    <% } else { %>
                    <li class="breadcrumb-item active">식당 조회 및 예약</li>
                    <% } %>
                </ol>

                <div class="card mb-4">
                    <div class="card-header">
                        <i class="fas fa-table me-1"></i>
                        <% if (type == DataBaseUtil.RESERVE_VIEW_RATING) { %>
                        평점이 <%=rating%> 이상인
                        <% } else if (type == DataBaseUtil.RESERVE_VIEW_VISITORS) { %>
                        예약 수가 높은 순서의
                        <% } %>
                        식당 목록
                    </div>
                    <div class="card-body">
                        <table id="restaurants">
                            <thead>
                            <tr>
                                <th>식당 코드</th>
                                <th>식당 이름</th>
                                <th>취급 음식</th>
                                <th>위치</th>
                                <% if (needsToExpandColumn(type)) { %>
                                <th>예약 수</th>
                                <% }
                                    if (type == DataBaseUtil.RESERVE_VIEW_DISHES) { %>
                                <th>주문하기</th>
                                <% } else { %>
                                <th>상세 정보</th>
                                <% } %>
                            </tr>
                            </thead>
                            <tbody>
                            <% while (rs.next()) {
                                for (int i = 0; i < columns; i++)
                                    row[i] = rs.getString(i + 1);
                                Restaurant r = new Restaurant(row);
                                session.setAttribute(row[0], r);
                            %>
                            <tr>
                                <td><%=r.getRestaurantCode()%>
                                </td>
                                <td><%=r.getRestaurantName()%>
                                </td>
                                <td><%=r.getTypeOfDishes()%>
                                </td>
                                <td><%=r.getLocation()%>
                                </td>
                                <% if (needsToExpandColumn(type)) { %>
                                <td><%=row[columns - 1]%>
                                </td>
                                <% } %>
                                <td>
                                    <% if (type == DataBaseUtil.RESERVE_VIEW_DISHES) { %>
                                    <input type="button" id="order" value="예약 주문"
                                           onclick="createWindow('orderDishes.jsp?rest=<%=row[0]%>', 1280, 720)">
                                    <% } else { %>
                                    <input type="button" id="detailedInfo" value="상세정보"
                                           onclick="createWindow('detailed.jsp?rest=<%=row[0]%>', 1280, 720)">
                                    <% } %>
                                </td>
                            </tr>
                            <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </main>
        <footer class="py-4 bg-light mt-auto">
            <div class="container-fluid px-4">
                <div class="d-flex align-items-center justify-content-between small">
                    <div class="text-muted">Yeongwoong Choi</div>
                </div>
            </div>
        </footer>
    </div>
</div>
<script>
    function createWindow(s, width = 640, height = 480) {
        const x = Math.ceil((window.screen.width - width) / 2);
        const y = Math.ceil((window.screen.height - height) / 2);
        window.open
        (s,
            'detailWindow',
            'width=' + width + ', height=' + height + ', left=' + x + ', top=' + y + ', location=no, status=no, scrollbars=no');
    }
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        crossorigin="anonymous"></script>
<script src="js/scripts.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>

<script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js"
        crossorigin="anonymous"></script>
<script src="js/datatables-simple-demo.js"></script>
</body>
</html>
<% } %>