package org.jifeihu.smartshed.main;

import java.awt.Cursor;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import org.jifeihu.smartshed.bean.Device;
import org.jifeihu.smartshed.bean.SensorValue;
import org.jifeihu.smartshed.bean.Threshold;
import org.jifeihu.smartshed.communication.RXTXThread;
import org.jifeihu.smartshed.dao.DaoImpl;
import org.jifeihu.smartshed.listener.ConnectStateEvent;
import org.jifeihu.smartshed.listener.ConnectStateListener;
import org.jifeihu.smartshed.ui.MainFrame;
import org.jifeihu.smartshed.util.DataUtils;
import org.jifeihu.smartshed.util.PropUtils;

public class Controller implements ConnectStateListener {
	// 主界面
	private MainFrame frame;
	// 通信类
	private RXTXThread rxtx;
	// 数据库访问对象
	private DaoImpl dao;
	// 实现定时查询的定时器
	private Timer timer;
	// 设备信息的映射集
	private Map<Integer, Device> list;
	// 阈值超出的标记，只有此标记改变时才会存储阈值超出或正常的信息
	private boolean[] flags = new boolean[4];
	
	public Controller() {
		frame = new MainFrame(this);
		rxtx = new RXTXThread();
		dao = new DaoImpl();
	}
	
	
	@Override
	public void dataAccept(int[] data) {
		list = dao.getDeviceInfo();
		List<Threshold> list = dao.getThresholds();
		for (int i = 0; i < data.length; i++) {
			check(list.get(i), data[i], i);
			dao.insertData(new SensorValue(data[i], i + 1));
		}
	}


	@Override
	public void onConnectStateChange(ConnectStateEvent e) {
		int state = e.getState();
		switch(state) {
		case ConnectStateEvent.STATE_CONNECTED:
			dao.insertLog("下位机开始连接");
			frame.setState(true);
			timer = new Timer(true);
			timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					rxtx.sendDataToTerminal(DataUtils.hexString2ByteArray(PropUtils.getValue("coordinator_query")));
				}
				
			}, 0, 10*60*1000);
			break;
		case ConnectStateEvent.STATE_CONNECTING:
			frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			frame.setEnabled(false);
			frame.getGlassPane().setVisible(true);
			break;
		case ConnectStateEvent.STATE_DISCONNECT:
			frame.setState(false);
			JOptionPane.showMessageDialog(frame, e.getMessage());
			if(e.getFormerState() == ConnectStateEvent.STATE_CONNECTED) {
				dao.insertLog("下位机断开连接");
				timer.cancel();
				rxtx.removeConnectStateListener();
				rxtx.exit();
			}
			
			break;
		}
	}

	public void connect() {
		rxtx.setConnectStateListener(this);
		new Thread(rxtx).start();
	}
	
	public void disconnect() {
		dao.insertLog("下位机断开连接");
		timer.cancel();
		frame.setState(false);
		rxtx.removeConnectStateListener();
		rxtx.exit();
	}
	/**
	 * 阈值校验 判断数值是否超出阈值，超出阈值则判断“超出阈值”这一状态是否持续，
	 * 如果持续则无需向数据库存储，只有在这一状态改变时才会向数据库中存
	 * 入这一改变信息。当处理数据超出阈值，还要控制相关的设备并 更新设备信息。
	 * 
	 * @param thre
	 * @param value
	 * @param i
	 */
	private void check(Threshold thre, int value, int i) {
		String name = thre.getName();
		int max = thre.getMax();
		int min = thre.getMin();
		if (value > max || value < min) {
			if (!flags[i]) {
				StringBuilder sb = new StringBuilder(name).append("值：")
						.append(value).append(", 超出阈值：").append(max)
						.append("~").append(min);
				dao.insertLog(sb.toString());
				flags[i] = true;
			}
			if (list.containsKey(i)) {
				Device device = list.get(i);
				if ("水泵".equals(device.getName())) {
					if (value > max && device.isOpen()) {
						rxtx.sendDataToTerminal(DataUtils.hexString2ByteArray(device.getCommandOff()));
						dao.setDeviceState(i, false);
					} else if (value < min && !device.isOpen()) {
						rxtx.sendDataToTerminal(DataUtils.hexString2ByteArray(device.getCommandOn()));
						dao.setDeviceState(i, true);
					}
				} else {
					if (value > max && !device.isOpen()) {
						rxtx.sendDataToTerminal(DataUtils.hexString2ByteArray(device.getCommandOn()));
						dao.setDeviceState(i, true);
					} else if (value < min && device.isOpen()) {
						rxtx.sendDataToTerminal(DataUtils.hexString2ByteArray(device.getCommandOff()));
						dao.setDeviceState(i, false);
					}
				}
			}
		} else {
			if (flags[i]) {
				StringBuilder sb = new StringBuilder(name).append("值：")
						.append(value).append(", 正常");
				dao.insertLog(sb.toString());
				flags[i] = false;
				if (list.containsKey(i)) {
					Device device = list.get(i);
					if (device.isOpen()) {
						rxtx.sendDataToTerminal(DataUtils.hexString2ByteArray(device.getCommandOff()));
						dao.setDeviceState(i, false);
					}
				}
			}
		}
	}

}
