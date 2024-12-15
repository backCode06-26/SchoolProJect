<%@page import="DB.DBConnect"%>
<%@page import="java.sql.*"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String sql = "SELECT MAX(RESVNO) + 1 FROM TBL_VACCRESV_202108";

	Connection conn = DBConnect.getConnection();
	PreparedStatement ps = conn.prepareStatement(sql);
	ResultSet rs = ps.executeQuery();
	
	rs.next();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/style.css?abc">
<script type="text/javascript">
	if(document.date.RESVNO) {
		alert("예약번호를 입력하지 않았습니다.");
		date.RESVNO.focus();
		return false;
	} else if(document.date.JUMIN) {
		alert("주민번호를 입력하지 않았습니다.");
		date.JUMIN.focus();
		return false;
	} else if(document.date.VCODE) {
		alert("백신코드를 입력하지 않았습니다.");
		date.VCODE.focus();
		return false;
	} else if(document.date.HOSPCODE) {
		alert("병원코드를 입력하지 않았습니다.");
		date.HOSPCODE.focus();
		return false;
	} else if(document.date.RESVDATE) {
		alert("예약날짜를 입력하지 않았습니다.");
		date.RESVDATE.focus();
		return false;
	} else if(document.date.RESVTIME) {
		alert("예약시간를 입력하지 않았습니다.");
		date.RESVTIME.focus();
		return false;
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
		<h2>백신 예약</h2>
		
		<form action="join_p.jsp" method="post" name="data" onsubmit="return checkValue()">
			<table class="table_line">
				<tr>
					<th>예약번호</th>
					<td><input type="text" name="RESVNO" value="<%= rs.getString(1)%>" readonly>예)20210011</td>
				</tr>
				<tr>
					<th>주민번호</th>
					<td><input type="text" name="JUMIN">예)790101-1111111</td>
				</tr>
				<tr>
					<th>백신코드</th>
					<td>
						<select name="VCODE">
							<option value="V001">A</option>
							<option value="V002">B</option>
							<option value="V003">C</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>병원코드</th>
					<td>
						<input type="radio" value="H001" name="HOSPCODE">가_병원
						<input type="radio" value="H002" name="HOSPCODE">나_병원
						<input type="radio" value="H003" name="HOSPCODE">다_병원
						<input type="radio" value="H004" name="HOSPCODE">라_병원
					</td>
				</tr>
				<tr>
					<th>예약날짜</th>
					<td><input type="text" name="RESVDATE">예)20210101</td>
				</tr>
				<tr>
					<th>예약시간</th>
					<td><input type="text" name="RESVTIME">예)0930, 1130</td>
				</tr>
				<tr class="center">
					<td colspan="2">
						<input type="submit" value="등록">
						<input type="button" value="취소">
						<input type="button" value="백신예약현황조회" onclick="location.href=''">
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