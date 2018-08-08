package com.github.brandonbai.smartmonitor.service;

import java.util.List;

import com.github.brandonbai.smartmonitor.pojo.Log;

/**
 * 
 * LogService 
 * @author Feihu Ji
 * @since 2016年10月19日
 *
 */
public interface LogService {
	
	List<Log> getLog();
	
	List<Log> getLog(String date);
	
	void addLog(Log log);

}
