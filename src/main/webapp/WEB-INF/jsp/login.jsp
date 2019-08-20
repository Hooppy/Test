<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<link rel="stylesheet" href="/webjars/bootstrap/3.3.4/dist/css/bootstrap.min.css">
</head>
<body>
<!-- ������ ��ť��Ƽ�� form action �⺻�� = j_spring_security_check �⺻���� ��� ������ form ��� �⺻ login form�� ǥ�� -->
<div id="content" class="container">
<form name="loginForm" action="login/check" method="post">
<input type="text" name="username"><br>
<input type="text" name="password"><br>
<input type="submit" value="�α���">
<button id="btn" type="button">ȸ������</button><br>
<img src="${pageContext.request.contextPath}/resources/naver.JPG">
<div class="flash_message">
	${message}
</div>
<input type="hidden" name="failureCode" value="${failureCode}">

<%-- <c:if test="${not empty param.fail}">
	<tr>
		<td colspan="2">
			<font color="red">
				<p> Your login attempt was not successful, try again. </p>
				<p> Reason : ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message} </p>
			</font>
			<c:remove scope="session" var="SPRING_SECURITY_LAST_EXCEPTION" />
		</td>
	</tr>		
</c:if> --%>

<c:if test="${not empty failureCode}">
	<tr>
		<td colspan="2">
			<font color="red">
				<p> �α��� ���� </p>
				<p> ���� : ${sessionScope["failureCode"]} </p>
			</font>
			<c:remove scope="session" var="failureCode" />
		</td>
	</tr>		
</c:if>

<img src="resources/images/icon_ing.gif">

</form>
</div>

<script type="text/javascript" src="http://code.jquery.com/jquery-2.1.3.min.js"></script>
<!-- <script type="text/javascript" src="/webjars/jquery/2.1.3/dist/jquery.min.js"></script> -->

<!-- <script type="text/javascript" src="/webjars/jquery/2.1.3/dist/jquery.min.js"></script> -->
<!-- <script type="text/javascript" src="/webjars/bootstrap/3.3.4/dist/js/bootstrap.min.js"></script>  -->

<script>
	/* $(document).ready(function() {
		$("#btn").click(function() {
			location.href = "new";
		});
	}); */
	
	$(document).ready(function() {
		$("#btn").click(function() {
			location.href = "new";
		});
	});
</script>

</body>
</html>