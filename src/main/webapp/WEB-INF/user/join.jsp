<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원 가입 </title>
</head>
<body>
<!-- 헤더 -->
<jsp:include page="../../header.jsp"></jsp:include>

<center>	
<br>
	<form:form action="join.userdo" method="post" commandName="userVO" class="w3-container w3-card-4 w3-light-grey w3-text-red w3-margin" style="width:470px;">
		<h2 class="w3-center">회 원 가 입</h2>
		*모두의 주방은 레시피 공유 사이트입니다^^
		<div class="w3-row w3-section">
		  <div class="w3-col" style="width:50px"><i class="w3-xxlarge fa fa-address-book"></i></div>
		    <div class="w3-rest">
		      <form:input path="id" class="w3-input w3-border" name="id" id="id"  placeholder="아이디"/>
		      <form:errors path="id" /><br/>
		    </div>
		</div> 
		
		<div class="w3-row w3-section">
		  <div class="w3-col" style="width:50px"><i class="w3-xxlarge fa fa-key"></i></div>
		    <div class="w3-rest">
		      <form:password path="pwd" class="w3-input w3-border" name="pwd"   placeholder="비밀번호"/>
		      <form:errors path="pwd"  /><br/>
		    </div>
		</div>
		
		<div class="w3-row w3-section">
		  <div class="w3-col" style="width:50px"><i class="w3-xxlarge fa fa-user"></i></div>
		    <div class="w3-rest">
		      <form:input path="name" class="w3-input w3-border"  name="name" placeholder="이름"/>
		      <form:errors path="name" /><br/>
		    </div>
		</div>
		
		<div class="w3-row w3-section">
		  <div class="w3-col" style="width:50px"><i class="w3-xxlarge fa fa-envelope-o"></i></div>
		    <div class="w3-rest">
		      <form:input path="email"  class="w3-input w3-border" name="email" placeholder="Email"/>
		      <form:errors path="email" /><br/>
		    </div>
		</div>
		
		<div class="w3-row w3-section">
		  <div class="w3-col" style="width:50px"><i class="w3-xxlarge fa fa-phone"></i></div>
		    <div class="w3-rest">
		      <form:input path="phone" class="w3-input w3-border" name="phone" placeholder="Phone"/>
		      <form:errors path="phone" /><br/>
		    </div>
		</div>
		<input type="submit" value="확인" class="w3-button w3-block w3-section w3-red w3-ripple w3-padding">
		<input type="button" value="취소" onclick="location.href='index.jsp'" class="w3-button w3-block w3-section w3-dark-gray w3-ripple w3-padding">
	</form:form>


<br><br>
</center>
</body>
</html>