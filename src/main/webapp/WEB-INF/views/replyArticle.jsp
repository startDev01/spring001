<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/board/replyArticle.do" method="post" name="boardVO">
        글 제목 : <input type="text" name="bname" required><br>
        작성자 : <input type="text" value="${member.userId}" disabled><br>
        <input type="text" name="bwriter" value="${member.userId}" hidden>
        글번호 : <input type="number" name="bno" value="${replyBno}" disabled>
        <input type="number" name="bno" value="${replyBno}" hidden>
        <br>
        글 내용 : <textarea rows="20" cols="60" name="bdetail" style="resize: none;" required></textarea><br>
        <input type="number" name="bParentNO" value="${bno}" hidden>
        <input type="text" name="btype" value="normal" hidden>
        <input type="submit" value="답글쓰기">
    </form>
</body>
</html>
