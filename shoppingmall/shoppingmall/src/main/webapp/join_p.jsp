<%@page import="java.awt.Window"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    
    
<%@ page import="DB.DBConnect" %>
<%@ page import="java.sql.*" %>

<%
	request.setCharacterEncoding("UTF-8"); // ����Ŭ �Է� �� ������ �ʵ��� ����
	String sql = "insert into member_tbl_02 values (?,?,?,?,?,?,?)";
	
	Connection conn = DBConnect.getConnection();
	PreparedStatement ps = conn.prepareStatement(sql);
	
	ps.setInt(1, Integer.parseInt(request.getParameter("custno")));
	ps.setString(2, request.getParameter("custname"));
	ps.setString(3, request.getParameter("phone"));
	ps.setString(4, request.getParameter("address"));
	ps.setString(5, request.getParameter("joindate"));
	ps.setString(6, request.getParameter("grade"));
	ps.setString(7, request.getParameter("city"));
	
	ps.executeUpdate(); // �������� �����ϰ�, �Ǻ� ����Ŭ ���̺� ����
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<jsp:forward page="member_list.jsp"></jsp:forward>
	<!-- �������Է� ���� �� ȸ����� ��ȸ �� �� �ִ� ������ �̵� -->
	
</body>
</html>