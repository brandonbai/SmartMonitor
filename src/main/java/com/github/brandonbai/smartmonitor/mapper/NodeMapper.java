package com.github.brandonbai.smartmonitor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.brandonbai.smartmonitor.pojo.Node;

public interface NodeMapper {

	List<Node> findAll(@Param("pageNum") int pageNum, 
            @Param("pageSize") int pageSize);

	Node findOnde(Integer nodeId);

	void update(Node node);

	void delete(Integer nodeId);

	void add(Node node);

	
}
