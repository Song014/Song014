package com.springbook.biz.user.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springbook.biz.board.BoardVO;
import com.springbook.biz.user.UserVO;

//DAO(Data Access Object)
@Repository
public class UserDAOMybatis {
	@Autowired
	private SqlSessionTemplate mybatis;

	// CRUD ����� �޼ҵ� ����
	// ȸ������
	public void insertUser(UserVO vo) {
		System.out.println("===> Mybatis��  insertUser() ��� ó��");
		mybatis.insert("UserDAO.insertUser", vo);
	}

	public String userCheck(UserVO vo) {
		System.out.println("===> Mybatis��  loginAction() ��� ó��");
		
		return mybatis.selectOne("UserDAO.userCheck", vo);
	}

}
