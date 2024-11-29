<%@page import="DB.DBConnect"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@page import="java.sql.*"%>    
<%
	String sql = "SELECT M.M_NO, M.M_NAME, COUNT(V.M_NO) vote_count " +
			"FROM TBL_VOTE_202005 V " +
			"JOIN TBL_MEMBER_202005 M ON V.M_NO = M.M_NO " +
			"GROUP BY M.M_NO, M.M_NAME " +
			"ORDER BY vote_count DESC ";
		
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
		<h2>후보자등수</h2>
		<table class="table_line">
			<tr class="back_color">
				<th>후보번호</th>
				<th>성명</th>
				<th>총투표건수</th>
			</tr>
			<% while(rs.next()) { %>
				<tr class="center">
					<td><%=rs.getString("M_NO") %></td>
					<td><%=rs.getString("M_NAME") %></td>
					<td><%=rs.getString("vote_count") %></td>
				</tr>
			<% } %>
		</table>
	</section>
	<footer>
		<jsp:include page="layout/footer.jsp"></jsp:include>
	</footer>
</body>
</html>