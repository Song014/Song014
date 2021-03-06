package com.springbook.biz.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbook.biz.user.UserService;
import com.springbook.biz.user.UserVO;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAOMybatis userDAO;


	public void insertUser(UserVO vo) {
		userDAO.insertUser(vo); 
	}

	public String loginAction(UserVO vo) {
		 
		return userDAO.userCheck(vo); 
	}



}