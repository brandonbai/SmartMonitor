package com.github.brandonbai.smartmonitor.pojo;

/**
 * 
 * Response 
 * 统一返回的JSON格式对应的实体类
 * @author Feihu Ji
 * @since 2016年10月11日
 *
 */
public class Response {

	private static final String OK = "ok";
	private static final String ERROR = "error";

	private Meta meta;
	private Object data;

	public Response success() {
		this.meta = new Meta(true, OK);
		return this;
	}

	public Response success(Object data) {
		this.meta = new Meta(true, OK);
		this.data = data;
		return this;
	}

	public Response failure() {
		this.meta = new Meta(false, ERROR);
		return this;
	}

	public Response failure(String message) {
		this.meta = new Meta(false, message);
		return this;
	}

	public Meta getMeta() {
		return meta;
	}

	public Object getData() {
		return data;
	}

}