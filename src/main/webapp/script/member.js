


function joinCheck() {
	if (document.frm.id.value.length == 0) {
		alert("아이디를 써주세요");
		frm.userid.focus();
		return false;
	}
	if (document.frm.pwd.value == "") {
		alert("암호는 반드시 입력해야 합니다.");
		frm.pwd.focus();
		return false;
	}
	if (document.frm.pwd.value != document.frm.pwd_check.value) {
		alert("암호가 일치하지 않습니다.");
		frm.pwd.focus();
		return false;
	}
	if (document.frm.name.value.length == 0) {
		alert("이름을 써주세요.");
		frm.name.focus();
		return false;
	}
	if (document.frm.email.value == 0) {
		alert("이메일 입력하세요!");
		frm.email.focus();
		return false;
	}
	if (document.frm.phone.value == 0) {
		alert("전화번호를 입력해세요!");
		frm.phone.focus();
		return false;
	}
	return true;
}
