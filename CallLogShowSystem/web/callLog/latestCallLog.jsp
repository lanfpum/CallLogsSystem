<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>通话记录</title>
    <link rel="stylesheet" type="text/css" href="../css/my.css">
</head>
<body>
    <c:if test="${callLogs == null}">
        无记录！
    </c:if>
    <c:if test="${callLogs != null}">
        <table id="t1" border="1px" class="t-1" style="width: 800px">
            <tr>
                <td>电话1</td>
                <td>电话2</td>
                <td>通话时间</td>
                <td>通话时长</td>
            </tr>
            </tr>
            <c:forEach items="${callLogs}" var="log">
                <tr>
                    <td><c:out value="${log.caller}"/></td>
                    <td><c:out value="${log.callee}"/></td>
                    <td><c:out value="${log.callTime}"/></td>
                    <td><c:out value="${log.callDuration}"/></td>
                </tr>
            </c:forEach>

        </table>
    </c:if>

</body>
</html>