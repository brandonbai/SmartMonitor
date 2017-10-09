package com.github.brandonbai.smartmonitor.annotation;

/**
 * 
 * RoleType 
 * @Description: 角色类型
 * @author Feihu Ji
 * 
 */
public enum RoleType {
	
	ROLE_ADMIN(1), ROLE_USER(2);
	
	private int id;
	
	RoleType(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
}
