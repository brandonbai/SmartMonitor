package com.jifeihu.smartmonitor.service;

import java.util.List;

import com.jifeihu.smartmonitor.pojo.Node;

public interface NodeService {

	List<Node> getAll(int pageNum, int pageSize);
	
	Node getOne(Integer nodeId);
	
	void update(Node node);
	
	void delete(Integer nodeId);
	
	void add(Node node);
	
}
