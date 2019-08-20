<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
${msg}<br>
<form action="delete" method="post">
<input type="text" name="username" value="${username}"><br>
<input type="text" name="password" value="${password}"><br>
<input type="submit" value="회원탈퇴">
</form>
<a href="logout">로그아웃</a><br>

<!-- 입력 값들 사용 -->
ID : <sec:authentication property="principal.username"/><br>
PW : <sec:authentication property="principal.password"/><br>

<!-- 사용 가능 계정 여부 -->
사용가능여부 : <sec:authentication property="principal.enabled"/><br>

<!-- 계정 만료 여부 -->
계정만료여부 : <sec:authentication property="principal.accountNonExpired"/>

<!-- 권한별로 화면 나눌때 사용 -->
<ul>
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<li>관리자 화면</li>
	</sec:authorize>
	
	<sec:authorize access="permitAll">
		<li>비회원 화면</li>
	</sec:authorize>
	
	<sec:authorize access="isAuthenticated()">
		<li>준회원 화면</li>
	</sec:authorize>
	
	<sec:authorize access="hasAnyRole('USER_MANAGER', 'USER')">
		<li>정회원 화면</li>
	</sec:authorize>
</ul>
<div style="width:630px; height:976px;">
	<div id="content">
		상호 : 주식회사 케이토토<br>
		사업자주소 : 서울시 마포구 상암산로 34 디지털큐브 10층<br>
		대표자성명 : 대표이사 김철수<br>
		<br><br>
		코드번호(MIS) : ${miscode}<br>
		판매점명 : ${totoname}<br>
		판매점주소 : ${totoaddr}<br>
		계약자명 : ${contractor}<br>
	</div>
</div>
<button>PDF</button>

<%-- <form action="downloadPDF" method="post" id="downloadPDF">
	<h3>Spring MVC PDF Download Example</h3>
	<input type="hidden" name="username" value="${username}">
	<input type="hidden" name="bldname" value="${bldname}">
	<input type="hidden" name="addr" value="${addr}">
	<input type="hidden" name="name" value="${name}">
	<input type="hidden" name="code" value="${code}">
	<input id="submitID" type="submit" value="Download PDF">
</form> --%>

</body>

<!-- <script src="resources/js/html2canvas.js"></script> -->
<script src="resources/js/jspdf.min.js"></script>

<script type="text/javascript">
   
    document.querySelector("button").addEventListener("click", function() {
        /* html2canvas(document.querySelector("#content"), {canvas: canvas}).then(function(canvas) {
        	var imgData = canvas.toDataURL('image/png');
        	
        	var imgWidth = 210; // 이미지 가로 길이(mm) A4 기준
            var pageHeight = imgWidth * 1.414;  // 출력 페이지 세로 길이 계산 A4 기준
            var imgHeight = canvas.height * imgWidth / canvas.width;
            var heightLeft = imgHeight;
            
        	var doc = new jsPDF('p', 'mm');
            var position = 0;
            
            doc.addImage(imgData, 'PNG', 0, position, imgWidth, imgHeight);
            
            doc.save('sample_A4.pdf');
        }); */
        
    	var option = {
   	        format: 'JPEG',
   			pagesplit : true,
   	        background: '#FFF'
   		};
        
    	var doc = new jsPDF('p','pt','a4');
   		doc.internal.scaleFactor = 1.165;
   		//
   		doc.addHTML(document.all["content"], 0, 0, option ,function() {
   			doc.save('test.pdf');
   		});
   		
    });

</script>
</html>