package com.github.brandonbai.smartmonitor.pojo;

import com.github.brandonbai.smartmonitor.exception.MsgException;
import com.github.brandonbai.smartmonitor.utils.TextUtils;

/**
 * 
 * User 
 * @Description: 用户
 * @author Feihu Ji
 * @sine 2016年10月11日
 *
 */
public class User {
	private Integer id;
	private String username;
	private String password;
	private String newPassword;
	private Integer roleId;
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
	
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
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
				+ password + ", newPassword=" + newPassword + ", roleId="
				+ roleId + ", name=" + name + ", state=" + state + ", tel="
				+ tel + "]";
	}

	/**
	 * �޸��û���Ϣ�������֤
	 * @throws MsgException
	 */
	public void checkUpdate() throws MsgException {
		
		if(TextUtils.isEmpty(username)) {
			throw new MsgException("用户名不能为空");
		}
		
		if(TextUtils.isEmpty(password)) {
			throw new MsgException("密码不能为空");
		}
		
		if(TextUtils.isEmpty(newPassword)) {
			throw new MsgException("新密码不能为空");
		}
		
		if(TextUtils.isEmpty(name)) {
			throw new MsgException("姓名不能为空");
		}
		
		if(!newPassword.matches("\\w{6,15}")) {
			throw new MsgException("密码位数为6~15");
		}
		
		if(TextUtils.isEmpty(tel)) {
			throw new MsgException("电话不能为空");
		}
		
		if(!tel.matches("^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$")) {
			throw new MsgException("手机号码格式不正确");
		}
		
	}
	
}
