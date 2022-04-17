<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.5.1/jquery.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#btn").click(function () {
                $.ajax({
                    url: "user/some9.do",
                    data: {
                        name: "smith",
                        age: 18
                    },
                    type: "post",
                    dataType: "json",
                    success: function (resp) {
                        alert(resp.name + resp.age)
                    }
                })
            })
        });
    </script>
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
<%--<form action="/user/some5.do" method="post">--%>
<%--    姓名：<input type="text" name="rName">--%>
<%--    年龄：<input type="text" name="rAge">--%>
<%--    <input type="submit" value="提交数据">--%>
<%--</form>--%>
<%--<form action="/user/some6.do" method="post">--%>
<%--    姓名：<input type="text" name="name">--%>
<%--    年龄：<input type="text" name="age">--%>
<%--    <input type="submit" value="提交数据">--%>
<%--</form>--%>
<%--<form action="/user/some6.do" method="post">--%>
<%--    姓名：<input type="text" name="name">--%>
<%--    年龄：<input type="text" name="age">--%>
<%--    <input type="submit" value="提交数据">--%>
<%--</form>--%>
<%--<form action="/user/some7.do" method="post">--%>
<%--    姓名：<input type="text" name="name">--%>
<%--    年龄：<input type="text" name="age">--%>
<%--    <input type="submit" value="提交数据">--%>
<%--</form>--%>
<button id="btn">点击发起Ajax请求</button>
</body>
</html>