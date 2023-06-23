<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
</style>

<meta charset="UTF-8">
<title>게시판 상세페이지입니다.</title>
</head>
<body>
		<p>글번호 <input type="text" name="bno" value="${articleVO.bno}" disabled>	
		<p>작성자 <input type="text" value="${articleVO.bwriter}" disabled>	
		<p>조회수 <input type="text" value="${articleVO.bcount}" disabled>	
		<p>제목 <input type="text" value="${articleVO.bname}" disabled>	
		<p>내용 <textarea cols="60" rows="20" style="resize: none;" disabled>${articleVO.bdetail}</textarea>	<br>

		<a href="${pageContext.request.contextPath}/#/${articleVO.bno}">답글달기</a>
		<a href="${pageContext.request.contextPath}/board/updateArticleForm?bno=${articleVO.bno}">글 수정하기</a>
		<a href="${pageContext.request.contextPath}/board/listBoard.do">게시판 목록</a>
		<a href="${pageContext.request.contextPath}/board/deleteArticle.do?bno=${articleVO.bno}">글 삭제</a>
</body>
</html>