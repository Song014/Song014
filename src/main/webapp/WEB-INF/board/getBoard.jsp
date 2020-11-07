<%@page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="script/board.js"></script>
<script src="http://code.jquery.com/jquery-1.11.2.min.js"></script> 
<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/board_r.css">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글 상세</title>
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
	<h2>모두의 주방 '${board.bno}번째 레시피'</h2>
		
	<table id="getboard">
		<tr>
			<td><input type="button" value="글목록" onclick="location.href='getBoardList.do'" class="w3-button w3-round w3-dark-gray w3-margin"> </td>
			<td id="td2" colspan="2">
				<c:if test="${sessionId == board.id}">
					<input type="button" value="글 삭제" onclick="location.href='deleteBoard.do?bno=${board.bno }'" class="w3-button w3-round w3-dark-gray">
					<input type="button" value="글 수정" onclick="location.href='updateBoard.do?bno=${board.bno }'" class="w3-button w3-round w3-dark-gray">
				</c:if>
				<c:if test="${sessionId != null}">
					<input type="button" value="글쓰기" onclick="location.href='insertBoard.do'" class="w3-button w3-round w3-red">
				</c:if>
			</td>
		</tr>
		<tr>
			<td id="img_td" rowspan="7" >
			<img src = "./img/${board.img}"   style="width: 700px; min-height:600px; height:auto;">
			</td>
			<td style="font-size:30; height:60px; ">  &nbsp; <b>${board.title }</b></td>
			<td width="150px">작성자 <b>${board.id }</b> </td>
		</tr>
		<tr>
			<td height="20px">  &nbsp; <fmt:formatDate value="${board.writedate }" pattern="yy-MM-dd HH:mm:ss"/></td>
			<td>조회수 <b>${board.readcount }</b></td>
		</tr>
		<tr>
			<td colspan="2">
				<div class='table-scroll'>
				<table id="content_table">
					<!-- 글 내용 -->
					<tr>
						<td><textarea cols="45" rows="15" readonly="readonly">${board.content }</textarea>
							<br>
						</td>
					</tr>
					<!-- 해쉬 태그 -->
					<tr>
						<td><b>${board.hashtag }</b></td>
					</tr>
					<!-- 좋아요 ajax -->
					<tr>
						<td>
							<center>
								<form id="like_form">
									<table>
										<input type="hidden" name="bno" value="${board.bno}">
										<tr><input type="button" value="좋아요 ♥" onclick="return like()" class="w3-button w3-round w3-red"> </tr>
										<tr><div id="like_result">${board.like_it}</div> </tr> <!-- 좋아요 눌렀을때는 갯수가 생기는데, 안누르면 갯수가 안보여서 ${board.like_it} 넣는다. -->
									</table>
								</form>
								
							</center>
						</td>
					</tr>
					<!-- 댓글 list -->
					<tr>
						<td>
							<c:forEach var="cmt" items="${cmt_list}">
								<b>${cmt.id}</b> &nbsp;
								${cmt.content}  &nbsp;&nbsp;&nbsp;
								<a style="font-size: 10px;text-align: right"><fmt:formatDate value="${cmt.writedate}" pattern="yy-MM-dd HH:mm:ss"/></a><br>
	
							</c:forEach> 
						</td>
					</tr>
				</table>
				</div>
			
			
			</td>		
		</tr>
		
		
		<tr>
			<!-- 좋아요,댓글 갯수 -->
			<td height="10px"colspan="2" id="like_form">
				<a style="font-size: 15px;">
					 &nbsp;<img src="./myimg/comment.png"style="width:15px;height:15px">&nbsp;<b>${board.comment_count}</b>&nbsp;&nbsp;
					<!-- <img src="./myimg/love.png"style="width:15px;height:15px">&nbsp;<b>${board.like_it}</b> -->
				</a>
			</td>
			
		</tr>
		<tr>
			<!-- 댓글 폼 -->
			<td height="50px" colspan="2">
				<table>
					<tr>
					<c:if test="${sessionId != null}">
				            <form id="commentForm" name="commentForm" method="post" action="insertComment.do">
				            	<input type="hidden" name="id" value="${sessionId}">
				                <input type="hidden" name="bno" value="${board.bno}">
				      				<td width="435px"><input type="text" class="w3-input" name="content" id="content" placeholder="댓글을 입력하세요" ></td>
					                <td><input type="submit" class="w3-button w3-small w3-round w3-red" value="등록" onclick="return commentCheck()"> </td>
				            </form>
		            </c:if>
					<c:if test="${sessionId == null}">
			            	<tr><td colspan="3" align="center">  &nbsp; 댓글을 남기려면 <a href="login.userdo">로그인</a>을 해주세요 ^^</td></tr>
			         </c:if>
					</tr>  
				</table>
			</td>
			
		</tr>
		<tr>
			<td colspan="2"></td>
		
		</tr>
	</table>
	
		<hr>
		
	</center>
	<br><br><br>
<!-- 푸터 -->
<jsp:include page="../../footer.jsp"></jsp:include>

</body>
</html>
