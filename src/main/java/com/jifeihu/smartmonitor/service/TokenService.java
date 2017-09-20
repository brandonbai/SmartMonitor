package com.jifeihu.smartmonitor.service;

import com.jifeihu.smartmonitor.pojo.User;

public interface TokenService {

    String createToken(User user);

    boolean checkToken();
    
    void deleteToken();

    User getUser();

    User getUser(String token);

    String getUsername();
    
}