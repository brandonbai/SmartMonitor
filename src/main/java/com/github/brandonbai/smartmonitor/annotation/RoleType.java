package com.github.brandonbai.smartmonitor.annotation;

/**
 * 
 * RoleType 
 * 角色类型
 * @author Feihu Ji
 * 
 */
public enum RoleType {
	/**管理员*/
	ROLE_ADMIN(1), 
	/**用户*/
	ROLE_USER(2);
	
	private int id;
	
	RoleType(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
}
