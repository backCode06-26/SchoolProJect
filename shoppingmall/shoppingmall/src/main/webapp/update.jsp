<%@page import="java.sql.*"%>
<%@page import="DB.DBConnect"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%
    String sql = "select custno, custname, phone, address, " +
            	 "to_char(joindate, 'yyyy-mm-dd') as joindate, grade, city " +
            	 "from member_tbl_02 where custno = ?";
		int custno = Integer.parseInt(request.getParameter("check_custno"));
    
    	Connection conn = DBConnect.getConnection();
    	PreparedStatement ps = conn.prepareStatement(sql);
    	ps.setInt(1, custno);
    	ResultSet rs = ps.executeQuery();
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href=" css/style.css?abc">
<script type="text/javascript">
function checkValue() {
    if(!document.data.custname.value) {
        alert("성명을 입력하세요");
        document.data.custname.focus();
        return false;
    } else if(!document.data.phone.value) {
        alert("전화번호를 입력하세요");
        document.data.phone.focus();
        return false;
    } else if(!document.data.address.value) {
        alert("주소를 입력하세요");
        document.data.address.focus();
        return false;
    } else if(!document.data.joindate.value) {
        alert("가입날짜를 입력하세요");
        document.data.joindate.focus();
        return false;
    } else if(!document.data.grade.value) {
        alert("등급을 입력하세요");
        document.data.grade.focus();
        return false;
    } else if(!document.data.city.value) {
        alert("도시를 입력하세요");
        document.data.city.focus();
        return false;
    }
    alert("등록이 완료되었습니다.");
    return true;
}

function checkDel(custno) {
    var msg = "정말 삭제하시겠습니까?";
    if (confirm(msg)) {
        alert("삭제되었습니다.");
        location.href = 'delete.jsp?check_custno=' + custno;
    } else {
        alert("삭제가 취소되었습니다.");
    }
}
</script>
</head>
<body>
<header>
	<jsp:include page="/layout/header.jsp"></jsp:include>
</header>
<nav>
	<jsp:include page="/layout/nav.jsp"></jsp:include>
</nav>
<section class="section">
	<h2>홈쇼핑 회원 정보 수정</h2><br>
	
	<form name="data" action="update_p.jsp" method="post" onsubmit="checkValue()">
		<table class="table_line">
			<%
			if(rs.next()) {
			%>
			<tr>
				<th>회원번호</th>
				<td><input type="text" name="custno" value="<%=rs.getString("custno")%>" readonly></td>
			</tr>
			<tr>
				<th>회원성명</th>
				<td><input type="text" name="custname" value="<%=rs.getString("custname")%>"></td>
			</tr>
			<tr>
				<th>회원전화</th>
				<td><input type="text" name="phone" value="<%=rs.getString("phone")%>"></td>
			</tr>
			<tr>
				<th>회원주소</th>
				<td><input type="text" name="address" value="<%=rs.getString("address")%>"></td>
			</tr>
			<tr>
				<th>가입일자</th>
				<td><input type="text" name="joindate" value="<%=rs.getString("joindate")%>"></td>
			</tr>
			<tr>
				<th>고객등급(A:VIP,B:일반,C:도시)</th>
				<td><input type="text" name="grade" value="<%=rs.getString("grade")%>"></td>
			</tr>
			<tr>
				<th>도시코드</th>
				<td><input type="text" name="city" value="<%=rs.getString("city")%>"></td>
			</tr>
			<tr class="center">
				<td colspan="2">
					<input type="submit" value="수정">
					<input type="button" value="삭제" onclick="checkDel(<%=rs.getString("custno")%>)">
					<input type="button" value="조회" onclick="location.href='member_list.jsp'">
				</td>
			</tr>
			<%} %>
		</table>
	</form>
</section>
<footer>
	<jsp:include page="/layout/footer.jsp"></jsp:include>
</footer>
</body>
</html>