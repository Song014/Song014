package com.springbook.biz.user;

import java.util.List;

public interface UserService {
	// CRUD ����� �޼ҵ� ����
	// �� ��� ��ȸ
	//List<UserVO> getBoardList(UserVO vo);
	
	// �� �� ��ȸ
	//UserVO getBoard(UserVO vo);
	
	// ȸ������
	void insertUser(UserVO vo);
	
	//��й�ȣ üũ�� ���� ��й�ȣ ��������
	String loginAction(UserVO vo);
	

}
