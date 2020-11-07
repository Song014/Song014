<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link rel="stylesheet" type="text/css" href="css/boardList.css">

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>글 목록</title>
</head>
<body>
<!-- 헤더 -->
<jsp:include page="../../header.jsp"></jsp:include>
<br>
	<div class="w3-container w3-dark-grey w3-padding w3-center" style="width:100%">
		<c:choose>
			<c:when test="${sessionId != null}">
				* ${sessionId}님 환영합니다! 당신의 레시피를 공유해주세요!  
				&nbsp;  &nbsp;  <button type="button" onclick="location.href='logout.userdo'" class="w3-button w3-round w3-small w3-padding-small w3-white"><img src="./myimg/logout.png"style="width:15px;height:15px">&nbsp;로그아웃</button>
			</c:when>
			<c:when test="${sessionId == null}">
				 * 로그인을 통해 당신의 레시피를 공유해주세요! 
				  &nbsp;  &nbsp; <a href="login.userdo">로그인</a>  &nbsp;  &nbsp;  <a href="join.userdo">회원가입</a>
			</c:when>
		</c:choose>
	</div>



<div class="outwrap">
	<div class="inwrap">	
	
		<!-- 게시글 컨테이너 -->
		<div class="w3-row-padding w3-margin-top" style="width: 90%; margin-left:200px">
			<!-- 검색 시작 -->
			<div  style="width:77%;">
				<form action="getBoardList.do" method="post">
					<table id="card">
						<tr>
							<td>
							<div class="w3-row" style="width:400px">
								<div class="w3-container w3-twothird">	
									<input name="searchKeyword" type="text" placeholder="#음식이나 재료 입력  " class="w3-input w3-border w3-round-large" style="width:260px"/> 
								</div>
								<div class="w3-container w3-third">
									<input type="submit" class="w3-button w3-round  w3-dark-gray" value="검색" /> 
								</div>
							</div>
							</td>	
							<td id="td2">
								<c:if test="${sessionId != null}">
									 <button type="button" onclick="location.href='insertBoard.do'"  class="w3-button w3-round w3-red">
									 	<img src="./myimg/edit.png"style="width:15px;height:15px">&nbsp;글쓰기
									 </button>
								</c:if>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<!-- 게시글 리스트 -->
			<c:forEach items="${boardList }" var="board">
			  <div class="w3-third" style="width:350px;height:350px">
			    <div class="w3-card " >
			    	<a href="getBoard.do?bno=${board.bno }">
			    		<img src="./img/${board.img }" class="w3-hover-opacity" style="width:335px;height:300px"></a>
			      	<div class="w3-container">	      	
			        	<table id="card">
			        		<tr>
			        			<td><b>${board.title }</b></td>
			        			<td id="td2"><img src="./myimg/comment.png"style="width:15px;height:15px">&nbsp;${board.comment_count}&nbsp;&nbsp;<img src="./myimg/love.png"style="width:15px;height:15px">&nbsp;${board.like_it}</td>
			        		</tr>
			        	</table>
			      	</div>
			    </div>
			  </div>
			 </c:forEach>
		</div>
		
		<br><br>
</div>
</div>
<br><br>
<!-- 푸터 -->
<jsp:include page="../../footer.jsp"></jsp:include>

</body>
</html>