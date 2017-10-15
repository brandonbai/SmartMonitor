package com.github.brandonbai.smartmonitor.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.brandonbai.smartmonitor.exception.MsgException;
import com.github.brandonbai.smartmonitor.mapper.UserMapper;
import com.github.brandonbai.smartmonitor.pojo.User;
import com.github.brandonbai.smartmonitor.service.UserService;
import com.github.brandonbai.smartmonitor.utils.MD5;
import com.github.pagehelper.PageInfo;

@Service
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserMapper userMapper;
	
	@Override
	public User findUser(String username, String password) throws MsgException {
		User user = userMapper.findUserByUsername(username);
		if(user == null || !user.getPassword().equals(MD5.getMd5Hash(password))) {
			throw new MsgException("用户名或密码错误");
		}
		return user;
	}
	
	@Override
	public PageInfo<User> findAll(Integer pageNum, Integer pageSize) {
		
		List<User> list = userMapper.findAll(pageNum, pageSize);
		
		return new PageInfo<User>(list);
	}
	
	@Override
	public void updateUser(User user) throws MsgException {
		User u = userMapper.findUserByUsername(user.getUsername());
		if(u == null || !u.getPassword().equals(MD5.getMd5Hash(user.getPassword()))) {
			throw new MsgException("密码错误");
		}
		userMapper.update(user);
	}

	@Override
	public User findUserByUsername(String username) {
		return userMapper.findUserByUsername(username);
	}

	@Override
	public void changePassword(String username, String password, String newPassword) throws MsgException {
		User user = userMapper.findUserByUsername(username);
		if(user == null || !user.getPassword().equals(MD5.getMd5Hash(password))) {
			throw new MsgException("密码错误");
		}
		User userTmp = new User();
		userTmp.setUsername(username);
		userTmp.setPassword(MD5.getMd5Hash(password));
		userTmp.setNewPassword(MD5.getMd5Hash(newPassword));
		userMapper.changePassword(userTmp);
	}

}
