package com.github.brandonbai.smartmonitor.service.impl;

import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.github.brandonbai.smartmonitor.pojo.User;
import com.github.brandonbai.smartmonitor.service.TokenService;
import com.github.brandonbai.smartmonitor.utils.MD5;
import com.github.brandonbai.smartmonitor.utils.TextUtils;

/**
 * 
 * TokenServiceImpl 
 * @author Feihu Ji
 * @sine 2016年10月19日
 *
 */
@Component
public class TokenServiceImpl implements TokenService {

	/**
	 * TOKEN标记
	 */
	private static final String TOKEN_NAME = "token";
	/** 保存token-username的线程安全map*/
	private static Map<String, User> tokenMap = new ConcurrentHashMap<>();

	@Override
	public String createToken(User user) {
		String token = MD5.getMd5Hash(user.getUsername()+Calendar.getInstance().getTime());
		user.setPassword("");
		tokenMap.put(token, user);
		return token;
	}

	@Override
	public boolean checkToken() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		Object user = request.getSession().getAttribute("_user");
		return (!TextUtils.isEmpty(getToken()) && tokenMap.containsKey(getToken()))||user!=null;
	}

	@Override
	public void deleteToken() {
		tokenMap.remove(getToken());
	}

	@Override
	public User getUser() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		Object user = request.getSession().getAttribute("_user");
		return tokenMap.get(getToken()) == null ? (User)user : tokenMap.get(getToken());
	}

	@Override
	public String getUsername() {
		return getUser().getUsername();
	}

	private String getToken() {
		// 当前请求
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request.getHeader(TOKEN_NAME);
	}

	@Override
	public User getUser(String token) {
		if(TextUtils.isEmpty(token) || !tokenMap.containsKey(token)) {
			return null;
		}
		// 当前请求
		return tokenMap.get(token);
	}

}
