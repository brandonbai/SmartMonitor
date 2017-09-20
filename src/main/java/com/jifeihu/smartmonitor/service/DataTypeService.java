package com.jifeihu.smartmonitor.service;

import java.util.List;

import com.jifeihu.smartmonitor.pojo.DataType;

public interface DataTypeService {

	List<DataType> getAll(int pageNum, int pageSize);
	
	DataType getOne(Integer dataTypeId);
	
	void update(DataType dataType);
	
	void delete(Integer dataTypeId);
	
	void add(DataType dataType);
	
}
