package org.jifeihu.smartshed.listener;

/**
 * �����¼�
 * @see ConnectionListener
 */
public class ConnectStateEvent {
	/** ���������� */
	public static final int STATE_CONNECTING = 0;
	/** ������ */
	public static final int STATE_CONNECTED = 1;
	/** δ���� */
	public static final int STATE_DISCONNECT = 2;

	// ��ǰ����״̬
	private int state = STATE_DISCONNECT;
	// ��Ϣ
	private String message;
	// ֮ǰ������״̬
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
