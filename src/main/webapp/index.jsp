<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@	taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link rel="stylesheet" type="text/css" href="css/boardList.css">

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>모두의 주방</title>
</head>
<body>
<div class="outwrap">
	<div class="inwrap" style=" margin-top:140px">	
			<center>
				<a href="index.jsp"><img src="./myimg/koa_logo1.png" width="390px"></a><br><br>
				<!-- 검색 시작 -->
				<div class="w3-container" >
					
					<form action="getBoardList.do" method="post" >
						<table style="text-align: center; width:580px;  height: 100px;">
							<tr>
								<td><input name="searchKeyword" placeholder=" #음식이나 재료를 입력해보세요 !  #감자 #참치 #돼지고기" class="w3-input w3-border w3-round-large" type="text" /> </td>
							</tr>
							<tr>
								<td><input type="submit"  class="w3-button w3-round-large w3-dark-gray" value=" 검 색 " /></td>
							</tr>
						</table>
					</form>
				</div>
			</center>
			<br>
			<hr>
			<c:choose>
				<c:when test="${sessionId != null}">
					<h3>${sessionId}님 환영합니다.</h3>	
				</c:when>
				
			</c:choose>
			
	
			<c:if test="${sessionId == null}">
				<input type="button" value="로그인"   class="w3-button  w3-border w3-round-large" onclick="location.href='login.userdo'" />
				<input type="button" value="회원가입"  class="w3-button  w3-border w3-round-large" onclick="location.href='join.userdo'" />
			</c:if>
			<c:if test="${sessionId != null}">
				<input type="button" value="로그아웃"  class="w3-button  w3-border w3-round-large" onclick="location.href='logout.userdo'" />

			</c:if>
			
			
			
			<br><br>
			
			<a href="getBoardList.do">글 목록 바로가기</a>
			
		<hr>
</div>
</div>
</body>
</html>