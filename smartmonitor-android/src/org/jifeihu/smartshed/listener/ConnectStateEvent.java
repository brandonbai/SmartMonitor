package org.jifeihu.smartshed.listener;

/**
 * 连接事件
 * @see ConnectionListener
 */
public class ConnectStateEvent {
	/** 尝试连接中 */
	public static final int STATE_CONNECTING = 0;
	/** 已连接 */
	public static final int STATE_CONNECTED = 1;
	/** 未连接 */
	public static final int STATE_DISCONNECT = 2;

	// 当前连接状态
	private int state = STATE_DISCONNECT;
	// 消息
	private String message;
	// 之前的连接状态
	private int formerState = STATE_DISCONNECT;

	public ConnectStateEvent(int state) {
		this.state = state;
	}

	public ConnectStateEvent(int state, String message) {
		this.state = state;
		this.message = message;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getFormerState() {
		return formerState;
	}

	public void setFormerState(int formerState) {
		this.formerState = formerState;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
