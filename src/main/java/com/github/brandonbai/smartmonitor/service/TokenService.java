package com.github.brandonbai.smartmonitor.service;

import com.github.brandonbai.smartmonitor.pojo.User;

/**
 * 
 * TokenService 
 * @author Feihu Ji
 * @sine 2016年10月19日
 *
 */
public interface TokenService {

    String createToken(User user);

    boolean checkToken();
    
    void deleteToken();

    User getUser();

    User getUser(String token);

    String getUsername();
    
}