<%@page import="DB.DBConnect"%>
<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String sql = "SELECT T_CODE, T_NAME FROM TBL_LAB_TEST_202004";

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
		if(!document.data.P_NO.value) {
			alert("환자번호가 입력되지 않았습니다!")	
			data.P_NO.focus();
			return false;
		} else if(!document.data.T_CODE.value) {
			alert("검사코드가 선택되지 않았습니다!")	
			data.T_CODE.focus();
			return false;
		} else if(!document.data.T_SDATE.value) {
			alert("검사일자가 입력되지 않았습니다!")	
			data.T_SDATE.focus();
			return false;
		} else if(!document.data.T_STATUS.value) {
			alert("검사상태가 선택되지 않았습니다!")	
			data.T_STATUS.focus();
			return false;
		} else if(!document.data.T_LDATE.value) {
			alert("검사완료일자가 입력되지 않았습니다!")	
			data.T_LDATE.focus();
			return false;
		} else if(!document.data.T_RESULT.value) {
			alert("검사결과가 선택되지 않았습니다!")	
			data.T_RESULT.focus();
			return false;
		}
		alert("검사결과가 정상적으로 등록 되었습니다!")
		return true;
	} 
	function valueReset() {
		alert("정보를 지우고 첨으부터 다시 입력합니다!");
		document.getElementById("dataForm").reset();
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
		<h2>검사결과입력</h2>
		
		<form action="result_input_p.jsp" method="post" onsubmit="return checkValue()" name="data" id="dataForm">
			<table class="table_line">
				<tr>
					<th>환자번호</th>
					<td><input type="text" name="P_NO"> 예)1001</td>
				</tr>
				<tr>
					<th>검사코드</th>
					<td>
						<select name="T_CODE">
							<% while(rs.next()) { %>
								<option value="<%= rs.getString("T_CODE") %>"><%= rs.getString("T_NAME") %></option>
							<% } %>
						</select>
					</td>
				</tr>
				<tr>
					<th>검사시작일자</th>
					<td><input type="text" name="T_SDATE"> 예>20200101</td>
				</tr>
				<tr>
					<th>검사상태</th>
					<td>
						<label><input type="radio" value="1" name="T_STATUS">검사중</label>
						<label><input type="radio" value="2" name="T_STATUS">검사완료</label>
					</td>
				</tr>
				<tr>
					<th>검사완료일자</th>
					<td><input type="text" name="T_LDATE"> 예)20200101</td>
				</tr>
				<tr>
					<th>검사결과</th>
					<td>
						<label><input type="radio" value="X" name="T_RESULT">미입력</label>
						<label><input type="radio" value="P" name="T_RESULT">양성</label>
						<label><input type="radio" value="N" name="T_RESULT">음성</label>
					</td>
				</tr>
				<tr class="center">
					<td colspan="2">
						<input type="submit" value="검사결과등록">
						<input type="button" onclick="valueReset()" value="다시쓰기">
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