<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="utils.DataBaseUtil" %>
<%@ page import="entity.Restaurant" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding("UTF-8");
    String id = (String) session.getAttribute("sessionID");
    Class.forName(DataBaseUtil.getDatabaseDriver());
    Connection conn = DataBaseUtil.getConnection();

    if (id == null) {
        response.sendRedirect("index.jsp");
    } else {
        String[] row = new String[6];
        String sql = "select * from restaurant;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            for (int i = 0; i < 6; i++)
                row[i] = rs.getString(i + 1);

            Restaurant r = new Restaurant(row);
            session.setAttribute(row[0], r);
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
    <title>메인 페이지</title>
    <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet"/>
    <link href="css/styles.css" rel="stylesheet"/>
    <link href="css/tableDesign.css" rel="stylesheet"/>
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    <link href="css/font.css" rel="stylesheet"/>
    <script src="js/jquery-3.5.1.min.js"></script>
</head>
<style> tr, th {
    padding-left: 1.8em;
    padding-right: 1.8em;
    text-align: center;
}

.header {
    width: 100%;
    display: grid;
    grid-template-columns: 1fr 1fr;
}

.searchArea, .backgroundArea {
    margin: 10px;
    padding: 0.5em;
    width: 95%;
}

.backgroundArea > img {
    width: 95%;
    height: 300px;
}

</style>
<script>
    let checkRange = x => x > 0.0 && x <= 5.0;

    function setReferenceRating() {
        let rating = Number(prompt('기준 평점을 입력하세요. (0부터 5 사이)', '4.2'));

        if (!checkRange(rating)) {
            if (confirm('올바른 값이 아닙니다. 일반 조회로 이동합니다.'))
                location.href = 'reserve.jsp?type=0';
        } else
            location.href = 'reserve.jsp?type=1&rating=' + rating.toFixed(2);
    }
</script>
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
                    <!--
                    <div class="collapse" id="collapseLayouts" aria-labelledby="headingOne"
                         data-bs-parent="#sidenavAccordion">
                        <nav class="sb-sidenav-menu-nested nav">
                            <a class="nav-link" href="layout-static.html">Static Navigation</a>
                            <a class="nav-link" href="layout-sidenav-light.html">Light Sidenav</a>
                        </nav>
                    </div>
                    -->
                    <div class="collapse" id="collapsePages" aria-labelledby="headingTwo"
                         data-bs-parent="#sidenavAccordion">
                        <nav class="sb-sidenav-menu-nested nav accordion" id="sidenavAccordionPages">
                            <a class="nav-link" href="reserve.jsp?type=0">식당 방문 예약</a>
                            <a class="nav-link" href="reserve.jsp?type=3">예약 주문</a>
                            <!--<div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>-->
                        </nav>
                    </div>
                    <div class="sb-sidenav-menu-heading">Addons</div>
                    <!-- <a class="nav-link" href="charts.html">
                        <div class="sb-nav-link-icon"><i class="fas fa-chart-area"></i></div>
                        Charts
                    </a> -->
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
                <div class="header">
                    <div class="searchArea">
                        <h1 class="mt-4">식당 예약 시스템</h1>
                        <ol class="breadcrumb mb-4">
                            <li class="breadcrumb-item active">예약 간편 조회</li>
                        </ol>
                        <div class="row">
                            <div class="col-xl-auto col-md-auto">
                                <div class="card bg-primary text-white mb-4">
                                    <div class="card-body">평점 높은 식당 조회</div>
                                    <div class="card-footer d-flex align-items-center justify-content-between">
                                        <a class="small text-white stretched-link" href="#" onclick="setReferenceRating()">
                                            상세 보기</a>
                                        <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xl-auto col-md-auto">
                                <div class="card bg-warning text-white mb-4">
                                    <div class="card-body">예약 수 기준 조회</div>
                                    <div class="card-footer d-flex align-items-center justify-content-between">
                                        <a class="small text-white stretched-link" href="reserve.jsp?type=2">상세 보기</a>
                                        <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xl-auto col-md-auto">
                                <div class="card bg-success text-white mb-4">
                                    <div class="card-body">리뷰 작성</div>
                                    <div class="card-footer d-flex align-items-center justify-content-between">
                                        <a class="small text-white stretched-link" href="reserve.jsp?type=0">예약 페이지로 가기</a>
                                        <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                                    </div>
                                </div>
                            </div>
                            <!--
                            <div class="col-xl-3 col-md-6">
                                <div class="card bg-success text-white mb-4">
                                    <div class="card-body">Success Card</div>
                                    <div class="card-footer d-flex align-items-center justify-content-between">
                                        <a class="small text-white stretched-link" href="#">View Details</a>
                                        <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xl-3 col-md-6">
                                <div class="card bg-danger text-white mb-4">
                                    <div class="card-body">Danger Card</div>
                                    <div class="card-footer d-flex align-items-center justify-content-between">
                                        <a class="small text-white stretched-link" href="#">View Details</a>
                                        <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                                    </div>
                                </div>
                            </div>
                            -->
                        </div>
                    </div>
                    <div class="backgroundArea">
                        <img src="img/main_background.jpg"/>
                    </div>
                </div>
                <div class="card mb-4">
                    <div class="card-header">
                        <i class="fas fa-table me-1"></i>
                        예약 주문 목록 (최대 5개)
                    </div>
                    <div class="card-body">
                        <table id="orders">
                            <thead>
                            <tr>
                                <th>식당 코드</th>
                                <th>식당 이름</th>
                                <th>주문한 음식</th>
                                <th>수량</th>
                                <th>방문 예정 시각</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%
                                sql = "select * from customerorder where customerIdentifier = ? limit 5;";
                                ps = conn.prepareStatement(sql);
                                ps.setString(1, id);
                                rs = ps.executeQuery();

                                while (rs.next()) {
                                    row[0] = rs.getString(2);
                                    sql = "select restaurantName from restaurant where restaurantCode = ?;";
                                    ps = conn.prepareStatement(sql);
                                    ps.setString(1, row[0]);
                                    ResultSet restaurantName = ps.executeQuery();
                                    if (restaurantName.next())
                                        row[1] = restaurantName.getString(1);
                                    sql = "select dishName from dish where dishIdentifier = ?;";
                                    ps = conn.prepareStatement(sql);
                                    ps.setString(1, rs.getString(3));
                                    ResultSet dishName = ps.executeQuery();
                                    if (dishName.next())
                                        row[2] = dishName.getString(1);
                                    
                                    row[3] = rs.getString(4);
                                    row[4] = DataBaseUtil.getDateTimeFormat(rs.getDate(5).toString(), rs.getTime(5).toString());
                            %>
                            <tr>
                                <% for (int i = 0; i < 5; i++) { %>
                                <td><%=row[i]%>
                                </td>
                                <% } %>
                            </tr>
                            <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="card mb-4">
                    <div class="card-header">
                        <i class="fas fa-table me-1"></i>
                        예약 목록 (최대 5개)
                    </div>
                    <div class="card-body">
                        <table id="reservations">
                            <thead>
                            <tr>
                                <th>식당 코드</th>
                                <th>식당 이름</th>
                                <th>예약 일시</th>
                                <th>예약 인원</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%
                                sql = "select * from reserve where customerIdentifier = ? limit 5;";
                                ps = conn.prepareStatement(sql);
                                ps.setString(1, id);
                                rs = ps.executeQuery();

                                while (rs.next()) {
                                    row[0] = rs.getString(2);
                                    sql = "select restaurantName from restaurant where restaurantCode = ?;";
                                    ps = conn.prepareStatement(sql);
                                    ps.setString(1, row[0]);
                                    ResultSet restaurantName = ps.executeQuery();
                                    if (restaurantName.next())
                                        row[1] = restaurantName.getString(1);
                                    row[2] = DataBaseUtil.getDateTimeFormat(rs.getDate(3).toString(), rs.getTime(3).toString());
                                    row[3] = rs.getString(4);
                            %>
                            <tr>
                                <% for (int i = 0; i < 4; i++) { %>
                                <td><%=row[i]%>
                                </td>
                                <% } %>
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
