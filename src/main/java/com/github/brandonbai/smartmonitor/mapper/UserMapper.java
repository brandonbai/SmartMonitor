package com.github.brandonbai.smartmonitor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.brandonbai.smartmonitor.exception.MsgException;
import com.github.brandonbai.smartmonitor.pojo.User;

/**
 * 
 * UserMapper 
 * @author Feihu Ji
 * @sine 2016年10月17日
 *
 */
public interface UserMapper {
	
	/**
	 * 通过用户名和密码查找用户
	 * @param username
	 * @param password
	 */
	public User findUser(@Param("username") String username, @Param("password") String password);
	
	/**
	 * 修改用户信息
	 * @param user
	 */
	public void update(User user) throws MsgException;
	
	/**
	 * 通过用户名查找用户
	 * @param username
	 */
	public User findUserByUsername(String username);

	void changePassword(User user);

	public List<User> findAll(Integer pageNum, Integer pageSize);
}
