<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import ="DB.DBConnect" %> <%-- 지시문형식을 통해 DB연결 자바파일 불러오기  --%>
<%@ page import = "java.sql.*" %>  <!-- 지시문형식을 통해 SQL 관련 라이브러리 불러오기  -->
 
 <%
          request.setCharacterEncoding("UTF-8"); // 오라클에 한글 입력 시 깨지지 않도록 셋팅
 
          String sql = "insert into member_tbl_02 values(?,?,?,?,?,?,?)"; 
          //sql 변수선언 후 쿼리문형태의 문자열 저장, 삽입쿼리문에 입력데이터 자리 7개 준비
          
          Connection conn = DBConnect.getConnection(); // DB 연결 기능을 객체변수 conn 에 저장 -> 1.DB연결
          PreparedStatement pstmt = conn.prepareStatement(sql);  // sql변수에 저장되어 있는 문장이 쿼리문이 됨 ->2.DB연결 후 쿼리문이 생성
          
        //    화면으로부터 사용자가 입력한 회원정보 데이터 7개를 쿼리문 각 자리에 셋팅    //
        
          pstmt.setInt(1, Integer.parseInt(request.getParameter("custno")));
          // 화면으로부터 읽어들인 데이터는 문자열이므로 숫자형으로 형변환 필요
          pstmt.setString(2,request.getParameter("custname"));  
          pstmt.setString(3, request.getParameter("phone"));
      	  pstmt.setString(4, request.getParameter("address"));
          pstmt.setString(5, request.getParameter("joindate"));
      	  pstmt.setString(6, request.getParameter("grade"));
      	  pstmt.setString(7, request.getParameter("city"));
      	 
      	  pstmt.executeUpdate();        // 쿼리문을 실행하고, 건별로 오라클 테이블에 누적
  %>    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<jsp:forward page="member_list.jsp"></jsp:forward>
<!-- 데이터입력 수행 완료 후 회원목록을 조회할 수 있는 페이지로 이동-->

</body>
</html>














