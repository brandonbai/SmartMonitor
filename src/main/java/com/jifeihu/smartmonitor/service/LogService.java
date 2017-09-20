package com.jifeihu.smartmonitor.service;

import java.util.List;

import com.jifeihu.smartmonitor.pojo.Log;

public interface LogService {
	
	List<Log> getLog();
	
	List<Log> getLog(String date);
	
	void addLog(Log log);

}
