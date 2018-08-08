package com.github.brandonbai.smartmonitor.service;

import java.util.List;

import com.github.brandonbai.smartmonitor.pojo.Threshold;

/**
 * 
 * ThresholdService 
 * @author Feihu Ji
 * @since 2016年10月19日
 *
 */
public interface ThresholdService {
	
	/**
	 * �޸���ֵ
	 * @param map
	 * @return
	 */
	public void updateThresholds(Threshold threshold) ;
	
	/**
	 * ��ȡ��ֵ
	 */
	public List<Threshold> getThresholds(int pageNum, int pageSize);
	
	/**
	 * ��ȡ��ֵ
	 */
	public Threshold findOne(Integer id);

}
