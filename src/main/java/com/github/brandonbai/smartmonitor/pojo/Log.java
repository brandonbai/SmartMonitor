package com.github.brandonbai.smartmonitor.pojo;

import java.util.Date;

/**
 * 
 * Log 
 * 日志
 * @author brandonbai
 * @since 2016年10月11日
 *
 */
public class Log {

	public static final int INFORMATION_CONNECT = 1;
	public static final int CHANGE_THRESHOLD = 2;
	public static final int CONTROL_DEVICE = 3;
	public static final int CHANGE_SYSTEM_INFORMATION = 4;
	public static final int OUT_OF_THRESHOLD = 5;
	
	private int type;
	private Date time;
	private String content;
	private Integer userId;
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getTime() {
		return time;
	}
	
	public void setTime(Date time) {
		this.time = time;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Log{" +
				"content='" + content + '\'' +
				", type=" + type +
				", time=" + time +
				", userId='" + userId + '\'' +
				'}';
	}
}
