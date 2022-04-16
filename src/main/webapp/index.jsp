<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--<h3><a href="http://localhost:8080/user/some1.do"> 点击访问 some1.do </a></h3>--%>
<%--<h3><a href="http://localhost:8080/user/some2.do"> 点击访问 some1.do </a></h3>--%>
<%--<h3><a href="http://localhost:8080/user/some3.do?name=zhangsan&age=12"> 点击访问 some3.do 并附带参数</a></h3>--%>
<%--<form action="/user/some4.do" method="post">--%>
<%--    姓名：<input type="text" name="rName">--%>
<%--    年龄：<input type="text" name="rAge">--%>
<%--    <input type="submit" value="提交数据">--%>
<%--</form></br>--%>
<form action="/user/some5.do" method="post">
    姓名：<input type="text" name="rName">
    年龄：<input type="text" name="rAge">
    <input type="submit" value="提交数据">
</form>
</body>
</html>