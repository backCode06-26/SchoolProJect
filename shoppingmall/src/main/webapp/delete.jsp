<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="DB.DBConnect"%>
<%@ page import="java.sql.*"%>
    
    <%
             String sql ="delete from member_tbl_02 where custno ="
                        +Integer.parseInt(request.getParameter("d_custno"));
    
             Connection conn = DBConnect.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql);
	         pstmt.executeUpdate();    
    
    %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보삭제</title>
</head>
<body>
<jsp:forward page="member_list.jsp" />
</body>
</html>