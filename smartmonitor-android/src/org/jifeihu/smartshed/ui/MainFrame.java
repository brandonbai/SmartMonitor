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
 * 主界面
 * 
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	// 控制器
	private Controller controller;
	// 连接开始时间
	private JLabel startTimeLabel;
	// 连接状态
	private JLabel stateLabel;
	// 连接按钮
	private JButton connectBtn;
	
	public MainFrame(Controller controller) {
		this.controller = controller;
		initUI();
		addListener();
	}

	/**
	 * 初始化界面
	 */
	private void initUI() {
		setTitle("智能农业大棚管理系统");
		setSize(300, 200);
		setResizable(false);
		setContentPane(getContentPanel());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setGlassPane(getGlassPanel());
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * 获取内容面板
	 * @return
	 */
	private JPanel getContentPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		startTimeLabel = new JLabel();
		stateLabel = new JLabel("<html><h1>未连接</h1></html>");
		stateLabel.setHorizontalAlignment(JLabel.CENTER);
		connectBtn = new JButton("连接");
		panel.add(startTimeLabel, BorderLayout.NORTH);
		panel.add(stateLabel, BorderLayout.CENTER);
		panel.add(connectBtn, BorderLayout.SOUTH);
		return panel;
	}
	
	/**
	 * 获取玻璃窗格
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
		JLabel label = new JLabel("<html><h1>连接中...</h1></html>");
		label.setHorizontalAlignment(JLabel.CENTER);
		panel.add(label);
		return panel;
	}

	/**
	 * 添加监听
	 */
	private void addListener() {
		
		connectBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String command = e.getActionCommand();
				if ("断开连接".equals(command)) {
					controller.disconnect();
				} else {
					controller.connect();
				}
			}
		});
	}

	/**
	 * 设置连接状态
	 * 
	 * @param isConnected
	 */
	public void setState(boolean isConnected) {
		String connTime = isConnected ? new SimpleDateFormat(
				"开始于：yyyy-MM-dd HH:mm:ss").format(new Date()) : "";
		String connState = isConnected ? "已连接" : "未连接";
		String btnStr = isConnected ? "断开连接" : "连接";
		startTimeLabel.setText(connTime);
		stateLabel.setText("<html><h1>" + connState + "</h1></html>");
		connectBtn.setText(btnStr);
		setEnabled(true);
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		getGlassPane().setVisible(false);
	}
	
}
