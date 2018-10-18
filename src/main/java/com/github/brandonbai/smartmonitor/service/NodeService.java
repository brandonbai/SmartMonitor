package com.github.brandonbai.smartmonitor.service;

import com.github.brandonbai.smartmonitor.pojo.Node;
import com.github.pagehelper.PageInfo;

/**
 * 
 * NodeService 
 * @author brandonbai
 * @since 2016年10月19日
 *
 */
public interface NodeService {

	PageInfo<Node> getAll(int pageNum, int pageSize);
	
	Node getOne(Integer nodeId);
	
	void update(Node node);
	
	void delete(Integer nodeId);
	
	void add(Node node);
	
}
