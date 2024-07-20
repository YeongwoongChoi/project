<%--
  Created by IntelliJ IDEA.
  User: 278ch
  Date: 2024-05-27
  Time: 오후 1:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>사진 업로드</title>
    <script type="text/javascript" src="js/jquery-3.5.1.min.js"></script>
    <link href="css/font.css" rel="stylesheet"/>
</head>

<script>
    function checkExtension() {
        var form = document.uploadForm;
        var fileName = form.fileName.value;
        if (fileName.substring(fileName.length - 3) !== "jpg") {
            alert('jpg만 업로드 가능합니다.');
            return false;
        }
        return true;
    }
    function closeAll() {
        parent.close();
        window.close();
        self.close();
    }
</script>
<body>
    <form method="post" name="uploadForm" enctype="multipart/form-data" onsubmit="return checkExtension()">
        <div class="photo">
            <input type="file" name="fileName" accept=".jpg">
            <p>* jpg 파일만 업로드 가능합니다.</p>
        </div>
        <div class="buttons">
            <input type="submit" value="업로드" formaction="requestUploading.jsp">
            <input type="button" value="창 닫기" onclick="closeAll()">
        </div>

    </form>
</body>
</html>
