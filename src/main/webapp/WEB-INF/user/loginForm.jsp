<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/boardList.css">
<script type="text/javascript"
	src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.2.js"
	charset="utf-8"></script>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="script/member.js"></script>
<title>로그인</title>
</head>
<body>
<!-- 헤더 -->
<jsp:include page="../../header.jsp"></jsp:include>

<div class="outwrap">
	<div class="inwrap">	
	<br>

		<!-- 네아로 제이쿼리를 이용한 파싱 -->
		<script type="text/javascript">
			$(document).ready(function() {
				var name = ${result}.response.name;
				var email = ${result}.response.email;
				$("#name").html(name);
				$("#email").html(email);
				$("#email_div").html(email);
			
			  });
			
		</script>
		<h2 style="text-align: center" id="name"></h2>
		<h4 style="text-align: center" id="email"></h4>
		<div id="email_div"></div>
	<div style="text-align:center">${result}</div>
	<hr>
	
	
	<center>
	<form action="login.userdo" method="post" name="frm" class="w3-container w3-card-4 w3-light-grey w3-text-red w3-margin" style="width:470px;">
		<h2 class="w3-center">로그인</h2>
		
		<div class="w3-row w3-section">
		  <div class="w3-col" style="width:50px"><i class="w3-xxlarge fa fa-user"></i></div>
		    <div class="w3-rest">
		    	<input type="text" name="id" id="id" class="w3-input w3-border" placeholder="아이디" value="${id}">
		    </div>
		</div>
		
		<div class="w3-row w3-section">
		  <div class="w3-col" style="width:50px"><i class="w3-xxlarge fa fa-user"></i></div>
		    <div class="w3-rest">
		    	<input type="password" id="pwd" name="pwd" class="w3-input w3-border" placeholder="비밀번호" >
		 
		    </div>
		</div>
		<span style="color: red;">${message}</span> 
		<input type="submit" value="로그인" onclick="return loginCheck()" class="w3-button w3-block w3-section w3-red w3-ripple w3-padding"  >

	</form>
	<br>
	<!-- 소셜 로그인 화면으로 이동 시키는 URL -->
	<div style="width:450px;">
		<button class="w3-button w3-block w3-green w3-round" onclick="location.href='${url}'"  >NAVER 아이디로 로그인</button> <br>
		<button class="w3-button w3-block w3-red w3-round"  onclick="location.href='${google_url}'" >Google 아이디로 로그인</button> <br>
		<button class="w3-button w3-block w3-indigo w3-round"  onclick="location.href='${facebook_url}'" >Facebook 아이디로 로그인</button> <br>
	
	</div>
	<br><br>
	</center>
	</div>
</div>
</body>
</html>