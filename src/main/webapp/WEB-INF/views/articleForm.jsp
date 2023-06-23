<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>
</head>
<body>
	<form action="${contextPath}/board/addArticle.done" method="post">
		글 제목 : <input type="text" name="bname" required><br>
		작성자 : <input type="text" value="${member.userId}" disabled><br>
		<input type="text" name="bwriter" value="${member.userId}" hidden>
		글번호 : <input type="number" name="bno" value="${bno}" disabled><br>
		<label for="notice">
			<input type="radio" id="notice" name="btype" value="notice">공지글
		</label>
		<label for="normal">
			<input type="radio" id="normal" name="btype" value="normal" checked>일반글<br>
		</label>
		글 내용 : <textarea rows="20" cols="60" name="bdetail" style="resize: none;" required></textarea><br>
		<input type="submit" value="글쓰기">
	</form>
</body>
</html>