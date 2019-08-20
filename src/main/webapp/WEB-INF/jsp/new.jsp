<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
<form action="create" method="post">
	ID : <input type="text" name="username"><br>
	비밀번호 : <input type="text" name="password"><br>
	판매점명 : <input type="text" name="bldname"><br>
	판매점주소 : <input type="text" name="addr"><br>
	이름 : <input type="text" name="name"><br>
	<a href="#" onClick ="history.back();">로그인 페이지</a>
	<input type="submit" value="작성완료">
</form>
</body>
</html>