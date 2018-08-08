package com.github.brandonbai.smartmonitor.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.brandonbai.smartmonitor.exception.MsgException;
import com.github.brandonbai.smartmonitor.pojo.Response;
import com.github.brandonbai.smartmonitor.pojo.User;
import com.github.brandonbai.smartmonitor.service.TokenService;
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
public class UserController {

	@Resource
	private UserService userService;

	@Autowired
	private TokenService tokenService;

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public Response login(HttpSession session, String username, String password) throws MsgException {
		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
			// 输入的数据为空
			throw new MsgException("用户名或密码不能为空");
		}
		User user = userService.findUser(username, password);
		session.setAttribute("_user", user);
		String token = tokenService.createToken(user);
		
		return new Response().success(token);
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public Response updateUser(User user) throws MsgException {
		
		user.checkUpdate();
		userService.updateUser(user);
		
		return new Response().success();
	}

	@RequestMapping(value = "changepwd", method = RequestMethod.POST)
	public Response changePassword(String username, String password, String newPassword) throws MsgException {

		userService.changePassword(username, password, newPassword);

		return new Response().success();
	}

	@RequestMapping(value = "userInfo", method = RequestMethod.GET)
	public Response userInfo() {

		User user = tokenService.getUser();

		return new Response().success(user);
	}

}
