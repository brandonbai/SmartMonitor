package com.github.brandonbai.smartmonitor.service;

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
	 * �����û�
	 * @param username
	 * @param password
	 * @return user
	 * @throws MsgException
	 */
	User findUser(String username, String password);
	
	/**
	 * �޸��û���Ϣ
	 * @param user
	 * @throws MsgException
	 */
	void updateUser(User user) throws MsgException;

	PageInfo<User> findAll(Integer pageNum, Integer pageSize);
	
	User findUserByUsername(String username);

	void changePassword(String username, String password, String newPassword);
}
