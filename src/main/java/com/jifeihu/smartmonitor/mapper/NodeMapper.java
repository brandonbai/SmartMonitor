package com.jifeihu.smartmonitor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jifeihu.smartmonitor.pojo.Node;

public interface NodeMapper {

	List<Node> findAll(@Param("pageNum") int pageNum, 
            @Param("pageSize") int pageSize);

	Node findOnde(Integer nodeId);

	void update(Node node);

	void delete(Integer nodeId);

	void add(Node node);

	
}
