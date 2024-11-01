<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="DB.DBConnect"%>
<%@ page import="java.sql.*"%>

<%
    String sql = "select custno, custname, phone, address, " +
    			 "to_char(joindate, 'yyyy-mm-dd') as joindate, " +
                 "case when grade = 'A' then 'VIP' when grade = 'B' then '일반' " + 
                 "when grade = 'C' then '직원' end as grade, city " +
                 "from member_tbl_02 " +
				 "order by custno";
    
    Connection conn = DBConnect.getConnection();
    PreparedStatement ps = conn.prepareStatement(sql);
    ResultSet rs = ps.executeQuery();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보</title>
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
    	<h2>회원 목록 조회 / 수정</h2><br>
        <table class="table_line">
	    	<tr>
	        	<th>회원번호</th>
	            <th>회원성명</th>
	            <th>전화번호</th>
	            <th>주소</th>
	            <th>가입일자</th>
	            <th>고객등급</th>
	            <th>거주지역</th>
			</tr>
            <%
                while(rs.next()) {
            %>
            <tr class="center">
            	<td><a href="update.jsp?check_custno=<%= rs.getInt("custno") %>"><%= rs.getInt("custno") %></a></td>
                <td><%= rs.getString("custname") %></td>
                <td><%= rs.getString("phone") %></td>
                <td><%= rs.getString("address") %></td>
                <td><%= rs.getString("joindate") %></td>
                <td><%= rs.getString("grade") %></td>
                <td><%= rs.getString("city") %></td>
            </tr>
            <%
                }
            %>
        </table>
    </section>
    <footer>
        <jsp:include page="layout/footer.jsp"></jsp:include>
    </footer>
</body>
</html>
