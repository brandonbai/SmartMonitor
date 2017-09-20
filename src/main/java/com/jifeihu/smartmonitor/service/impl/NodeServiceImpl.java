package com.jifeihu.smartmonitor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jifeihu.smartmonitor.mapper.NodeMapper;
import com.jifeihu.smartmonitor.pojo.Node;
import com.jifeihu.smartmonitor.service.NodeService;

@Service
public class NodeServiceImpl implements NodeService{

	@Autowired
	private NodeMapper nodeMapper;
	
	@Override
	public List<Node> getAll(int pageNum, int pageSize) {
		
		return nodeMapper.findAll(pageNum, pageSize);
	}

	@Override
	public Node getOne(Integer nodeId) {
		
		return nodeMapper.findOnde(nodeId);
	}

	@Override
	public void update(Node node) {
		
		nodeMapper.update(node);
		
	}

	@Override
	public void delete(Integer nodeId) {
		
		nodeMapper.delete(nodeId);
		
	}

	@Override
	public void add(Node node) {
		
		nodeMapper.add(node);
		
	}

	
}
