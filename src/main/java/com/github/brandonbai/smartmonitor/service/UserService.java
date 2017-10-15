package com.github.brandonbai.smartmonitor.service;

import com.github.brandonbai.smartmonitor.exception.MsgException;
import com.github.brandonbai.smartmonitor.pojo.User;
import com.github.pagehelper.PageInfo;

/**
 * 
 * UserService 
 * @author Feihu Ji
 * @sine 2016年10月19日
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
	public User findUser(String username, String password) throws MsgException;
	
	/**
	 * �޸��û���Ϣ
	 * @param user
	 * @throws MsgException
	 */
	public void updateUser(User user) throws MsgException;

	public PageInfo<User> findAll(Integer pageNum, Integer pageSize);
	
	public User findUserByUsername(String username);

	public void changePassword(String username, String password, String newPassword) throws MsgException;
}
