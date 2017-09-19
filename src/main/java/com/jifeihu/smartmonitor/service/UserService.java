package com.jifeihu.smartmonitor.service;

import com.jifeihu.smartmonitor.exception.MsgException;
import com.jifeihu.smartmonitor.pojo.User;

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

	public User findUserByUsername(String username);

	public void changePassword(String username, String password, String newPassword) throws MsgException;
}
