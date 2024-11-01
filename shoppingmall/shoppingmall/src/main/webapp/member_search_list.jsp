<%@page import="DB.DBConnect"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@page import="java.sql.*"%>
    
<%
	request.setCharacterEncoding("UTF-8");
	int custno = Integer.parseInt(request.getParameter("custno"));
	
	String sql = "select custno, custname, phone, address, " +
	             "to_char(joindate, 'yyyy-mm-dd') joindate, " +
	             "case when grade = 'A' then 'VIP' " +
	             "when grade = 'B' then '일반' " +
	             "when grade = 'C' then '직원' end as grade, " +
	             "city from member_tbl_02 where custno = " + custno;
	
	Connection conn = DBConnect.getConnection();
	PreparedStatement ps = conn.prepareStatement(sql);
	
	ResultSet rs = ps.executeQuery();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href=" css/style.css?abc">
</head>
<body>
<header>
	<jsp:include page="layout/header.jsp"></jsp:include>
</header>
<nav>
	<jsp:include page="layout/nav.jsp"></jsp:include>
</nav>
<section class="section">
	<h2>회원 조회 결과</h2><br>
	<%
	if(rs.next()) {
	%>
		<table class="table_line">
			<tr>
				<th>회원번호</th>
				<th>회원성명</th>
				<th>회원전화</th>
				<th>회원주소</th>
				<th>가입일자</th>
				<th>고객등급[A:VIP,B:일반,C:직원]</th>
				<th>도시코드</th>
			</tr>
			<tr class="center">
				<td><%=rs.getString("custno") %></td>
				<td><%=rs.getString("custname") %></td>
				<td><%=rs.getString("phone") %></td>
				<td><%=rs.getString("address") %></td>
				<td><%=rs.getString("joindate") %></td>
				<td><%=rs.getString("grade") %></td>
				<td><%=rs.getString("city") %></td>
			</tr>
			<tr class="center">
				<td colspan="7">
					<input type="button" value="홈으로" onclick="location.href='index.jsp'">
				</td>
			</tr>
		</table>
	<%
	} else {
		String cnstno = request.getParameter("custno");
	%>
		<div align="center">
			<p>회원번호 <%=cnstno %>의 회원 정보가 없습니다.</p>
			<input type="button" value="다시조회" onclick="location.href='member_search.jsp'">
		</div>
	<%
	}
	%>
	
</section>
<footer>
	<jsp:include page="layout/footer.jsp"></jsp:include>
</footer>
</body>
</html>