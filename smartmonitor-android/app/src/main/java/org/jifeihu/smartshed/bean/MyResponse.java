package org.jifeihu.smartshed.bean;

public class MyResponse<T> {

	private Meta meta;
	private T data;

	public Meta getMeta() {
		return meta;
	}

	public T getData() {
		return data;
	}

}