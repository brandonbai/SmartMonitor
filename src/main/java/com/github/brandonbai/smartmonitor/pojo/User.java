package com.github.brandonbai.smartmonitor.pojo;

import com.jifeihu.smartmonitor.exception.MsgException;
import com.jifeihu.smartmonitor.utils.TextUtils;

public class User {
	private Integer id;
	private String username;
	private String password;
	private String newPassword;
	private Integer power;
	private String name;
	private Integer state;
	private String tel;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public Integer getPower() {
		return power;
	}

	public void setPower(Integer power) {
		this.power = power;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password="
				+ password + ", newPassword=" + newPassword + ", power="
				+ power + ", name=" + name + ", state=" + state + ", tel="
				+ tel + "]";
	}

	/**
	 * �޸��û���Ϣ�������֤
	 * @throws MsgException
	 */
	public void checkUpdate() throws MsgException {
		
		if(TextUtils.isEmpty(username)) {
			throw new MsgException("�û�����Ϊ��");
		}
		
		if(TextUtils.isEmpty(password)) {
			throw new MsgException("���벻��Ϊ��");
		}
		
		if(TextUtils.isEmpty(newPassword)) {
			throw new MsgException("�����벻��Ϊ��");
		}
		
		if(TextUtils.isEmpty(name)) {
			throw new MsgException("������Ϊ��");
		}
		
		if(!newPassword.matches("\\w{6,15}")) {
			throw new MsgException("���볤��Ϊ6~15");
		}
		
		if(TextUtils.isEmpty(tel)) {
			throw new MsgException("�绰����Ϊ��");
		}
		
		if(!tel.matches("1[358]\\d{9}")) {
			throw new MsgException("�ֻ�����ʽ����ȷ");
		}
		
	}
	
}
