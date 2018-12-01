package org.jifeihu.smartshed.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jifeihu.smartshed.main.Controller;

/**
 * ������
 * 
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	// ������
	private Controller controller;
	// ���ӿ�ʼʱ��
	private JLabel startTimeLabel;
	// ����״̬
	private JLabel stateLabel;
	// ���Ӱ�ť
	private JButton connectBtn;
	
	public MainFrame(Controller controller) {
		this.controller = controller;
		initUI();
		addListener();
	}

	/**
	 * ��ʼ������
	 */
	private void initUI() {
		setTitle("����ũҵ�������ϵͳ");
		setSize(300, 200);
		setResizable(false);
		setContentPane(getContentPanel());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setGlassPane(getGlassPanel());
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * ��ȡ�������
	 * @return
	 */
	private JPanel getContentPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		startTimeLabel = new JLabel();
		stateLabel = new JLabel("<html><h1>δ����</h1></html>");
		stateLabel.setHorizontalAlignment(JLabel.CENTER);
		connectBtn = new JButton("����");
		panel.add(startTimeLabel, BorderLayout.NORTH);
		panel.add(stateLabel, BorderLayout.CENTER);
		panel.add(connectBtn, BorderLayout.SOUTH);
		return panel;
	}
	
	/**
	 * ��ȡ��������
	 * @return
	 */
	public JPanel getGlassPanel() {
		JPanel panel = new JPanel() {
			
			@Override
			protected void paintComponent(Graphics g) {
				Color c = getBackground();
				g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 150));
				
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		panel.setOpaque(false);
		JLabel label = new JLabel("<html><h1>������...</h1></html>");
		label.setHorizontalAlignment(JLabel.CENTER);
		panel.add(label);
		return panel;
	}

	/**
	 * ��Ӽ���
	 */
	private void addListener() {
		
		connectBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String command = e.getActionCommand();
				if ("�Ͽ�����".equals(command)) {
					controller.disconnect();
				} else {
					controller.connect();
				}
			}
		});
	}

	/**
	 * ��������״̬
	 * 
	 * @param isConnected
	 */
	public void setState(boolean isConnected) {
		String connTime = isConnected ? new SimpleDateFormat(
				"��ʼ�ڣ�yyyy-MM-dd HH:mm:ss").format(new Date()) : "";
		String connState = isConnected ? "������" : "δ����";
		String btnStr = isConnected ? "�Ͽ�����" : "����";
		startTimeLabel.setText(connTime);
		stateLabel.setText("<html><h1>" + connState + "</h1></html>");
		connectBtn.setText(btnStr);
		setEnabled(true);
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		getGlassPane().setVisible(false);
	}
	
}
