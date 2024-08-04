<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="utils.DataBaseUtil" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.sql.Time" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="entity.Reservation" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="java.sql.SQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding("UTF-8");
    String id = (String) session.getAttribute("sessionID");
    if (id == null) {
        response.sendRedirect("index.jsp");
    } else {
        PreparedStatement ps;
        Class.forName(DataBaseUtil.getDatabaseDriver());
        Connection conn = DataBaseUtil.getConnection();

        String sql;
        ResultSet rs;
        String[] row = new String[7];
        LinkedList<Reservation> reservations = new LinkedList<>();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>마이 페이지</title>
    <script type="text/javascript" src="js/jquery-3.5.1.min.js"></script>
    <link href="css/datatables.min.css" rel="stylesheet">
    <link href="css/myPage.css" rel="stylesheet">
    <script src="js/datatables.min.js"></script>
    <link href="css/font.css" rel="stylesheet"/>
    <link href="css/styles.css" rel="stylesheet"/>
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
</head>
<script>
    window.onload = function () {
        const idTag = $('input[name=userID]');
        idTag.css('backgroundColor', '#dfdfdf').css('border', '1px solid').attr('readonly', true);

    };

    $(document).ready(function () {
        $('#orders').DataTable(
            {
                ordering: true,
                searching: true,
                bAutoWidth: false,
                columnDefs: [
                    {orderable: false, targets: 7}
                ],
                lengthMenu: [5, 10, 25, 50]
            });

        $('#reservation').DataTable(
            {
                ordering: true,
                searching: true,
                bAutoWidth: false,
                columnDefs: [
                    {orderable: false, targets: 4}
                ],
                lengthMenu: [5, 10, 25, 50]
            });
    });

    function cancel(s, dateTime, dishId, type) {
        const selected = (type === 1 ? confirm("예약이 취소됩니다.") : confirm("주문이 취소됩니다."));
        let params = 'rest=' + s + '&date=' + dateTime.trim() + '&type=' + type;
        if (type === 0)
		params += ('&did=' + $('input[name=dishID][value=' + dishId + ']').val());
        if (selected) {
            location.href = 'checkCancellation.jsp?' + params;
            return true;
        }
        return false;
    }

    function createWindow(s, width = 640, height = 480) {
        const x = Math.ceil((window.screen.width - width) / 2);
        const y = Math.ceil((window.screen.height - height) / 2);
        window.open
        (s,
            'modifyingWindow',
            'width=' + width + ', height=' + height + ', left=' + x + ', top=' + y + ', location=no, status=no, scrollbars=no');
    }

    function hasAllElements() {
        const age = $('input[name=age]').val();
        const phoneNumber = $('input[name=phoneNumber]').val();
        const phoneNumberRegex = /^01([0|1|6|7|8|9])-([0-9]{3,4})-([0-9]{4})$/;

        if (age.trim().length === 0 || phoneNumber.trim().length === 0) {
            alert('필수 입력 항목입니다.');
            return false;
        }
        if (!phoneNumberRegex.test(phoneNumber)) {
            alert('전화번호를 형식에 맞게 써주세요.');
            return false;
        }
        if (parseInt(age) < 19) {
            alert('나이가 올바르지 않습니다.');
            return false;
        }
        return true;
    }

    function setDefaultImage(element) {
        element.src = "img/default.png";
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
                    <div class="collapse" id="collapsePages" aria-labelledby="headingTwo"
                         data-bs-parent="#sidenavAccordion">
                        <nav class="sb-sidenav-menu-nested nav accordion" id="sidenavAccordionPages">
                            <a class="nav-link" href="reserve.jsp?type=0">식당 방문 예약</a>
                            <a class="nav-link" href="reserve.jsp?type=3">예약 주문</a>
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
                <h1 class="mt-4">마이 페이지</h1>
                <ol class="breadcrumb mb-4">
                    <li class="breadcrumb-item active">개인정보 수정 및 예약내역 조회</li>
                </ol>

                <div class="card mb-4">
                    <div class="card-header">
                        <i class="fas fa-table me-1"></i>
                        내 정보
                    </div>
                    <%
                        sql = "select * from customer where customerIdentifier=?;";
                        PreparedStatement psmt = conn.prepareStatement(sql);
                        psmt.setString(1, id);
                        ResultSet account = psmt.executeQuery();
                        account.next();
                    %>
                    <div class="card-body">
                        <div class="card-column">
                            <h3>내 정보 수정</h3>
                            <form class="form" method="post" onsubmit="return hasAllElements()">
                                <div id="accountInfo">
                                    <label>ID <input type="text" value="<%=id%>" name="userID"/></label>
                                    <label>비밀번호 <input type="button" id="modifyingPassword" value="비밀번호 변경"
                                                       onclick="createWindow('modifyPwd.jsp', 600, 200)"/></label>
                                    <label>성별
                                        <select name="sex">
                                            <option value="Male">남자</option>
                                            <option value="Female">여자</option>
                                        </select>
                                    </label>
                                    <label>나이 <input type="text" name="age" value="<%=account.getInt(5)%>"/></label>
                                    <label>전화번호 <input type="tel" name="phoneNumber" placeholder="(ex. 010-1234-5678)"/></label>
                                </div>
                                <div class="submitArea">
                                    <input type="submit" name="confirmModification" value="정보 변경"
                                           formaction="checkModification.jsp?type=0">
                                </div>
                            </form>
                        </div>
                        <div class="card-column">
                            <h3>내 정보</h3>
                            <div class="account">
                                <div class="basicInfo">
                                    <p>이름: <%=account.getString(3)%>
                                    </p>
                                    <p>성별: <%=DataBaseUtil.getLocalizedSexName(account.getString(4))%>
                                    </p>
                                    <p>나이: <%=account.getInt(5)%>
                                    </p>
                                    <p>적립 포인트: <%=account.getInt(6)%>
                                    </p>
                                    <%
                                        sql = "select phoneNumber from customercontact where customerIdentifier=?;";
                                        psmt = conn.prepareStatement(sql);
                                        psmt.setString(1, id);
                                        account = psmt.executeQuery();
                                    %>
                                    <div class="contacts">
                                        <p>전화번호</p>
                                        <div class="contactsArea">
                                            <% while (account.next()) { %>
                                            <p><%=account.getString(1)%>
                                            </p>
                                            <% } %>
                                        </div>
                                    </div>
                                </div>
                                <div class="imageAndUnregister">
                                    <div class="imageArea">
                                        <img class="profileImage" width="150" height="180" src="uploaded/<%=id%>.jpg"
                                             onerror="setDefaultImage(this)"/>
                                    </div>
                                    <div class="buttonArea">
                                        <input type="button" name="uploadPhoto" value="사진 업로드"
                                               onclick="createWindow('uploadPhoto.jsp')">
                                        <input type="button" name="unregisterAccount" value="회원 탈퇴"
                                               onclick="createWindow('unregister.jsp', 500, 200)">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="card mb-4">
                    <div class="card-header">
                        <i class="fas fa-table me-1"></i>
                        주문 목록
                    </div>
                    <div class="card-body">
                        <table id="orders">
                            <thead>
                            <tr>
                                <th>식당 코드</th>
                                <th>식당 이름</th>
                                <th>주문한 음식</th>
                                <th>가격($)</th>
                                <th>수량</th>
                                <th>소계</th>
                                <th>방문 예정 시각</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <%
                                sql = "select * from customerorder where customerIdentifier = ?;";
                                ps = conn.prepareStatement(sql);
                                ps.setString(1, id);
                                rs = ps.executeQuery();
                                while (rs.next()) {
                            %>
                            <tr>
                                <td><%=rs.getString(2)%>
                                </td>
                                <%
                                    sql = "select restaurantName from restaurant where restaurantCode = ?;";
                                    ps = conn.prepareStatement(sql);
                                    ps.setString(1, rs.getString(2));
                                    ResultSet restaurantName = ps.executeQuery();
                                    restaurantName.next();
                                %>
                                <td><%=restaurantName.getString(1)%>
                                </td>
                                <%
                                    sql = "select dishName, dishPrice from dish where dishIdentifier = ?;";
                                    ps = conn.prepareStatement(sql);
                                    ps.setString(1, rs.getString(3));
                                    ResultSet dish = ps.executeQuery();
                                    dish.next();
                                %>
                                <input type="hidden" name="dishID" value="<%=rs.getString(3)%>">
                                <td><%=dish.getString(1)%>
                                </td>
                                <td>
                                    <%=dish.getDouble(2)%>
                                </td>
                                <td><%=rs.getInt(4)%>
                                </td>
                                <td>
                                    <%= dish.getDouble(2) * (double) rs.getInt(4) %>
                                </td>
                                <td><%=DataBaseUtil.getDateTimeFormat(rs.getDate(5).toString(), rs.getTime(5).toString())%>
                                </td>
                                <td><input type="button" value="주문 취소" onclick="cancel('<%=rs.getString(2)%>',
									   '<%=DataBaseUtil.replaceSpace(DataBaseUtil.getDateTimeFormat(rs.getDate(5).toString(), rs.getTime(5).toString()))%>', '<%=rs.getString(3)%>', 0)">
                                </td>
                            </tr>
                            <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="card mb-4">
                    <div class="card-header">
                        <i class="fas fa-table me-1"></i>
                        예약 목록
                    </div>
                    <div class="card-body">
                        <table id="reservation">
                            <thead>
                            <tr>
                                <th>식당 코드</th>
                                <th>식당 이름</th>
                                <th>예약 일시</th>
                                <th>예약 인원</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <%
                                sql = "select * from reserve where customerIdentifier=?;";
                                ps = conn.prepareStatement(sql);
                                ps.setString(1, id);
                                rs = ps.executeQuery();
                                try {
                                    while (rs.next()) {
                                        row[0] = rs.getString(2);
                                        sql = "select restaurantName from restaurant where restaurantCode=?;";
                                        ps = conn.prepareStatement(sql);
                                        ps.setString(1, row[0]);
                                        ResultSet tmp = ps.executeQuery();
                                        tmp.next();
                                        row[1] = tmp.getString(1);
					row[3] = rs.getString(4);
                                       	row[2] = DataBaseUtil.getDateTimeFormat(rs.getDate(3).toString(), rs.getTime(3).toString());	    
                                        reservations.add(new Reservation(row));
                                    }
                                } catch (SQLException e) {
                                    System.out.println(e.getStackTrace()[0]);
                                }

                                for (Reservation r : reservations) { %>
                            <tr>
                                <td><%=r.getCode()%>
                                </td>
                                <td><%=r.getName()%>
                                </td>
                                <td><%=r.getReservedTime()%>
                                </td>
                                <td><%=r.getNumbers()%>
                                </td>
                                <td>
                                    <input type="button" id="cancellation" value="예약 취소"
                                           onclick="cancel('<%=r.getCode()%>',
                                                   '<%=DataBaseUtil.replaceSpace(r.getReservedTime())%>', 0, 1)">
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

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        crossorigin="anonymous"></script>
<script src="js/scripts.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>

</body>
</html>
<% } %>
