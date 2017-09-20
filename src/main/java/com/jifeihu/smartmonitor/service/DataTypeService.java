package com.jifeihu.smartmonitor.service;

import com.github.pagehelper.PageInfo;
import com.jifeihu.smartmonitor.pojo.DataType;

public interface DataTypeService {

	PageInfo<DataType> getAll(int pageNum, int pageSize);
	
	DataType getOne(Integer dataTypeId);
	
	void update(DataType dataType);
	
	void delete(Integer dataTypeId);
	
	void add(DataType dataType);
	
}
