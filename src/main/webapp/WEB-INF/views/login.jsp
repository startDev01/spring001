<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>
	<form action="${contextPath}/member/login" method="post">
	<div>
		아이디 : <input type="text" name="userId" required><br>
		비밀번호 : <input type="text" name="userPwd" required><br>
		
		<input type="submit" value="로그인">
	</div>
	</form>
</body>
</html>