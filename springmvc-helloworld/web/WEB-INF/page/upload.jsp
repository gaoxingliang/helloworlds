<%--
  Created by IntelliJ IDEA.
  User: edward.gao
  Date: 2019/8/6
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>文件上传</title>
</head>
<body>
<form action="/upload" method="post" enctype="multipart/form-data">
  <input type="file" name="picture">
  <input type="submit" value="上 传">
</form>
</body>
</html>
