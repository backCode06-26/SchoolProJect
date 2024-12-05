<%@page import="DB.DBConnect"%>
<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String sql = "SELECT P.P_CITY, " +
		"	CASE P.P_CITY " +
		"	WHEN '10' THEN '서울' " +
		"	WHEN '20' THEN '경기' " +
		"	WHEN '30' THEN '강원' " +
		"	WHEN '40' THEN '대구' " +
		"END CITY_NAME, " +
		"COUNT(T.P_NO) COUNT_RESULT " +
		"FROM TBL_PATIENT_202004 P " +
		"JOIN TBL_RESULT_202004 T ON P.P_NO = T.P_NO " +
		"GROUP BY P.P_CITY";

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
		<h2>(지역별)검사건수통계</h2>
		
		<table class="table_line">
			<tr class="black_color">
				<th>지역코드</th>
				<th>지역명</th>
				<th>검사건수</th>
			</tr>
			<% while(rs.next()) { %>
			<tr class="center">
				<td><%=rs.getString("P_CITY") %></td>
				<td><%=rs.getString("CITY_NAME") %></td>
				<td><%=rs.getString("COUNT_RESULT") %></td>
			</tr>
			<% } %>
		</table>
	</section>
	<footer>
		<jsp:include page="layout/footer.jsp"></jsp:include>
	</footer>
</body>
</html>