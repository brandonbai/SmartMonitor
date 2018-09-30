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
	ROLE_ADMIN("ADMIN"),
	/**用户*/
	ROLE_USER("USER");
	
	private String flag;
	
	RoleType(String flag) {
		this.flag = flag;
	}

	public String getFlag() {
		return flag;
	}

	public static String getFlag(int id) {
		if(id == 0) {
			return ROLE_ADMIN.flag;
		}
		return ROLE_USER.flag;
	}
	
}
