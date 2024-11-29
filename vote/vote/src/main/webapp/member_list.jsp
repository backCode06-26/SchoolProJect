<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<%@ page import="java.sql.*" %>
<%@ page import="DB.DBConnect" %>
<%
	String sql = "select M.M_NO, M.M_NAME, P.P_NAME, " +
				 "case M.P_SCHOOL " +
			 	 "when '1' then '고졸' " +
  	    		 "when '2' then '학사' " +
	  			 "when '3' then '석사' " +
				 "when '4' then '박사' " +
				 "end P_SCHOOL, " +
				 "M.M_JUMIN, M.M_CITY, P_TEL1||'-'||P_TEL2||'-'||P_TEL3 phone " +
				 "from TBL_MEMBER_202005 M, TBL_PARTY_202005 P " +
				 "where M.P_CODE = P.P_CODE";

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
		<h2>후보조회</h2>
		
		<table class="table_line">
			<tr class="back_color">
				<th>후보번호</th>
				<th>성명</th>
				<th>소속정당</th>
				<th>학력</th>
				<th>주민번호</th>
				<th>지역구</th>
				<th>대표전화</th>
			</tr>
			<% while(rs.next()) { %>
				<tr class="center">
					<td><%=rs.getString("M_NO") %></td>
					<td><%=rs.getString("M_NAME") %></td>
					<td><%=rs.getString("P_NAME") %></td>
					<td><%=rs.getString("P_SCHOOL") %></td>
					<td><%=rs.getString("M_JUMIN") %></td>
					<td><%=rs.getString("M_CITY") %></td>
					<td><%=rs.getString("phone") %></td>
				</tr>
			<% } %>
		</table>
	</section>
	<footer>
		<jsp:include page="layout/footer.jsp"></jsp:include>
	</footer>
</body>
</html>