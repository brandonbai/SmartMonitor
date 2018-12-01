package com.github.brandonbai.smartmonitor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.brandonbai.smartmonitor.pojo.Node;
import tk.mybatis.mapper.common.Mapper;

/**
 * 
 * NodeMapper 
 * @author brandonbai
 * @since 2016年10月17日
 *
 */
public interface NodeMapper extends Mapper<Node> {

	List<Node> findAll(@Param("pageNum") int pageNum, 
            @Param("pageSize") int pageSize);

	Node findOne(Integer nodeId);

	void updateNode(Node node);

	void deleteNode(Integer nodeId);

	void addNode(Node node);

	
}
