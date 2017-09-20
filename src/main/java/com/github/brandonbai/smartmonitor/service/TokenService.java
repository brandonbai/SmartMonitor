package com.github.brandonbai.smartmonitor.service;

import com.github.brandonbai.smartmonitor.pojo.User;

public interface TokenService {

    String createToken(User user);

    boolean checkToken();
    
    void deleteToken();

    User getUser();

    User getUser(String token);

    String getUsername();
    
}