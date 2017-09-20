package com.github.brandonbai.smartmonitor.service;

import java.util.List;

import com.github.brandonbai.smartmonitor.pojo.Log;

public interface LogService {
	
	List<Log> getLog();
	
	List<Log> getLog(String date);
	
	void addLog(Log log);

}
