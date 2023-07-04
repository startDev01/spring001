<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
	<script
			src="https://code.jquery.com/jquery-3.7.0.js"
			integrity="sha256-JlqSTELeR4TLqP0OG9dxM7yDPqX1ox/HfgiSLBj8+kM="
			crossorigin="anonymous"></script>
	<script>
		/* 버튼 클릭시 file 업로드 input 태그 추가 */
		var cnt = 1;
		var fileMax = 0;
		function fn_addFile() {
			if (fileMax < 3) {
				$("#d_file").append("<br>" + "<input type='file' name='file"+cnt+"' accept='.jpg, .jpeg, .png' />");
				fileMax++;
				cnt++;
			}
		}
	</script>
	<link rel="stylesheet" href="/resources/style/main.css">
<meta charset="UTF-8">
<title>글쓰기</title>
</head>
<body>
	<form action="${contextPath}/board/addArticle.done" method="post" enctype="multipart/form-data">
		<div>
			글 제목 : <input class="formInput" type="text" name="bname" required><br>
			작성자 : <input class="formInput" type="text" value="${member.userId}" disabled><br>
			<input class="formInput" type="text" name="bwriter" value="${member.userId}" hidden>
			글번호 : <input class="formInput" type="number" name="bno" value="${bno}" disabled>
			<input class="formInput" type="number" name="bno" value="${bno}" hidden>
			<br>
			<label for="notice">
				<input class="formInput" type="radio" id="notice" name="btype" value="notice">공지글
			</label>
			<label for="normal">
				<input class="formInput" type="radio" id="normal" name="btype" value="normal" checked>일반글<br>
			</label>
			글 내용 : <textarea rows="20" cols="60" name="bdetail" style="resize: none;" required></textarea><br>

			<input type="button" value="파일 추가" onclick="fn_addFile()"/>
			<div id="d_file"></div>

			<input type="submit" value="글쓰기">
		</div>
	</form>
</body>
</html>