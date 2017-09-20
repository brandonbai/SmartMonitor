package com.github.brandonbai.smartmonitor.service;

import java.util.List;

import com.jifeihu.smartmonitor.pojo.Threshold;


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
