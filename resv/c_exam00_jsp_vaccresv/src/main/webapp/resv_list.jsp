<%@page import="DB.DBConnect"%>
<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String sql = "SELECT " +
		    "H.HOSPCODE, " +
		    "H.HOSPNAME, " +
		    "COUNT(V.HOSPCODE) RESV_COUNT " +
		"FROM TBL_HOSP_202108 H " +
		"LEFT OUTER JOIN TBL_VACCRESV_202108 V ON V.HOSPCODE = H.HOSPCODE " +
		"GROUP BY H.HOSPCODE, H.HOSPNAME " +
		"order by COUNT(V.HOSPCODE) desc";

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
		<h2>백신예약현환</h2>
		
		<table class="table_line">
			<tr>
				<th>병원지역</th>
				<th>병원지역명</th>
				<th>접종예약건수</th>
			</tr>
			<%	
				int result = 0;
				while(rs.next()){ 
			%>
			<tr class="center">
				<td><%= rs.getString("HOSPCODE") %></td>
				<td><%= rs.getString("HOSPNAME") %></td>
				<td><%= rs.getString("RESV_COUNT") %></td>
			</tr>
			<%
			result += Integer.parseInt(rs.getString("RESV_COUNT"));
			} 
			%>
			<tr class="center">
				<th colspan="2">총합</th>
				<td><%= result %></td>
			</tr>
		</table>
	</section>
	<footer>
		<jsp:include page="layout/footer.jsp"></jsp:include>
	</footer>	
</body>
</html>