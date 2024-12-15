<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/style.css?abc">
</head>
<body>
	<header>
		<jsp:include page="layout/header.jsp"></jsp:include>
	</header>
	<nav>
		<jsp:include page="layout/nav.jsp"></jsp:include>
	</nav>
	<section class="section">
		<h2>백신예약조회</h2>
		
		<form action="research_result.jsp" method="post">
			<table class="table_line">
				<tr>
					<th>예약번호</th>
					<td><input type="text" name="check_RESVNO">예)20210001</td>
				</tr>
				<tr class="center">
					<td colspan="2">
						<input type="submit" value="조회">
						<input type="button" value="취소">
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