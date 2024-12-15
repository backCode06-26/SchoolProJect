<%@page import="DB.DBConnect"%>
<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String sql = "SELECT " + 
		"    V.RESVNO, " +
		"    J.MANE, " +
		"    CASE " +
		"        WHEN SUBSTR(J.JUMIN, 8, 1) IN ('1', '3') THEN '남' " +
		"        WHEN SUBSTR(J.JUMIN, 8, 1) IN ('2', '4') THEN '여' " +
		"    END GENDER, " +
		"    H.HOSPNAME, " +
		"    TO_CHAR(TO_DATE(V.RESVDATE, 'YYYYMMDD'), 'YYYY\"년\"MM\"월\"DD\"일\"') RESVDATE, " +
		"    TO_CHAR(TO_DATE(V.RESVTIME, 'HH24MI'), 'HH24:MI') RESVTIME, " +
		"   V.VCODE, " +
		"    (SELECT CITY_NAME FROM CITY_CODE_TBL_20 WHERE CITY = H.HOSPADDR) CITY_NAME " +
		"FROM TBL_JUMIN_202108 J " +
		"JOIN TBL_VACCRESV_202108 V ON J.JUMIN = V.JUMIN " +
		"JOIN TBL_HOSP_202108 H ON V.HOSPCODE = H.HOSPCODE " +
		"WHERE RESVNO = ?";

	String resvno = request.getParameter("check_RESVNO");

	Connection conn = DBConnect.getConnection();
	PreparedStatement ps = conn.prepareStatement(sql);
	ps.setString(1, resvno);
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
		<% if(rs.next()) { %>
		<h2>예약번호 <%= resvno %>님의 예약 조회</h2>
		
		<table class="table_line">
			<tr>
				<th>예약번호</th>
				<th>성명</th>
				<th>성별</th>
				<th>병원이름</th>
				<th>예약날짜</th>
				<th>예약시간</th>
				<th>백신코드</th>
				<th>병원지역</th>
			</tr>
			<tr class="center">
				<td><%=rs.getString("RESVNO") %></td>
				<td><%=rs.getString("MANE") %></td>
				<td><%=rs.getString("GENDER") %></td>
				<td><%=rs.getString("HOSPNAME") %></td>
				<td><%=rs.getString("RESVDATE") %></td>
				<td><%=rs.getString("RESVTIME") %></td>
				<td><%=rs.getString("VCODE") %></td>
				<td><%=rs.getString("CITY_NAME") %></td>
			</tr>
			<tr class="center">
				<td colspan="8">
					<input type="button" onclick="location.href='research.jsp'" value="취소 후 다시 조회">
				</td>
			</tr>
		</table>
			
		<% } else { %>
		<h2>예약번호 <%= resvno %>로 조회된 결과가 없습니다.</h2>
		
		<table class="table_line">
			<tr>
				<td>
					<input type="button" onclick="location.href='research.jsp'" value="취소 후 다시 조회">
					<input type="button" onclick="location.href='index.jsp'" value="홈으로">
				</td>
			</tr>	
		</table>
		<% } %>
		
	</section>
	<footer>
		<jsp:include page="layout/footer.jsp"></jsp:include>
	</footer>
</body>
</html>