package org.jifeihu.smartshed.util;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Arrays;

public class TerminalService {

	private static final String TAG = TerminalService.class.getSimpleName();
	/** 数据查询*/
	public static final byte[] QUERY = {3,1,1};
	/** 水泵关*/
	public static final byte[] WATER_ON = {5,6,4,2,0};
	/** 水泵开*/
	public static final byte[] WATER_OFF = {5,7,4,2,1};
	/** 卷帘开*/
	public static final byte[] DOOR_ON = {5,10,5,4,1};
	/** 卷帘关*/
	public static final byte[] DOOR_OFF = {5,11,5,4,2};
	/** 卷帘电机加速*/
	public static final byte[] DOOR_SPEED = {5,12,5,4,3};
	/** 卷帘电机减速*/
	public static final byte[] DOOR_SLOW = {5,13,5,4,4};
	/** 卷帘停*/
	public static final byte[] DOOR_STOP = {5,14,5,4,5};

	// ip地址
	private String ip;
	// 端口号
	private int port;


	public TerminalService(Context context) {
		ip = SharedPreferenceUtil.getInstance(context).getNetworkInfo()[0];
		port = Integer.parseInt(SharedPreferenceUtil.getInstance(context).getNetworkInfo()[1]);
	}

	public Byte[] send(byte[] data) {
		SocketAddress socketAddress = new InetSocketAddress(ip, port);

		Socket socket = null;
		InputStream in = null;
		OutputStream out = null;
		try {
			socket = new Socket();
			socket.connect(socketAddress, 3000);

			in = socket.getInputStream();
			out = socket.getOutputStream();

			out.write(data, 0, data.length);
			Log.i(TAG, "send "+Arrays.toString(data));

			byte[] result = new byte[64];
			int len = in.read(result);

			return parseData(result, len);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private Byte[] parseData(byte data[], int len) {
		if (len == 0)
			return null;

		if (data[0] != len)
			return null;

		byte dataLen = (byte) (data[0] - 2);
		byte sum = checkSum(data, dataLen);
		if (data[1] != sum)
			return null;

		byte fc = data[2];
		switch (fc) {
		case 2:
			Byte[] result = new Byte[]{data[4], data[5], data[3], data[11], data[13]};
			Log.i(TAG, "accept "+Arrays.toString(result));
			return result;
		}
		return null;
	}

	private byte checkSum(byte data[], byte len) {
		byte i;
		byte check_sum = 0;

		for (i = 0; i < len; i++) {
			check_sum += data[2 + i];
		}
		return check_sum;
	}

}
