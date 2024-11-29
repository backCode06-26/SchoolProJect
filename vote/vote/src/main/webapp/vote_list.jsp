<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.sql.*" %>
<%@ page import="DB.DBConnect" %>
<%
	String sql = "SELECT " +
			    "V_NAME, " +
			    "CASE  " + 
			        "WHEN SUBSTR(V_JUMIN, 7, 1) IN ('1', '3') THEN '19' || SUBSTR(V_JUMIN, 1, 2) || '년' || SUBSTR(V_JUMIN, 3, 2) || '월' || SUBSTR(V_JUMIN, 5, 2) || '일' " +
			        "WHEN SUBSTR(V_JUMIN, 7, 1) IN ('2', '4') THEN '20' || SUBSTR(V_JUMIN, 1, 2) || '년' || SUBSTR(V_JUMIN, 3, 2) || '월' || SUBSTR(V_JUMIN, 5, 2) || '일' " +
			    "END birth_date, " +
			    "'만' || TRUNC(MONTHS_BETWEEN(SYSDATE, " + 
			            "TO_DATE( " +
			                "CASE " +
			                    "WHEN SUBSTR(V_JUMIN, 7, 1) IN ('1', '2') THEN '19' || SUBSTR(V_JUMIN, 1, 2) || SUBSTR(V_JUMIN, 3, 2) || SUBSTR(V_JUMIN, 5, 2) " +
			                    "WHEN SUBSTR(V_JUMIN, 7, 1) IN ('3', '4') THEN '20' || SUBSTR(V_JUMIN, 1, 2) || SUBSTR(V_JUMIN, 3, 2) || SUBSTR(V_JUMIN, 5, 2) " +
			                "END, " + 
			                "'YYYYMMDD' " +
			            ") " +
			        ") / 12 " +
			    ") || '세' age, " +
			    "CASE " +
			        "WHEN SUBSTR(V_JUMIN, 7, 1) IN ('1', '3') THEN '남' " +
			        "WHEN SUBSTR(V_JUMIN, 7, 1) IN ('2', '4') THEN '여' " +
			    "END gender, " +
			    "M_NO, " +
			    "SUBSTR(V_TIME, 1, 2) || ':' || SUBSTR(V_TIME, 3, 2) V_TIME, " +
			    "CASE " +
			        "WHEN V_CONFIRM = 'Y' THEN '확인' " + 
			        "WHEN V_CONFIRM = 'N' THEN '미확인' " +
			    "END V_CONFIRM " +
			"FROM TBL_VOTE_202005 " +
			"WHERE V_AREA = '제1투표장' ";
	
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
		<h2>후보검수조회</h2>
		
		<table class="table_line">
			<tr class="back_color">
				<th>성명</th>
				<th>생년월일</th>
				<th>나이</th>
				<th>성별</th>
				<th>후보번호</th>
				<th>투표</th>
				<th>유권자확인</th>
			</tr>
			<% while(rs.next()) { %>
				<tr class="center">
					<td><%=rs.getString("V_NAME") %></td>
					<td><%=rs.getString("birth_date") %></td>
					<td><%=rs.getString("age") %></td>
					<td><%=rs.getString("gender") %></td>
					<td><%=rs.getString("M_NO") %></td>
					<td><%=rs.getString("V_TIME") %></td>
					<td><%=rs.getString("V_CONFIRM") %></td>
				</tr>
			<% } %>
		</table>
	</section>
	<footer>
		<jsp:include page="layout/footer.jsp"></jsp:include>
	</footer>
</body>
</html>