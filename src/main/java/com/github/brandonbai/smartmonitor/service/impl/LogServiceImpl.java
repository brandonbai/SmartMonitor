package com.github.brandonbai.smartmonitor.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.brandonbai.smartmonitor.mapper.LogMapper;
import com.github.brandonbai.smartmonitor.pojo.Log;
import com.github.brandonbai.smartmonitor.service.LogService;
import com.github.brandonbai.smartmonitor.websocket.MessageWebSocketHandler;

/**
 * 
 * LogServiceImpl 
 * @author Feihu Ji
 * @sine 2016年10月19日
 *
 */
@Service
public class LogServiceImpl implements LogService {
	
	@Resource
	private LogMapper logMapper;
	@Autowired
	private MessageWebSocketHandler messageWebSocketHandler;

	@Override
	public List<Log> getLog() {
		return logMapper.findLog();
	}
	
	@Override
	public List<Log> getLog(String date) {
		return logMapper.findLogByDate(date);
	}

	@Override
	public void addLog(Log log) {
		logMapper.addLog(log);
		messageWebSocketHandler.sendMessage(Integer.parseInt(log.getUsername()), log);
	}

}
