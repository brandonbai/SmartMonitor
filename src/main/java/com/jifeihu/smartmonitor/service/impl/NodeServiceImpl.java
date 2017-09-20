package com.jifeihu.smartmonitor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.jifeihu.smartmonitor.mapper.NodeMapper;
import com.jifeihu.smartmonitor.pojo.Node;
import com.jifeihu.smartmonitor.service.NodeService;

@Service
public class NodeServiceImpl implements NodeService{

	@Autowired
	private NodeMapper nodeMapper;
	
	@Override
	public PageInfo<Node> getAll(int pageNum, int pageSize) {
		
		List<Node> list = nodeMapper.findAll(pageNum, pageSize);
		
		return new PageInfo<Node>(list);
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
