package org.jifeihu.smartshed.ui;

/**
 * view��ӿ�
 *
 */
public interface View {
	/**
	 * ����
	 */
	public void connect();
	
	/**
	 * �Ͽ�����
	 */
	public void disconnect();
	/**
	 * ������
	 */
	public void showConnecting();
	/**
	 * ���ӳɹ�
	 */
	public void showConnectSuccessed();
	/**
	 * ����ʧ��
	 * @param message ����ʧ����Ϣ
	 */
	public void showConnectFailed(String message);
}
