package com.github.brandonbai.smartmonitor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.brandonbai.smartmonitor.pojo.DataType;
import tk.mybatis.mapper.common.Mapper;

/**
 * 
 * DataTypeMapper 
 * @author brandonbai
 * @since 2016年10月17日
 *
 */
public interface DataTypeMapper extends Mapper<DataType> {

	void addDataType(DataType dataType);

	void deleteDataType(Integer dataTypeId);

	void updateDataType(DataType dataType);

	DataType findOne(Integer dataTypeId);

	List<DataType> findAll(@Param("pageNum") int pageNum, 
            @Param("pageSize") int pageSize);

	
	
}
