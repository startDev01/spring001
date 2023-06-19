<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<style>
	p {
		font-size: 20px;
	}
	input {
		height: 20px;
		font-size: 20px;
	}
	textarea {
		font-size: 16px;
	}
	.submit-btn {
		height: 50px;
		border: none;
	}
	.submit-btn:hover {
		cursor: pointer;
	}
</style>

<meta charset="UTF-8">
<title>게시판 수정</title>
</head>
<body>
	<form action="${contextPath}/board/updateArticle?bno=${articleVO.bno}" method="post">
		<p>글번호 <input type="text" name="bno" value="${articleVO.bno}" disabled>
		<p>작성자 <input type="text" value="${articleVO.bwriter}" disabled>	
		<p>조회수 <input type="text" value="${articleVO.bcount}" disabled>
		<p>제목 <input type="text" name="bname" value="${articleVO.bname}">
		<%-- <p>내용 <input type="text" name="bdetail" value="${articleVO.bdetail}"> --%>
		<p>내용 <textarea cols="60" rows="20" name="bdetail" style="resize: none;">${articleVO.bdetail}</textarea>	
		
		<br>
		
		<input class="submit-btn" type="submit" value="수정하기">
		<a href="${pageContext.request.contextPath}/board/listBoard.do">게시판 목록</a>
	</form>
</body>
</html>