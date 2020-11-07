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

	// CRUD 기능의 메소드 구현
	// 회원가입
	public void insertUser(UserVO vo) {
		System.out.println("===> Mybatis로  insertUser() 기능 처리");
		mybatis.insert("UserDAO.insertUser", vo);
	}

	public String userCheck(UserVO vo) {
		System.out.println("===> Mybatis로  loginAction() 기능 처리");
		
		return mybatis.selectOne("UserDAO.userCheck", vo);
	}

}
