package com.github.brandonbai.smartmonitor.service;

import com.github.brandonbai.smartmonitor.pojo.DataType;
import com.github.pagehelper.PageInfo;

public interface DataTypeService {

	PageInfo<DataType> getAll(int pageNum, int pageSize);
	
	DataType getOne(Integer dataTypeId);
	
	void update(DataType dataType);
	
	void delete(Integer dataTypeId);
	
	void add(DataType dataType);
	
}
