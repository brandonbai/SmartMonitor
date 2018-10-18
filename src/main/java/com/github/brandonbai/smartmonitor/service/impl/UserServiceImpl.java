package com.github.brandonbai.smartmonitor.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.github.brandonbai.smartmonitor.dto.UserDTO;
import com.github.brandonbai.smartmonitor.utils.TokenUtil;
import org.springframework.stereotype.Service;

import com.github.brandonbai.smartmonitor.exception.MsgException;
import com.github.brandonbai.smartmonitor.mapper.UserMapper;
import com.github.brandonbai.smartmonitor.pojo.User;
import com.github.brandonbai.smartmonitor.service.UserService;
import com.github.brandonbai.smartmonitor.utils.MD5;
import com.github.pagehelper.PageInfo;

/**
 * 
 * UserServiceImpl 
 * @author brandonbai
 * @since 2016年10月19日
 *
 */
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
		userMapper.update(user);
	}

	@Override
	public User findUserByUsername(String username) {
		return userMapper.findUserByUsername(username);
	}

	@Override
	public void changePassword(UserDTO userDTO) throws MsgException {
		User user = userMapper.findUserByUsername(TokenUtil.getUsername());
		if(user == null || !user.getPassword().equals(MD5.getMd5Hash(userDTO.getPassword()))) {
			throw new MsgException("密码错误");
		}
		User userTmp = new User();
		userTmp.setId(user.getId());
		userTmp.setPassword(MD5.getMd5Hash(userDTO.getNewPassword()));
		userMapper.changePassword(userTmp);
	}

}
