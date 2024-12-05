<%@page import="DB.DBConnect"%>
<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String sql = "SELECT R.P_NO, P.P_NAME,L.T_NAME, " +
			"TO_CHAR(R.T_SDATE, 'YYYY-MM-DD') T_SDATE, " +
			"CASE R.T_STATUS WHEN '1' THEN '검사중' WHEN '2' THEN '검사완료' END T_STATUS, " +
			"TO_CHAR(R.T_LDATE, 'YYYY-MM-DD') T_LDATE,  " +
			"CASE R.T_RESULT WHEN 'X' THEN '미입력' WHEN 'P' THEN '양성' WHEN 'N' THEN '음성' END T_STATUS_RESULT " +
			"FROM TBL_RESULT_202004 R " +
			"JOIN TBL_PATIENT_202004 P ON P.P_NO = R.P_NO " +
			"JOIN TBL_LAB_TEST_202004 L ON L.T_CODE = R.T_CODE " +
			"ORDER BY R.P_NO";

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
		<h2>검사결과조회</h2>
		
		<table class="table_line">
			<tr class="black_color">
				<th>환자번호</th>
				<th>환자명</th>
				<th>검사일</th>
				<th>검사시작일</th>
				<th>검사상태</th>
				<th>검사종료일</th>
				<th>검사결과</th>
			</tr>
			<% while(rs.next()) { %>
			<tr class="center">
				<td><%=rs.getString("P_NO") %></td>
				<td><%=rs.getString("P_NAME") %></td>
				<td><%=rs.getString("T_NAME") %></td>
				<td><%=rs.getString("T_SDATE") %></td>
				<td><%=rs.getString("T_STATUS") %></td>
				<td><%=rs.getString("T_LDATE") %></td>
				<td><%=rs.getString("T_STATUS_RESULT") %></td>
			</tr>
			<% } %>
		</table>
	</section>
	<footer>
		<jsp:include page="layout/footer.jsp"></jsp:include>
	</footer>
</body>
</html>