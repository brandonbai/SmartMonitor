package com.github.brandonbai.smartmonitor.mapper;

import java.util.List;

import com.github.brandonbai.smartmonitor.pojo.Log;

/**
 * 
 * LogMapper 
 * @author Feihu Ji
 * @sine 2016年10月17日
 *
 */
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
