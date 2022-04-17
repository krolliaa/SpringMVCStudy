<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.5.1/jquery.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#btn").click(function () {
                $.ajax({
                    url: "user/some11.do",
                    data: {
                        name: "smith",
                        age: 18
                    },
                    type: "post",
                    dataType: "json",
                    success: function (data) {
                        $.each(data, function (i, n) {
                            alert(n.name + "---" + n.age)
                        })
                    }
                })
            })
        })
    </script>
<body>
<button id="btn">点击发起Ajax请求</button>
</body>
</html>