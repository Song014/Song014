package com.springbook.biz.user;

import java.util.List;

public interface UserService {
	// CRUD 기능의 메소드 구현
	// 글 목록 조회
	//List<UserVO> getBoardList(UserVO vo);
	
	// 글 상세 조회
	//UserVO getBoard(UserVO vo);
	
	// 회원가입
	void insertUser(UserVO vo);
	
	//비밀번호 체크를 위한 비밀번호 가져오기
	String loginAction(UserVO vo);
	

}
