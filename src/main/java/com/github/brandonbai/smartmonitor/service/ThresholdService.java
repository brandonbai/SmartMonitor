package com.github.brandonbai.smartmonitor.service;

import java.util.List;

import com.github.brandonbai.smartmonitor.pojo.Threshold;

/**
 * 
 * ThresholdService 
 * @author brandonbai
 * @since 2016年10月19日
 *
 */
public interface ThresholdService {

	/**
	 * 更新阈值
	 * @param threshold
	 */
	void updateThreshold(Threshold threshold);

	/**
	 * 新增阈值
	 * @param threshold
	 */
	void insertThreshold(Threshold threshold);

	/**
	 * 获取阈值列表
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	List<Threshold> getThresholds(int pageNum, int pageSize);

	/**
	 * 查看阈值
	 * @param id
	 * @return
	 */
	Threshold findOne(Integer id);

}
