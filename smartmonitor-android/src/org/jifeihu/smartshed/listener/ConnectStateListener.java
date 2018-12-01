package org.jifeihu.smartshed.listener;

/**
 * 该连接监听器接口用于接收连接事件
 * 需要监听连接状态的类实现该接口，连接对象可通过addConnectionListener
 * 方法添加监听器，当连接状态改变时便会调用onConnectStateChanged方法，当
 * 接收到数据时，便会调用dataAccepted方法
 * 
 * @see ConnectStateEvent
 * 
 */
public interface ConnectionListener {
	
	/**
	 * 连接状态发生改变
	 * @param e 连接状态改变事件
	 */
	void onConnectStateChanged(ConnectStateEvent e);
	
	/**
	 * 接收到了数据
	 * @param data 接收到的数据
	 */
	void onDataAccepted(int[] data);
}
