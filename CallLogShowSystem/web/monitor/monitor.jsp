<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>通话记录</title>
    <link rel="stylesheet" type="text/css" href="../css/my.css">
    <script type="text/javascript" src="../js/jquery-3.2.0.min.js"></script>
    <script type="text/javascript">

        //定义函数
        function getHearbeatInfo() {
            $("#t1 tbody").empty();
            $.getJSON("/json/monitor/getMonotorInfo", function (data) {
                $.each(data, function (i, obj) {
                    var str = "<tr><td>" + obj.ip + "</td>";
                    str = str + "<td>" + obj.flag + "</td>"
                    str = str + "<td>" + obj.timestamp + "</td>"
                    str = str + "</tr>";
                    $("#t1 tbody").append(str);
                });
            });
        }

        $(function () {
            setInterval(getHearbeatInfo, 1000);
        })
    </script>
</head>
<body>
<table id="t1" border="1px" class="t-1" style="width: 800px">
    <thead>
    <tr>
        <td>ip</td>
        <td>flag</td>
        <td>时间戳</td>
    </tr>
    </thead>
    <tbody>
    </tbody>
</table>
</body>
</html>
