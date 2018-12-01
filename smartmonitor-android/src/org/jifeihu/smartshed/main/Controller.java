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
	// ������
	private MainFrame frame;
	// ͨ����
	private RXTXThread rxtx;
	// ���ݿ���ʶ���
	private DaoImpl dao;
	// ʵ�ֶ�ʱ��ѯ�Ķ�ʱ��
	private Timer timer;
	// �豸��Ϣ��ӳ�伯
	private Map<Integer, Device> list;
	// ��ֵ�����ı�ǣ�ֻ�д˱�Ǹı�ʱ�Ż�洢��ֵ��������������Ϣ
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
			dao.insertLog("��λ����ʼ����");
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
				dao.insertLog("��λ���Ͽ�����");
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
		dao.insertLog("��λ���Ͽ�����");
		timer.cancel();
		frame.setState(false);
		rxtx.removeConnectStateListener();
		rxtx.exit();
	}
	/**
	 * ��ֵУ�� �ж���ֵ�Ƿ񳬳���ֵ��������ֵ���жϡ�������ֵ����һ״̬�Ƿ������
	 * ������������������ݿ�洢��ֻ������һ״̬�ı�ʱ�Ż������ݿ��д�
	 * ����һ�ı���Ϣ�����������ݳ�����ֵ����Ҫ������ص��豸�� �����豸��Ϣ��
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
				StringBuilder sb = new StringBuilder(name).append("ֵ��")
						.append(value).append(", ������ֵ��").append(max)
						.append("~").append(min);
				dao.insertLog(sb.toString());
				flags[i] = true;
			}
			if (list.containsKey(i)) {
				Device device = list.get(i);
				if ("ˮ��".equals(device.getName())) {
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
				StringBuilder sb = new StringBuilder(name).append("ֵ��")
						.append(value).append(", ����");
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
