<%@page import="DB.DBConnect"%>
<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String sql = "INSERT INTO TBL_VACCRESV_202108 VALUES(?,?,?,?,?,?)";

	Connection conn = DBConnect.getConnection();
	PreparedStatement ps = conn.prepareStatement(sql);
	ps.setString(1, request.getParameter("RESVNO"));
	ps.setString(2, request.getParameter("JUMIN"));
	ps.setString(3, request.getParameter("HOSPCODE"));
	ps.setString(4, request.getParameter("RESVDATE"));
	ps.setString(5, request.getParameter("RESVTIME"));
	ps.setString(6, request.getParameter("VCODE"));
	ps.executeUpdate();
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