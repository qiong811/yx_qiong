<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<html>
<body>
<h2>Hello World!</h2>
<form action="${pageContext.request.contextPath}/file/upload" enctype="multipart/form-data" method="post">
    <input type="file" name="photo"><br>
    <input type="submit" value="提交">
</form>
<hr>
<a href="${pageContext.request.contextPath}/file/download?fileName=b.txt">b.txt</a>
</body>
</html>
