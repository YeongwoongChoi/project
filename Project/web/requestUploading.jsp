<%--
  Created by IntelliJ IDEA.
  User: 278ch
  Date: 2024-05-27
  Time: 오후 1:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.List" %>
<%@ page import="utils.DataBaseUtil" %>

<%
    String id = (String) session.getAttribute("sessionID");

    if (id == null) {
        response.sendRedirect("index.jsp");
    } else {
        String filePath = DataBaseUtil.getFileUploadPath();
        DiskFileUpload upload = new DiskFileUpload();
        upload.setSizeMax(DataBaseUtil.MAX_UPLOAD_SIZE);        // 10 * 1024 * 1024(Byte) == 10 MB
        upload.setSizeThreshold(DataBaseUtil.MAX_UPLOAD_SIZE / 2);      // 5 MB can be stored at main memory.
        upload.setRepositoryPath(filePath);

        try {
            List<FileItem> items = upload.parseRequest(request);
            for (FileItem f : items) {
                if (!f.isFormField()) {
                    String fileName = f.getName();
                    int i = fileName.lastIndexOf('.');
                    fileName = fileName.replace(fileName.substring(0, i), id);
                    File file = new File(filePath + "/" + fileName);
                    f.write(file);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace()[0]);
        }

%>
<script>
    function closeAll() {
        opener.location.reload();
        parent.close();
        window.close();
        self.close();
    }
</script>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
</head>
<body>
<script> closeAll(); </script>
</body>
</html>
<% } %>