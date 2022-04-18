<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%--<script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.5.1/jquery.js"></script>--%>
    <script src="js/jquery341.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#btn").click(function () {
                $.ajax({
                    url: "user/some12.do",
                    data: {
                        name: "smith",
                        age: 18
                    },
                    type: "post",
                    dataType: "text",
                    success: function (data) {
                        alert(data)
                    }
                })
            })
        })
    </script>
<body>
<button id="btn">点击发起Ajax请求</button>
<br>
<br>
<form action="/user/some13.do" method="post">
    <button type="submit">测试请求转发</button>
</form>
<form action="/user/some14.do" method="post">
    <button type="submit">测试重定向</button>
</form>
<form action="/user/some15.do" method="post">
    <button type="submit">SpringMVC请求转发</button>
</form>
<form action="/user/some16.do" method="post">
    <button type="submit">SpringMVC重定向</button>
</form>
</body>
</html>