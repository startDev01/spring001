<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="/resources/main.css">
<style>
	input {
		font-size:40px;
		text-align: center;
	}
	
	a {
		text-decoration: none;
		color: black;
	}
	
	.notice {
		font-weight: bold;
	}
	
	.main_list {
		font-weight: bold;
		font-size: 30px;
		padding: 15px;
	}
	
	.articleForm {
		font-weight: bold;
		font-size: 30px;
		border-bottom: 2px solid gray;
		color: teal;

	}
	
	  .pageInfo{
      list-style : none;
      display: inline-block;
    margin: 50px 0 0 100px;      
  }
  .pageInfo li{
      float: left;
    font-size: 20px;
    margin-left: 18px;
    padding: 7px;
    font-weight: 500;
  }

  .startSpan {
	  padding-right: 30px;
  }

  .endSpan {
	  padding-left: 20px;
  }

	a:link {color:black; text-decoration: none;}
	a:visited {color:black; text-decoration: none;}
	a:hover {color:black; text-decoration: underline;}
	
	.active {
		background-color: #cdd5ec;
	}
</style>

<!-- jQuery 사용!!! -> $ <- -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script>
	function fn_articleForm(isLogOn, articleForm, loginForm) {
		if(isLogOn != '' && isLogOn != 'false') {
			location.href = articleForm;
		} else {
			alert('로그인  후 글쓰기가 가능합니다.')
			location.href = loginForm+'?action=/board/articleForm.do';
		}
	}
</script>

<meta charset="UTF-8">
<title>게시판 목록입니다.</title>
</head>
<body>
	<table border="1" align="center" width="80%">
		<tr align="center" bgcolor="#E3F6CE">
			<td class="main_list" colspan="5">게시판 목록
		</tr>
		<tr align="center" bgcolor="lightgreen">
			<td><b>글번호</b></td>
			<td><b>글이름</b></td>
			<td><b>작성일</b></td>
			<td><b>조회수</b></td>
			<td><b>작성자</b></td>
		</tr>
		
		<%--<c:set var="num" value="${pageMaker.total - ((pageMaker.cri.pageNum-1) * 10) }"/>--%>
		
		<c:forEach var="notice" items="${noticeList}">
		<tr class="notice" align="center" bgcolor="lightgray">
			<td>${notice.bno}</td>
			<td><a href="${pageContext.request.contextPath}/board/viewArticle.do?
						bno=${notice.bno}">${notice.bname}</a></td>
			<td>${notice.bcount}</td>
			<td>${notice.bwriter}</td>
		</tr>
	</c:forEach>
	
	<c:forEach var="board" items="${boardList}">
		<tr class="normal" align="center">
			<td width="10%">${board.bno}</td>
			<td class="bnameAlign" align="left" width="55%">
				<span class="startSpan"></span>
				<c:choose>
					<c:when test="${board.level > 1}">
						<c:forEach begin="1" end="${board.level}" step="1">
							<span class="endSpan"></span>
						</c:forEach>
						<span style="font-size: 12px;">[답변]</span>
						<a href="${pageContext.request.contextPath}/board/viewArticle.do?
							bno=${board.bno}">${board.bname}</a>
					</c:when>
					<c:otherwise>
						<a href="${pageContext.request.contextPath}/board/viewArticle.do?
						bno=${board.bno}">${board.bname}</a>
					</c:otherwise>
				</c:choose>
			</td>
			<td width="10%">${board.bwritedate}</td>
			<td width="10%">${board.bcount}</td>
			<td width="15%">${board.bwriter}</td>
		</tr>
		<c:set var="num" value="${num-1 }"></c:set>
	</c:forEach>
	</table>
	<br>
	<div align="center">
		<%--<a class="articleForm" href="${pageContext.request.contextPath}/board/articleForm.do" align="center">글쓰기</a>--%>
		<a class="articleForm" href="javascript:fn_articleForm()('${isLogOn}',
		'${pageContext.request.contextPath}/board/articleForm.do',
		'${pageContext.request.contextPath}/member/login')" align="center">글쓰기</a>
		<div class="pageInfo_wrap" >
        <div class="pageInfo_area">
			<ul id="pageInfo" class="pageInfo">

							<!-- 이전페이지 버튼 -->
				<%--<c:if test="${pageMaker.prev}">
				  <li class="pageInfo_btn previous"><a href="${pageMaker.startPage-1}">Previous</a></li>
				</c:if>--%>

							<!-- 페이지 이동 버튼 -->
				<%--<c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
				   <li class="pageInfo_btn ${pageMaker.cri.pageNum == num ? "active":"" }"><a href="${num}">${num}</a></li>
				</c:forEach>--%>

				<!-- 다음페이지 버튼 -->
				<%--<c:if test="${pageMaker.next}">
					<li class="pageInfo_btn next"><a href="${pageMaker.endPage + 1 }">Next</a></li>
				</c:if>  --%>


			</ul>
        </div>
    </div>
		
		<form id="moveForm" class="moveForm" method="get">
			<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum }">
			<input type="hidden" name="amount" value="${pageMaker.cri.amount }">
		</form>
	</div>
	
	<!-- Script Code!! -->
	<script>
		console.log("안녕하세요.");
		
		let moveForm = $("#moveForm");
		
		$(".move").on("click", function(e){
			e.preventDefault();
			moveForm.append("<input type='hidden' name='bno' value='"+ $(this).attr("href")+ "'>");
			moveForm.attr("action", "/board/get");
			moveForm.submit();
		});
	
		$(".pageInfo a").on("click", function(e){
			 e.preventDefault();
       moveForm.find("input[name='pageNum']").val($(this).attr("href"));
       moveForm.attr("action", "${pageContext.request.contextPath}/board/listBoard.do");
       moveForm.submit();
	  });	
	
	
	</script>
	
	
</body>
</html>