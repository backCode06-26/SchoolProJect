<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<section class="section">
		<h2>백신예약 프로그램 작성 안내</h2>
		<p>
			데이터 입출력 요건에 맞게 주민정보, 병원정보, 백신예약정보를 작성하는 프로그램이다.<br>
			프로그램 작성 순서<br>
			1. 주어진 테이블 정보를 생성한다.<br>
			2. 문제에 제시된 기본 데이터를 일괄 입력한다.<br>
			3. 백신예약 정보를 입력하는 프로그램을 작성한다.<br>
			 - 예약번호는 자동 생성<br>
			 - 백신코드는 셀렉트 박스로 만들며, 값은 V001은 A백신, V002는 B백신, V003은 C백신으로 표시<br>
			 - 병원코드는 라디오 버튼으로 값은 H001, H002, H003, H004로 보이는 것은 가_병원, 나_병원, 다_병원, 라_병원<br>
			 - 예약날짜는 YYYYMMDD, 예약시간dms 0930, 1130처림 4자리 숫자로 24시간제로 표시<br>
			4. 모든 항목에 대한 유효성 검사를 하며, 갑시 비어있거나 선택되지 않을 경우 오류메세지를 띄우고 포커스를 이동<br>
		</p>
	</section>
</body>
</html>