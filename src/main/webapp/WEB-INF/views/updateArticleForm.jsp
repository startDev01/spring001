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
	.update-btn {
		height: 48px;
		position: relative;
		bottom: 15px;
		font-size: 16px;
	}
	.img-box {
		display: block;
		margin-bottom: 10px;
	}

	.disabledInput {
		background-color: #ebebeb;
		border-radius: 4px;
		border: 1px solid gray;
	}

	.disabledInput:focus {
		border: 1px solid gray;
		outline: none;
	}
</style>

<meta charset="UTF-8">
<title>게시판 수정</title>
</head>
<body>
	<form action="${contextPath}/board/updateArticle?bno=${articleVO.bno}" method="post" enctype="multipart/form-data">
		<p>글번호 <input type="text" class="disabledInput" name="bno" value="${articleVO.bno}" readonly>
		<p>작성자 <input type="text" class="disabledInput" name="bwriter" value="${articleVO.bwriter}" readonly>
		<p>조회수 <input type="text" class="disabledInput" name="bcount" value="${articleVO.bcount}" readonly>
		<p>제목 <input type="text" name="bname" value="${articleVO.bname}">
		<%-- <p>내용 <input type="text" name="bdetail" value="${articleVO.bdetail}"> --%>
		<p>내용 <textarea cols="60" rows="8" name="bdetail" style="resize: none;">${articleVO.bdetail}</textarea>
		<input type="text" name="bwritedate" value="${articleVO.bwritedate}" hidden>
		<input type="text" name="btype" value="${articleVO.btype}" hidden>
		
		<br>

		<c:if test="${imageMap != null}">
		<p>이미지</p>
		</c:if>
		<c:forEach var="imageFileName" items="${imageMap.imageList}" varStatus="status">
			<%--			<img src="<c:url value="/resources/images/${imageFileName}"/>" alt="" width="48">  <br>--%>
			<div class="img-box">
				<input type="file" class="update-btn" name="file${status.count}" accept=".jpg, .jpeg, .png">
				<img src="${pageContext.request.contextPath}/download?imageFileName=${imageFileName}" alt="" width="48">  <br>
			</div>
		</c:forEach>
		
		<input class="submit-btn" type="submit" value="수정하기">
		<a href="${pageContext.request.contextPath}/board/listBoard.do">게시판 목록</a>
	</form>
</body>
</html>