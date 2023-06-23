<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/resources/main.css">
    <style>
        input {
            font-size: 40px;
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

        .pageInfo {
            list-style: none;
            display: inline-block;
            margin: 50px 0 0 100px;
        }

        .pageInfo li {
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

        .hitBoard {
            font-size: 10px;
            color: red;
        }

        .newBoard {
            font-size: 10px;
            color: green;
        }

        a:link {
            color: black;
            text-decoration: none;
        }

        a:visited {
            color: black;
            text-decoration: none;
        }

        a:hover {
            color: black;
            text-decoration: underline;
        }

        .active {
            background-color: #cdd5ec;
        }
    </style>

    <!-- jQuery 사용!!! -> $ <- -->
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script>
        function fn_articleForm(isLogOn, articleForm, loginForm) {
            if (isLogOn != '' && isLogOn != 'false') {
                location.href = articleForm;
            } else {
                alert('로그인  후 글쓰기가 가능합니다.')
                location.href = loginForm + '?action=/board/articleForm.do';
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
	<%-- 1. 공지글 !! --%>
    <c:forEach var="notice" items="${noticeList}">
        <tr class="notice" align="center" bgcolor="lightgray">
                <%--<td>${notice.bno}</td>--%>
            <td></td>
            <td align="left"><span class="startSpan"></span>
                <a href="${pageContext.request.contextPath}/board/viewArticle.do?
						bno=${notice.bno}">${notice.bname}</a></td>
            <td>${notice.bwritedate}</td>
            <td>${notice.bcount}</td>
            <td>${notice.bwriter}</td>
        </tr>
    </c:forEach>

	<%-- 2. 일반글 !! --%>
    <c:forEach var="board" items="${boardList}" varStatus="boardNum">
        <%-- NEW 날짜 변수 지정 --%>
        <jsp:useBean id="now" class="java.util.Date"/>
        <fmt:parseNumber value="${now.time / (1000 * 60 * 60 * 24)}" integerOnly="true" var="timeNow" scope="request"/>
        <fmt:parseNumber value="${board.bwritedate.time / (1000 * 60 * 60 * 24)}" integerOnly="true" var="timeBoard"
                         scope="request"/>

        <tr class="normal" align="center">
                <%--<td width="10%">${board.bno}</td>--%>
            <td width="10%">${boardNum.count}</td>
            <td class="bnameAlign" align="left" width="55%">
                <span class="startSpan"></span>
                <c:choose>
                    <c:when test="${board.level > 1}">
                        <c:forEach begin="1" end="${board.level}" step="1">
                            <span class="endSpan"></span>
                        </c:forEach>

                        <span style="font-size: 12px;">[답변]</span>
                        <a href="${pageContext.request.contextPath}/board/viewArticle.do?
							bno=${board.bno}">
                                <%-- NEW 글 --%>
                            <c:if test="${timeNow - timeBoard <= 1}">
                                <span class="newBoard">NEW</span>
                            </c:if>
                                <%-- HIT 글 --%>
                            <c:if test="${board.bcount > 10}">
                                <span class="hitBoard">HIT</span>
                            </c:if>
                                ${board.bname}
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/board/viewArticle.do?
						bno=${board.bno}">
                                <%-- NEW 글 --%>
                            <c:if test="${timeNow - timeBoard <= 1}">
                                <span class="newBoard">NEW</span>
                            </c:if>
                                <%-- HIT 글 --%>
                            <c:if test="${board.bcount > 10}">
                                <span class="hitBoard">HIT</span>
                            </c:if>
                                ${board.bname}
                        </a>
                    </c:otherwise>
                </c:choose>
            </td>
            <td width="12%">${board.bwritedate}</td>
            <td width="8%">${board.bcount}</td>
            <td width="15%">${board.bwriter}</td>
        </tr>
        <c:set var="num" value="${num-1 }"></c:set>
    </c:forEach>
</table>
<br>
<div align="center">
    <%--<a class="articleForm" href="${pageContext.request.contextPath}/board/articleForm.do" align="center">글쓰기</a>--%>
    <a class="articleForm" href="${pageContext.request.contextPath}/board/articleForm.do" align="center">글쓰기</a>
</div>
</body>
</html>