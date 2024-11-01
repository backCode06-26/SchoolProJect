<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import ="DB.DBConnect" %> <%-- 지시문형식을 통해 DB연결 자바파일 불러오기  --%>
<%@ page import = "java.sql.*" %>  <!-- 지시문형식을 통해 SQL 관련 라이브러리 불러오기  -->
 
 <%
        String sql = " select custno, custname, phone, address,  "
              			  + " to_char(joindate, 'yyyy-mm-dd')  joindate,"
             			  + " grade, city from member_tbl_02 where custno= "
             			  +   request.getParameter("click_custno");          //지정번호 회원 정보 조회하는 쿼리문
 
        Connection conn = DBConnect.getConnection(); // DB 연결 기능을 객체변수 conn 에 저장 -> 1.DB연결
        PreparedStatement pstmt = conn.prepareStatement(sql);  // sql변수에 저장되어 있는 문장이 쿼리문이 됨 ->2.DB연결 후 쿼리문이 생성\
        ResultSet rs = pstmt.executeQuery(); // 변수pstmt에 저장되어있는 SQL문을 실행하여 객체변수 rs에 저장
        rs.next(); //변수 rs에 결과값이 저장되는 경우 next()를 호출하여 마지막 값을 확인
        
        %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>회원정보수정</title>

<script type = "text/javascript">
	function u_checkValue(){
					if(!document.u_data.custname.value) {
						alert("회원성명이 입력되지 않았습니다.");
						u_data.custname.focus();
						return false;
					}
					else if(!document.u_data.phone.value) {
						alert("전화번호가 입력되지 않았습니다.");
						u_data.phone.focus();
						return false;
					}
					else if (!document.u_data.address.value) {
						alert("주소를 입력하세요.");
						u_data.address.focus();
						return false;
					} 
					else if (!document.u_data.joindate.value) {
						alert("가입일자를 입력하세요.");
						u_data.joindate.focus();
						return false;
					} 
					else if (!document.u_data.grade.value) {
						alert("고객등급을 입력하세요.");
						u_data.grade.focus();
						return false;
					}  
					else if (!document.u_data.city.value) {
						alert("도시코드를 입력하세요.");
						u_data.city.focus();
						return false;
						
					}
					alert("회원정보수정이 완료 되었습니다.");
					return true;					
		}
	
	function checkDel(f_custno) {
		msg = "정말로 삭제하시겠습니까?";
		if(confirm(msg)!=0)
		   {                   alert("삭제되었습니다.");
			                    location.href ="delete.jsp?d_custno="+f_custno;
	  }else{
		         alert("삭제가 취소되었습니다.");
	  }		
	}
</script>
</head>
<body>
<header>
	  <jsp:include page="layout/header.jsp"></jsp:include>
 </header>

 <nav>
   	 <jsp:include page="layout/nav.jsp"></jsp:include>
 </nav>
		
 <section class="section">
   	 <h2>홈쇼핑 회원 정보수정</h2><br>

<form name="u_data" action="update_p.jsp" method="post"  onsubmit="return u_checkValue()">
			<table class="table_line">
				<tr>
					<th>회원번호(자동발생)</th>
					<td><input type="text" name="custno"  value="<%= rs.getString("custno") %>"  readonly ></td>
				</tr>
				<tr>
					<th>회원성명</th>
					<td><input type="text" name="custname" value="<%= rs.getString("custname") %>" ></td>
				</tr>
				<tr>
					<th>회원전화</th>
					<td><input type="text" name="phone" value="<%= rs.getString("phone") %>" ></td>
				</tr>
				<tr>
					<th>회원주소</th>
					<td><input type="text" name="address"  value="<%= rs.getString("address") %>" ></td>
				</tr>
				<tr>
					<th>가입일자</th>
					<td><input type="text" name="joindate" value="<%= rs.getString("joindate") %>" ></td>
				</tr>
				<tr>
					<th>고객등급[A:VIP,B:일반,C:직원]</th>
					<td><input type="text" name="grade" value="<%= rs.getString("grade") %>" ></td>
				</tr>
				<tr>
					<th>도시코드</th>
					<td><input type="text" name="city"  value="<%= rs.getString("city") %>"  > ></td>
				</tr>
				<tr class="center">
					<td  colspan="2" >
						<input type="submit" value="등록">
						<input type="button" value="조회"  onclick = "location.href='member_list.jsp'  " >
						<input type="button" value="삭제" onclick="checkDel(<%= rs.getString("custno") %>);">
				</tr>
			</table>
		</form>	
   	
 </section>
		
<footer>
	<jsp:include page="layout/footer.jsp"></jsp:include>
</footer>

</body>
</html>


