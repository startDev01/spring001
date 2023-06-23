<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <script>
    var msg = "${msg}";
    var url = "${url}";
      alert(msg);
      location.href = url;
  </script>
  <meta charset="UTF-8">
  <title>로그인 필요</title>
</head>
<body>


</body>
</html>