package com.github.brandonbai.smartmonitor.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jifeihu.smartmonitor.mapper.LogMapper;
import com.jifeihu.smartmonitor.pojo.Log;
import com.jifeihu.smartmonitor.service.LogService;
import com.jifeihu.smartmonitor.websocket.MessageWebSocketHandler;

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
