package com.springbook.biz.user;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;


public class UserVO {
	@NotEmpty(message="이름을 입력하세요!")
	private String name;
	@NotEmpty(message="아이디를 입력하세요!")
	private String id;
	@NotEmpty(message="패스워드를 입력하세요!")
	private String pwd;
	@NotEmpty(message="이메일을 입력하세요!")
	@Email(message="이메일 형식이 아닙니다. 'xxxx@xxxx.xxx'")
	private String email;
	@NotEmpty(message="폰번호를 입력하세요!")
	private String phone;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "UserVO [name=" + name + ", id=" + id + ", pwd=" + pwd + ", email=" + email + ", phone=" + phone + "]";
	}
	
	
	
}