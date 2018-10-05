package com.github.brandonbai.smartmonitor.service;

import com.github.brandonbai.smartmonitor.dto.UserDTO;
import com.github.brandonbai.smartmonitor.exception.MsgException;
import com.github.brandonbai.smartmonitor.pojo.User;
import com.github.pagehelper.PageInfo;

/**
 * 
 * UserService 
 * @author Feihu Ji
 * @since 2016年10月19日
 *
 */
public interface UserService {
	/**
	 * find user
	 * @param username
	 * @param password
	 * @return user
	 */
	User findUser(String username, String password);
	
	/**
	 * update user basic info
	 * @param user
	 */
	void updateUser(User user);

	PageInfo<User> findAll(Integer pageNum, Integer pageSize);
	
	User findUserByUsername(String username);

	void changePassword(UserDTO user);
}
