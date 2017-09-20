package com.jifeihu.smartmonitor.service;

import com.github.pagehelper.PageInfo;
import com.jifeihu.smartmonitor.pojo.Node;

public interface NodeService {

	PageInfo<Node> getAll(int pageNum, int pageSize);
	
	Node getOne(Integer nodeId);
	
	void update(Node node);
	
	void delete(Integer nodeId);
	
	void add(Node node);
	
}
