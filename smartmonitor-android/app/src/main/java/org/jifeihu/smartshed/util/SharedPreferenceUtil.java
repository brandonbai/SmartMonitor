package org.jifeihu.smartshed.util;

import android.content.Context;
import android.content.SharedPreferences;

import org.jifeihu.smartshed.interfaces.IConstants;

/**
 * Created by Administrator on 2016/11/21.
 * 用于数据存储的工具类
 */

public class SharedPreferenceUtil implements IConstants {
    private Context mContext;
    private static SharedPreferenceUtil instance;

    private SharedPreferenceUtil(Context context) {
        mContext = context;

    }

    public static SharedPreferenceUtil getInstance(Context context) {
        if(instance == null) {
            synchronized (SharedPreferenceUtil.class) {
                if(instance == null) {
                    instance = new SharedPreferenceUtil(context);
                }
            }
        }
        return instance;
    }

    /**
     * 获取用户信息
     * @return user
     */
    public String getUserInfo() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                SHARED_PREFERENCE_USER, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_INFO, "");
    }

    /**
     * 保存用户信息
     * @param username
     */
    public void setUserInfo(String username) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                SHARED_PREFERENCE_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_INFO, username);
        editor.commit();
    }

    /**
     * 获取网络信息
     * @return ip地址和端口号字符串数组
     */
    public String[] getNetworkInfo() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                SHARED_PREFERENCE_NETWORK, Context.MODE_PRIVATE);
        String ip = sharedPreferences.getString(KEY_IP, "0.0.0.0");
        String port = sharedPreferences.getString(KEY_PORT, "8080");

        return new String[]{ip, port};
    }

    /**
     * 保存网络设置
     * @param ip ip地址
     * @param port 端口号
     */
    public void setNetworkInfo(String ip, String port) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                SHARED_PREFERENCE_NETWORK, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_IP, ip);
        editor.putString(KEY_PORT, port);
        editor.commit();
    }

    /**
     * 获取服务器首页
     * @return
     */
    public String getServerHome() {
        return "http://"+getNetworkInfo()[0]+":"+getNetworkInfo()[1]+ "/";
    }

    public void setToken(String token) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                SHARED_PREFERENCE_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }

    public String getToken() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                SHARED_PREFERENCE_USER, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_TOKEN, "");
    }

    public void setNotificationState(boolean state) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                SHARED_PREFERENCE_NOTIFICATION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_STATE, state);
        editor.commit();
    }

    public Boolean getNotificationState() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                SHARED_PREFERENCE_NOTIFICATION, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_STATE, false);
    }

    public SharedPreferences getNotification() {
        return mContext.getSharedPreferences(
                SHARED_PREFERENCE_NOTIFICATION, Context.MODE_PRIVATE);
    }

}
