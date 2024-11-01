<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" href="css/style.css?abc">
<script type="text/javascript">
function checkValue() {
    if (!document.data.custno.value) {
        alert("회원번호를 입력하세요");
        document.data.custno.focus();
        return false;
    }
    alert("조회가 완료되었습니다.");
    return true;
}
</script>

</head>
<body>
<header>
    <jsp:include page="layout/header.jsp"></jsp:include>
</header>
<nav>
    <jsp:include page="layout/nav.jsp"></jsp:include>
</nav>
<section class="section">
    <h2>회원조회</h2><br>
    <form name="data" action="member_search_list.jsp" method="post" onsubmit="return checkValue()">
        <table class="table_line">
        <tr>
            <th colspan="2">회원 번호</th>
            <td><input type="text" name="custno"></td>
        </tr>
        <tr class="center">
            <td colspan="3">
                <input type="button" value="취소" onclick="location.href='member_list.jsp'">
                <input type="submit" value="제출">
            </td>
        </tr>
    </table>
    </form>
</section>
<footer>
    <jsp:include page="layout/footer.jsp"></jsp:include>
</footer>
</body>
</html>
