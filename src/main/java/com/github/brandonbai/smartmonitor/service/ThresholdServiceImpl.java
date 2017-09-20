package com.github.brandonbai.smartmonitor.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.brandonbai.smartmonitor.mapper.ThresholdMapper;
import com.github.brandonbai.smartmonitor.pojo.Threshold;

@Service
public class ThresholdServiceImpl implements ThresholdService {
	
	@Resource
	private ThresholdMapper thresholdMapper;
	
	@Override
	public void updateThresholds(Threshold threshold) {
			thresholdMapper.update(threshold);
	}

	@Override
	public List<Threshold> getThresholds(int pageNum, int pageSize) {
		return thresholdMapper.findAll(pageNum, pageSize);
	}

	@Override
	public Threshold findOne(Integer id) {
		return thresholdMapper.findOne(id);
	}

}
