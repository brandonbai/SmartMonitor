package com.github.brandonbai.smartmonitor.pojo;

/**
 * 
 * Meta 
 * @Description: 统一返回JSON对应的信息类
 * @author Feihu Ji
 * @sine 2016年10月15日
 *
 */
public class Meta {

	private boolean success;
	private String message;

	public Meta(boolean success) {
		this.success = success;
	}

	public Meta(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}
}