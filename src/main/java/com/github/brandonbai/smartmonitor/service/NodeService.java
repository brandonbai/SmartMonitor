package com.github.brandonbai.smartmonitor.service;

import com.github.brandonbai.smartmonitor.pojo.Node;
import com.github.pagehelper.PageInfo;

public interface NodeService {

	PageInfo<Node> getAll(int pageNum, int pageSize);
	
	Node getOne(Integer nodeId);
	
	void update(Node node);
	
	void delete(Integer nodeId);
	
	void add(Node node);
	
}
