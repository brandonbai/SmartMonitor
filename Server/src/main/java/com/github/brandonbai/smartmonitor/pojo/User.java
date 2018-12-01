package com.github.brandonbai.smartmonitor.pojo;

import com.github.brandonbai.smartmonitor.exception.MsgException;
import com.github.brandonbai.smartmonitor.utils.TextUtils;

/**
 * 
 * User 
 * 用户
 * @author brandonbai
 * @since 2016年10月11日
 *
 */
public class User {
	private Integer id;
	private String username;
	private String password;
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
				+ password + ", roleId="
				+ roleId + ", name=" + name + ", state=" + state + ", tel="
				+ tel + "]";
	}
	
}
