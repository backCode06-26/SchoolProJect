<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@page import="DB.DBConnect"%>
<%@page import="java.sql.*"%>
    
<%
	request.setCharacterEncoding("utf-8");

    String sql = "update member_tbl_02 set custname = ?, phone = ?, " + 
                 "address = ?, joindate = ?, grade = ?, city = ? where custno = ?";

    Connection conn = DBConnect.getConnection();
    PreparedStatement ps = conn.prepareStatement(sql);
    
    ps.setString(1, request.getParameter("custname"));
    ps.setString(2, request.getParameter("phone"));
    ps.setString(3, request.getParameter("address"));
    ps.setString(4, request.getParameter("joindate"));
    ps.setString(5, request.getParameter("grade"));
    ps.setString(6, request.getParameter("city"));
    ps.setString(7, request.getParameter("custno"));
    
    ps.executeUpdate();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	

	<jsp:forward page="member_list.jsp"></jsp:forward>
</body>
</html>