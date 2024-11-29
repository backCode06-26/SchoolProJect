<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="DB.DBConnect" %>
<%
	String sql = "select M_NO, M_NAME from TBL_MEMBER_202005";
	
	Connection conn = DBConnect.getConnection();
	PreparedStatement ps = conn.prepareStatement(sql);
	ResultSet rs = ps.executeQuery();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/style.css?abc">
<script type="text/javascript">
	function checkValue() {
		if(!document.data.jumin.value) {
			alert("주민번호 입력되지 않았습니다!");
			data.jumin.focus();
			return false;
		} else if(!document.data.name.value) {
			alert("성명 입력되지 않았습니다!");
			data.name.focus();
			return false;
		} else if(!document.data.number.value) {
			alert("후보번호 입력되지 않았습니다!")
			data.number.focus();
			return false;
		} else if(!document.data.time.value) {
			alert("투표시간 입력되지 않았습니다!")
			data.time.focus();
			return false;
		} else if(!document.data.place.value) {
			alert("투표장소 입력되지 않았습니다!")
			data.place.focus();
			return false;
		} else if(!document.data.check.value && !document.data.no_check.value) {
			alert("유권자 확인 선택되지 않았습니다!")
			data.check.focus();
			return false;
		}
		alert("투표하기 정보가 정상적으로 등록 되었습니다.");
		return true;
	}
	
	function checkReset() {
		if(confirm("정보를 지우고 처음부터 다시 입력합니다.")) {
			document.getElementById("form").reset();
			data.jumin.focus();
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
		<h2>투표하기</h2>
		
		<form action="member_vote_p.jsp" method="post" onsubmit="return checkValue()" name="data" id="form">
			<table class="table_line">
				<tr>
					<th>주민번호</th>
					<td><input type="text" name="jumin">예)890101200021</td>
				</tr>
				<tr>
					<th>성명</th>
					<td><input type="text" name="name"></td>
				</tr>
				<tr>
					<th>후보번호</th>
					<td>
						<select name="number">
							<% while(rs.next()) { %>
								<option value="<%=rs.getString("M_NO") %>"><%="[" + rs.getString("M_NO") +"]" + rs.getString("M_NAME") %></option>
							<% } %>
						</select>
					</td>
				</tr>
				<tr>
					<th>투표시간</th>
					<td><input type="text" name="time">예) 0930 (09시30분)</td>
				</tr>
				<tr>
					<th>투표장소</th>
					<td><input type="text" name="place"></td>
				</tr>
				<tr>
					<th>유권자확인</th>
					<td>
						<input type="radio" value="Y" name="check">확인
						<input type="radio" value="N" name="check">미확인
					</td>
				</tr>
				<tr class="center">
					<td colspan="2">
						<input type="submit" value="투표하기">
						<input type="button" value="다시쓰기" onclick="checkReset()">
					</td>
				</tr>
			</table>
		</form>
	</section>
	<footer>
		<jsp:include page="layout/footer.jsp"></jsp:include>
	</footer>
</body>
</html>