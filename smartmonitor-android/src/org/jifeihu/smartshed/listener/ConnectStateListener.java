package org.jifeihu.smartshed.listener;

/**
 * �����Ӽ������ӿ����ڽ��������¼�
 * ��Ҫ��������״̬����ʵ�ָýӿڣ����Ӷ����ͨ��addConnectionListener
 * ������Ӽ�������������״̬�ı�ʱ������onConnectStateChanged��������
 * ���յ�����ʱ��������dataAccepted����
 * 
 * @see ConnectStateEvent
 * 
 */
public interface ConnectionListener {
	
	/**
	 * ����״̬�����ı�
	 * @param e ����״̬�ı��¼�
	 */
	void onConnectStateChanged(ConnectStateEvent e);
	
	/**
	 * ���յ�������
	 * @param data ���յ�������
	 */
	void onDataAccepted(int[] data);
}
