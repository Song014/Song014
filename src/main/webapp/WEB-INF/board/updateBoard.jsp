<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<link rel="stylesheet" type="text/css" href="css/board_cu.css">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>새글등록</title>
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
	<center>
		<h1>레시피 수정</h1>
		
		<hr>
		<form action="updateBoard.do" method="post" enctype="multipart/form-data">
			<table>
				<tr>
					<th>제목</th>
					<td align="left"><input type="text"  class="w3-input" name="title" value="${board.title }"/></td>
				</tr>
				<tr>
					<th>작성자</th>
					<td align="left"><input type="text"  class="w3-input" name="id" size="10"  value="${board.id }" readonly="readonly"/></td>
				</tr>
				<tr>
					<th>내용</th>
					<td align="left"><textarea name="content" class="w3-input w3-border" cols="40" rows="10" >${board.content}</textarea></td>
				</tr>
				<tr>
					<th>hashtag</th>
					<td align="left"><input name="hashtag" class="w3-input w3-border" size="10" value="${board.hashtag }"/></td>
				</tr>
				<tr>
					<th>업로드</th>
					<td align="left"><input type="file" name="uploadFile" /></td>
				</tr>
				
				<tr>
					<td colspan="2" align="center">
						<input type="button" value="취소 " onclick="history.back(-1);"  class="w3-button w3-round w3-dark-grey"> &nbsp;&nbsp;
						<input type="submit" value="글 수정 " class="w3-button w3-round  w3-red" />

					</td>
				</tr>
			</table>
		</form>
		<br><br><br>
		
	</center>
<br><br>
<!-- 푸터 -->
<jsp:include page="../../footer.jsp"></jsp:include>
</body>
</html>