<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<%@page import="DB.DBConnect"%>
<%@page import="java.sql.*"%> 
<%
    	String sql = "select mb.custno, mb.custname, " +
    			"	case mb.grade when 'A' then 'VIP' " +
    			"	       when 'B' then '일반' " +
    			"	       when 'C' then '직원' end grade, " +
    			"	sum(mo.price) price " +
    			"from money_tbl_02 mo, member_tbl_02 mb " +
    			"where mo.custno = mb.custno " +
    			"group by mb.custno, mb.custname, mb.grade " +
    			"order by price desc";
    
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
		<h2>회원매출조회</h2><br>
		<table class="table_line">
			<tr>
				<th>회원번호</th>
				<th>회원성명</th>
				<th>고객등급</th>
				<th>매출</th>
			</tr>
			<%
				int result = 0;
			
				while(rs.next()) {
			%>
			<tr class="center">
				<td><%=rs.getString("custno") %></td>
				<td><%=rs.getString("custname") %></td>
				<td><%=rs.getString("grade") %></td>
				<td><%=rs.getString("price") %></td>
			</tr>
			<%
				result += rs.getInt("price");
			
				}
			%>
			<tr class="center">
				<td colspan="3">총합</td>
				<td><%=result %></td>
			</tr>
		</table>
	</section>
	<footer>
		<jsp:include page="layout/footer.jsp"></jsp:include>
	</footer>
</body>
</html>