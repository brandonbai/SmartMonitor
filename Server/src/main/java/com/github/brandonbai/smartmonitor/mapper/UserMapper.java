package com.github.brandonbai.smartmonitor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.brandonbai.smartmonitor.exception.MsgException;
import com.github.brandonbai.smartmonitor.pojo.User;
import tk.mybatis.mapper.common.Mapper;

/**
 * UserMapper
 *
 * @author brandonbai
 * @since 2016年10月17日
 */
public interface UserMapper extends Mapper<User> {

    /**
     * 通过用户名和密码查找用户
     *
     * @param username 用户名
     * @param password 密码
     */
    User findUser(@Param("username") String username, @Param("password") String password);

    /**
     * 修改用户信息
     *
     * @param user 用户
     */
    void updateUser(User user) throws MsgException;

    /**
     * 通过用户名查找用户
     *
     * @param username 用户名
     */
    User findUserByUsername(String username);

    void changePassword(User user);

    List<User> findAll(Integer pageNum, Integer pageSize);

}
