package com.github.brandonbai.smartmonitor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.jifeihu.smartmonitor.mapper.DataTypeMapper;
import com.jifeihu.smartmonitor.pojo.DataType;
import com.jifeihu.smartmonitor.service.DataTypeService;

@Service
public class DataTypeServiceImpl implements DataTypeService {

	@Autowired
	private DataTypeMapper dataTypeMapper;
	
	@Override
	public PageInfo<DataType> getAll(int pageNum, int pageSize) {
		
		List<DataType> list = dataTypeMapper.findAll(pageNum, pageSize);
		
		return new PageInfo<DataType>(list);
	}

	@Override
	public DataType getOne(Integer dataTypeId) {

		return dataTypeMapper.findOne(dataTypeId);
	}

	@Override
	public void update(DataType dataType) {
		
		dataTypeMapper.update(dataType);
	}

	@Override
	public void delete(Integer dataTypeId) {
		
		dataTypeMapper.delete(dataTypeId);
		
	}

	@Override
	public void add(DataType dataType) {

		dataTypeMapper.add(dataType);
		
	}

	
}
