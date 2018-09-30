package com.github.brandonbai.smartmonitor.controller;

import javax.annotation.Resource;

import com.github.brandonbai.smartmonitor.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.brandonbai.smartmonitor.exception.MsgException;
import com.github.brandonbai.smartmonitor.pojo.Response;
import com.github.brandonbai.smartmonitor.pojo.User;
import com.github.brandonbai.smartmonitor.service.UserService;
import com.github.brandonbai.smartmonitor.utils.TextUtils;

/**
 * 
 * UserController 
 * 用户相关
 * @author Feihu Ji
 * @since 2016年10月15日
 *
 */
@RestController
@RequestMapping("/user/")
@Api(tags="用户管理")
public class UserController {

	@Resource
	private UserService userService;

	@RequestMapping(value = "login", method = RequestMethod.POST)
	@ApiOperation(value="用户登录", response = Response.class)
	public Response login(String username, String password) throws MsgException {
		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
			// 输入的数据为空
			throw new MsgException("用户名或密码不能为空");
		}
		User user = userService.findUser(username, password);
		String token = TokenUtil.createToken(user);
		
		return new Response().success(token);
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ApiOperation(value="更新用户信息", response = Response.class)
	public Response updateUser(User user) throws MsgException {
		
		user.checkUpdate();
		userService.updateUser(user);
		
		return new Response().success();
	}

	@RequestMapping(value = "changepwd", method = RequestMethod.POST)
	@ApiOperation(value="修改密码", response = Response.class)
	public Response changePassword(String username, String password, String newPassword) throws MsgException {

		userService.changePassword(username, password, newPassword);

		return new Response().success();
	}

	@RequestMapping(value = "userInfo", method = RequestMethod.GET)
	@ApiOperation(value="查询单条用户信息", response = Response.class)
	public Response userInfo() {

		User user = TokenUtil.getUser();

		return new Response().success(user);
	}

}
