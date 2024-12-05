<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@page import="java.sql.*"%>
<%@page import="DB.DBConnect"%>
<%
	String sql = "SELECT P_NO, P_NAME, " +
			"TO_CHAR(TO_DATE(P_BIRTH, 'YYYYMMDD'), 'YYYY\"년\"MM\"월\"DD\"일\"') P_BIRTH, " +
			"CASE P_GNEDER WHEN 'M' THEN '남' WHEN 'F' THEN '여' END P_GNEDER, " +
			"P_TEL1 || '-' || P_TEL2 || '-' || P_TEL3 P_TEL, " +
			"CASE P_CITY WHEN '10' THEN '서울' WHEN '20' THEN '경기' WHEN '30' THEN '강원' WHEN '40' THEN '대구' END P_CITY " +
			"FROM TBL_PATIENT_202004";

	Connection conn = DBConnect.getConnection();
	PreparedStatement ps = conn.prepareStatement(sql);
	ResultSet rs = ps.executeQuery();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/style.css?abc">
</head>
<body>
	<header>
		<jsp:include page="layout/header.jsp"></jsp:include>
	</header>
	<nav>
		<jsp:include page="layout/nav.jsp"></jsp:include>
	</nav>
	<section class="section">
		<h2>환자조회</h2>
		
		<table class="table_line">
			<tr class="black_color">
				<th>환자번호</th>
				<th>환자성명</th>
				<th>생년월일</th>
				<th>성별</th>
				<th>전화번호</th>
				<th>지역</th>
			</tr>
			<% while(rs.next()) { %>
			<tr class="center">
				<td><%= rs.getString("P_NO") %></td>
				<td><%= rs.getString("P_NAME") %></td>
				<td><%= rs.getString("P_BIRTH") %></td>
				<td><%= rs.getString("P_GNEDER") %></td>
				<td><%= rs.getString("P_TEL") %></td>
				<td><%= rs.getString("P_CITY") %></td>
			</tr>
			<% } %>
		</table>
	</section>
	<footer>
		<jsp:include page="layout/footer.jsp"></jsp:include>
	</footer>
</body>
</html>