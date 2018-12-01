package org.jifeihu.smartshed.ui;

/**
 * view层接口
 *
 */
public interface View {
	/**
	 * 连接
	 */
	public void connect();
	
	/**
	 * 断开连接
	 */
	public void disconnect();
	/**
	 * 连接中
	 */
	public void showConnecting();
	/**
	 * 连接成功
	 */
	public void showConnectSuccessed();
	/**
	 * 连接失败
	 * @param message 连接失败信息
	 */
	public void showConnectFailed(String message);
}
