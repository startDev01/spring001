<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="/resources/style/main.css">
<meta charset="UTF-8">
<title>글쓰기</title>
</head>
<body>
	<form action="${contextPath}/board/addArticle.done" method="post">
		<div>
			글 제목 : <input class="formInput" type="text" name="bname" required><br>
			작성자 : <input class="formInput" type="text" value="${member.userId}" disabled><br>
			<input class="formInput" type="text" name="bwriter" value="${member.userId}" hidden>
			글번호 : <input class="formInput" type="number" name="bno" value="${bno}" disabled><br>
			<label for="notice">
				<input class="formInput" type="radio" id="notice" name="btype" value="notice">공지글
			</label>
			<label for="normal">
				<input class="formInput" type="radio" id="normal" name="btype" value="normal" checked>일반글<br>
			</label>
			글 내용 : <textarea rows="20" cols="60" name="bdetail" style="resize: none;" required></textarea><br>
			<input type="submit" value="글쓰기">
		</div>
	</form>
</body>
</html>