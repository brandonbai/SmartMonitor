package com.github.brandonbai.smartmonitor.pojo;

import java.util.Date;

/**
 * 
 * Log 
 * 日志
 * @author Feihu Ji
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
	private String username;
	
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "Log{" +
				"content='" + content + '\'' +
				", type=" + type +
				", time=" + time +
				", username='" + username + '\'' +
				'}';
	}
}
