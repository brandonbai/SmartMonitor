package com.jifeihu.smartmonitor.controller.sysadmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.jifeihu.smartmonitor.pojo.Node;
import com.jifeihu.smartmonitor.pojo.Response;
import com.jifeihu.smartmonitor.service.NodeService;

@RestController
@RequestMapping("/node")
public class NodeController {

	@Autowired
	private NodeService nodeService;
	
	@RequestMapping("/add")
	public Response nodeAdd(Node node) {
		
		nodeService.add(node);
		
		return new Response().success();
	}
	
	@RequestMapping("/del")
	public Response nodeDel(Integer nodeId) {
		
		nodeService.delete(nodeId);
		
		return new Response().success();
	}
	
	@RequestMapping("/update")
	public Response nodeUpdate(Node node) {
		
		nodeService.update(node);
		
		return new Response().success();
	}
	
	@RequestMapping("/one")
	public Response nodeOne(Integer nodeId) {
		
		nodeService.getOne(nodeId);
		
		return new Response().success();
	}
	
	@RequestMapping("/list")
	public Response nodeList(@RequestParam(defaultValue="0")Integer pageNum, @RequestParam(defaultValue="0")Integer pageSize) {
		
		PageInfo<Node> pageInfo = nodeService.getAll(pageNum, pageSize);
		
		return new Response().success(pageInfo);
	}
	
}
