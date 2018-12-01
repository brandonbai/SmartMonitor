package org.jifeihu.smartshed.communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

import org.jifeihu.smartshed.listener.ConnectStateEvent;
import org.jifeihu.smartshed.listener.ConnectStateListener;
import org.jifeihu.smartshed.util.PropUtils;

/**
 * 数据传输线程
 *
 */
public class RXTXThread implements Runnable {
	// 套接字
	private Socket socket;
	// 接收线程
	private AcceptThread rxThread;
	// 连接标记
	private int state = ConnectStateEvent.STATE_DISCONNECT;
	// 连接状态监听器
	private ConnectStateListener listener = null;
	// 输入流
	private InputStream inputStream = null;
	// 输出流
	private OutputStream outputStream = null;
	@Override
	public void run() {
		try {
			if(listener != null) {
				listener.onConnectStateChange(new ConnectStateEvent(ConnectStateEvent.STATE_CONNECTING));
			}
			socket = new Socket();
			socket.connect(new InetSocketAddress(PropUtils.getValue("ip"), Integer.parseInt(PropUtils.getValue("port"))), 5000);
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
			state = ConnectStateEvent.STATE_CONNECTED;
			rxThread = new AcceptThread();
			rxThread.start();
			if(listener != null) {
				listener.onConnectStateChange(new ConnectStateEvent(ConnectStateEvent.STATE_CONNECTED));
			}
		} catch (IOException e) {
			e.printStackTrace();
			if(listener != null) {
				ConnectStateEvent cse = new ConnectStateEvent(ConnectStateEvent.STATE_DISCONNECT, "连接失败");
				cse.setFormerState(state);
				listener.onConnectStateChange(cse);
			}
		}
	}
	/**
	 * 向下位机发送数据
	 * @param data
	 * @param len
	 */
	public void sendDataToTerminal(final byte[] data) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if(outputStream !=null) {
						outputStream.write(data);
					}
				} catch (IOException e) {
					e.printStackTrace();
					if(listener != null) {
						listener.onConnectStateChange(new ConnectStateEvent(ConnectStateEvent.STATE_DISCONNECT, "发生失败"));
					}
				}
			}
		}).start();
	}
	
	/**
	 * 接收数据的线程
	 *
	 */
	public class AcceptThread extends Thread {
		public void run() {
			try {
				while (state == ConnectStateEvent.STATE_CONNECTED && socket.isConnected()) {
					byte RxBuf[] = new byte[14];
					int len = inputStream.read(RxBuf);
					if (len > 0) {
						parseData(RxBuf, (byte) len);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				if(listener != null) {
					ConnectStateEvent cse = new ConnectStateEvent(ConnectStateEvent.STATE_DISCONNECT, "连接失败");
					cse.setFormerState(state);
					listener.onConnectStateChange(cse);
				}
			}
		}
	}
	/**
	 * 解析数据
	 * @param data
	 * @param len
	 */
	private void parseData(byte data[], byte len) {
		if (len == 0)
			return;

		if (data[0] != len)
			return;

		byte dataLen = (byte) (data[0] - 2);
		byte sum = checkSum(data, dataLen);
		if (data[1] != sum)
			return;

		byte fc = data[2];
		switch (fc) {
		case 2:
			int[] d = new int[]{data[4], data[5], data[11], data[13]};
			System.out.println("client accept: "+Arrays.toString(d));
			listener.dataAccept(d);
			break;
		}
	}
	/**
	 * 校验和
	 * @param data
	 * @param len
	 * @return
	 */
	private byte checkSum(byte data[], byte len) {
		byte i;
		byte checkSum = 0;

		for (i = 0; i < len; i++) {
			checkSum += data[2 + i];
		}
		return checkSum;
	}
	/**
	 * 退出
	 */
	public void exit() {
		state = ConnectStateEvent.STATE_DISCONNECT;
		try {
			if (inputStream != null) {
				inputStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			inputStream = null;
		}
		try {
			if (outputStream != null) {
				outputStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			outputStream = null;
		}
		try {
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			socket = null;
		}
	}
	
	/**
	 * 设置监听器
	 * @param listener
	 */
	public void setConnectStateListener(ConnectStateListener listener) {
		this.listener = listener;
	}
	
	/**
	 * 移除监听器
	 */
	public void removeConnectStateListener() {
		this.listener = null;
	}
}
