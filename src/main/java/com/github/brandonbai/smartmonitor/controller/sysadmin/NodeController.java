package com.github.brandonbai.smartmonitor.controller.sysadmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.brandonbai.smartmonitor.pojo.Node;
import com.github.brandonbai.smartmonitor.pojo.Response;
import com.github.brandonbai.smartmonitor.service.NodeService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * NodeController 
 * @Description: 节点处理
 * @author Feihu Ji
 * @sine 2016年10月15日
 * 
 */
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
		
		PageInfo<Node> pi = nodeService.getAll(pageNum, pageSize);
		
		return new Response().success(pi);
	}
	
}
