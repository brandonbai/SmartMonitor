package com.github.brandonbai.smartmonitor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.brandonbai.smartmonitor.pojo.DataType;

/**
 * 
 * DataTypeMapper 
 * @author brandonbai
 * @since 2016年10月17日
 *
 */
public interface DataTypeMapper {

	void add(DataType dataType);

	void delete(Integer dataTypeId);

	void update(DataType dataType);

	DataType findOne(Integer dataTypeId);

	List<DataType> findAll(@Param("pageNum") int pageNum, 
            @Param("pageSize") int pageSize);

	
	
}
