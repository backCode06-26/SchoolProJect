<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.sql.*" %>
<%@ page import="DB.DBConnect" %>
<%
	String sql = "insert into TBL_VOTE_202005 values (?, ?, ?, ?, ?, ?)";
	request.setCharacterEncoding("utf-8");
	
	Connection conn = DBConnect.getConnection();
	PreparedStatement ps = conn.prepareStatement(sql);
	ps.setString(1, request.getParameter("jumin"));
	ps.setString(2, request.getParameter("name"));
	ps.setString(3, request.getParameter("number"));
	ps.setString(4, request.getParameter("time"));
	ps.setString(5, request.getParameter("place"));
	ps.setString(6, request.getParameter("check"));
	
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