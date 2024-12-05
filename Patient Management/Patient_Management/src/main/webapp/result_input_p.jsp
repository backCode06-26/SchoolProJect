<%@page import="DB.DBConnect"%>
<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String sql = "INSERT INTO TBL_RESULT_202004 VALUES " +
				 "(?,?,?,?,?,?)";

	Connection conn = DBConnect.getConnection();
	PreparedStatement ps = conn.prepareStatement(sql);
	ps.setString(1, request.getParameter("P_NO"));
	ps.setString(2, request.getParameter("T_CODE"));
	ps.setString(3, request.getParameter("T_SDATE"));
	ps.setString(4, request.getParameter("T_STATUS"));
	ps.setString(5, request.getParameter("T_LDATE"));
	ps.setString(6, request.getParameter("T_RESULT"));
	ResultSet rs = ps.executeQuery();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:forward page="index.jsp"></jsp:forward>
</body>
</html>