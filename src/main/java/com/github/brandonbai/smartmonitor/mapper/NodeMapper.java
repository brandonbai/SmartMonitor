package com.github.brandonbai.smartmonitor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.brandonbai.smartmonitor.pojo.Node;

/**
 * 
 * NodeMapper 
 * @author Feihu Ji
 * @since 2016年10月17日
 *
 */
public interface NodeMapper {

	List<Node> findAll(@Param("pageNum") int pageNum, 
            @Param("pageSize") int pageSize);

	Node findOne(Integer nodeId);

	void update(Node node);

	void delete(Integer nodeId);

	void add(Node node);

	
}
