package com.github.brandonbai.smartmonitor.controller.sysadmin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.github.brandonbai.smartmonitor.pojo.Node;
import com.github.brandonbai.smartmonitor.pojo.Response;
import com.github.brandonbai.smartmonitor.service.NodeService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * NodeController 
 * 节点处理
 * @author Feihu Ji
 * @since 2016年10月15日
 * 
 */
@RestController
@RequestMapping("/node")
@CrossOrigin
@Api(tags="节点管理")
public class NodeController {

	@Autowired
	private NodeService nodeService;
	
	@RequestMapping("/add")
	@ApiOperation(value="新增节点", response = Response.class)
	public Response nodeAdd(@RequestBody Node node) {
		
		nodeService.add(node);
		
		return new Response().success();
	}
	
	@RequestMapping("/del")
	@ApiOperation(value="删除节点", response = Response.class)
	public Response nodeDel(Integer nodeId) {
		
		nodeService.delete(nodeId);
		
		return new Response().success();
	}
	
	@RequestMapping("/update")
	@ApiOperation(value="更新节点", response = Response.class)
	public Response nodeUpdate(@RequestBody Node node) {
		
		nodeService.update(node);
		
		return new Response().success();
	}
	
	@RequestMapping("/one")
	@ApiOperation(value="查询单个节点信息", response = Response.class)
	public Response nodeOne(Integer nodeId) {
		
		nodeService.getOne(nodeId);
		
		return new Response().success();
	}
	
	@RequestMapping("/list")
	@ApiOperation(value="查询节点列表", response = Response.class)
	public Response nodeList(@RequestParam(defaultValue="0")Integer pageNum, @RequestParam(defaultValue="0")Integer pageSize) {
		
		PageInfo<Node> pi = nodeService.getAll(pageNum, pageSize);
		
		return new Response().success(pi);
	}
	
}
