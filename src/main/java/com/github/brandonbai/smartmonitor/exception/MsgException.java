package com.github.brandonbai.smartmonitor.exception;

/**
 * 
 * MsgException 
 * 自定义异常
 * @author Feihu Ji
 * @since 2016年10月15日
 *
 */
@SuppressWarnings("serial")
public class MsgException extends Exception {
	
	public MsgException() {
		
	}

	public MsgException(String message) {
		super(message);
	}
	
}
