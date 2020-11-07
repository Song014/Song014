package com.springbook.biz.user;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;


public class UserVO {
	@NotEmpty(message="�̸��� �Է��ϼ���!")
	private String name;
	@NotEmpty(message="���̵� �Է��ϼ���!")
	private String id;
	@NotEmpty(message="�н����带 �Է��ϼ���!")
	private String pwd;
	@NotEmpty(message="�̸����� �Է��ϼ���!")
	@Email(message="�̸��� ������ �ƴմϴ�. 'xxxx@xxxx.xxx'")
	private String email;
	@NotEmpty(message="����ȣ�� �Է��ϼ���!")
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