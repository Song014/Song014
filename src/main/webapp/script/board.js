function boardCheck() {
	if (document.frm.title.value.length == 0) {
		alert("제목을 입력하세요.");
		frm.title.focus();
		return false;
	}
	if (document.frm.id.value.length == 0) {
		alert("아이디를 입력하세요.");
		frm.id.focus();
		return false;
	}
	if (document.frm.password.value.length == 0) {
		alert("비밀번호를 입력하세요.");
		frm.password.focus();
		return false;
	}
	if (document.frm.hashtag.value.length == 0) {
		alert("해쉬태그는 꼭 입력해주세요!.");
		frm.hashtag.focus();
		return false;
	}
	if (document.frm.uploadFile.value.length == 0) {
		alert("이미지를 업로드 해주세요!");
		return false;
	}

	return true;
}


function commentCheck() {
	if (document.commentForm.content.value.length == 0) {
		alert("댓글 내용을 입력하세요.");
		return false;
	}
	return true;
}



//밑으로 안쓰는 거
function open_win(url, name) {
	window.open(url, name, "width=500, height=230");
}


function passCheck() {
	if (document.frm.pass.value.length == 0) {
		alert("비밀번호를 입력하세요.");
		return false;
	}
	return true;
}
function del_check(board_num){
	var result = confirm("글을 삭제하시겠습니까?");
    if(result){
    	location.href="BoardServlet?command=board_delete&num="+board_num;
    }
	
}

function like(){
	  $.ajax({
		    url: "like.do",
		    type: "POST",
		    cache: false,
		    dataType: "json",
		    data: $('#like_form').serialize(),   //아이디가 like_form인 곳의 모든 정보를 가져와  파라미터 전송 형태(표준 쿼리형태)로 만들어줌
		    success: 
		    function(data){      					//ajax통신 성공시 넘어오는 데이터 통째 이름 =data
		    	alert("'좋아요'가 반영되었습니다!") ;  // data중 put한 것의 이름 like
                $("#like_result").html(data.like);  //id값이 like_result인 html을 찾아서 data.like값으로 바꿔준다.
		    },   
		    
		    error: 
		    function (request, status, error){  
		      alert("ajax실패")                  
		    }
		  });
}