package com.github.brandonbai.smartmonitor.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.github.brandonbai.smartmonitor.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.brandonbai.smartmonitor.mapper.ThresholdMapper;
import com.github.brandonbai.smartmonitor.pojo.Threshold;
import com.github.brandonbai.smartmonitor.service.ThresholdService;

/**
 * 
 * ThresholdServiceImpl 
 * @author brandonbai
 * @since 2016年10月19日
 *
 */
@Service
public class ThresholdServiceImpl implements ThresholdService {
	
	@Resource
	private ThresholdMapper thresholdMapper;

	@Autowired
	private RedisService redisService;
	
	@Override
	public void updateThreshold(Threshold threshold) {
		thresholdMapper.updateThreshold(threshold);
		redisService.setThreshold(threshold);
	}

	@Override
	public void insertThreshold(Threshold threshold) {
		thresholdMapper.insertThreshold(threshold);
		redisService.setThreshold(threshold);
	}

	@Override
	public List<Threshold> getThresholds(int pageNum, int pageSize) {
		// TODO
		return thresholdMapper.findAll(pageNum, pageSize);
	}

	@Override
	public Threshold findOne(Integer id) {
		return thresholdMapper.findOne(id);
	}

}
