package com.jifeihu.smartmonitor.mapper;

import java.util.List;

import com.jifeihu.smartmonitor.pojo.Log;

public interface LogMapper {  
	
	/**
	 * �鿴���һ��ʱ�����־
	 * @return
	 */
	public List<Log> findLog();
	
	/**
	 * �鿴ָ�����ڵ���־
	 * @param date
	 * @return
	 */
	public List<Log> findLogByDate(String date);
	
	/**
	 * ��¼ϵͳ��־
	 * @param log
	 */
	public void addLog(Log log);
	
}