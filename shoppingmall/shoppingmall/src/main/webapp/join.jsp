
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="DB.DBConnect"%><!-- 지시문형식을 통해 DB연결 자바파일 불러오기 -->
<%@page import="java.sql.*"%> <!-- 지시문형식을 통해 SQL 관련 라이브러리 가져오기 -->
    
<%
	String sql = "select max(custno) from member_tbl_02"; // 퀴리문 형식의 문자열이 변수명 sql에 저장
	
	Connection conn = DBConnect.getConnection(); // DB 연결 가능을 객체변수 conn에 저장 -> 1. DB연결
	PreparedStatement pstmt = conn.prepareStatement(sql); // sql변수에 저장되어 있는 문장이 퀴리문이 된 2. DB 연결 후 쿼리문이 생성
	ResultSet rs = pstmt.executeQuery(); // 변수pstmt에 저장되어있는 SQL문을 실행하여 객체변수 rs에 저장
	
	rs.next(); // 변수 rs에 결과값이 저장되는 경우 next()를 호출하여 마지막값 을 확인
	
	int num = rs.getInt(1) + 1; //num에는 오라클 member 테이블의 마지막 회원번호 +1 값이 정수로 저장
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8 ">
<title>Insert title here</title>
<link rel="stylesheet" href=" css/style.css?abc">
<script type="text/javascript">
	function checkValue() {
		if(!document.data.custname.value) {
			alert("성명을 입력하세요")
			data.custname.focus();
			return false;
		}
		else if(!document.data.phone.value) {
			alert("전화번호을 입력하세요")
			data.phone.focus();
			return false;
		}
		else if(!document.data.address.value) {
			alert("주소을 입력하세요")
			data.address.focus();
			return false;
		}
		else if(!document.data.joindate.value) {
			alert("가입날짜을 입력하세요")
			data.joindate.focus();
			return false;
		}
		else if(!document.data.grade.value) {
			alert("등급을 입력하세요")
			data.grade.focus();
			return false;
		}
		else if(!document.data.city.value) {
			alert("도시을 입력하세요")
			data.city.focus();
			return false;
		}
		alert("등록이 완료되었습니다.");
		return true;
	}
</script>
</head>
<body>
	<header>
		<jsp:include page="/layout/header.jsp"></jsp:include>
	</header>
	<nav>
		<jsp:include page="/layout/nav.jsp"></jsp:include>
	</nav>
	<section class="section">
		<h2>홈쇼핑 회원 등록</h2><br>
		<form name="data" action="join_p.jsp" method="post" onsubmit="return checkValue()">
			<table class="table_line">
					<tr>
						<th>회원번호(자동발생)</th>
						<td><input type="text" name="custno" value="<%=num %>" readonly></td>
					</tr>
					<tr>
						<th>회원성명</th>
						<td><input type="text" name="custname"></td>
					</tr>
					<tr>
						<th>회원전화</th>
						<td><input type="text" name="phone"></td>
					</tr>
					<tr>
						<th>회원주소</th>
						<td><input type="text" name="address"></td>
					</tr>
					<tr>
						<th>가입일자</th>
						<td><input type="text" name="joindate"></td>
					</tr>
					<tr>
						<th>고객등급[A:VIP,B:일반,C:직원]</th>
						<td><input type="text" name="grade"></td>
					</tr>
					<tr>
						<th>도시코드</th>
						<td><input type="text" name="city"></td>
					</tr>
					<tr class="center">
						<td colspan="2">
							<input type="submit" value="등록">
							<input type="button" value="취소" onclick="location.href='join.jsp'">
							<input type="button" value="조회" onclick="location.href='member_list.jsp'">
						</td>
					</tr>
			</table>
		</form>
	</section>
	<footer>
		<jsp:include page="/layout/footer.jsp"></jsp:include>
	</footer>
</body>
</html>