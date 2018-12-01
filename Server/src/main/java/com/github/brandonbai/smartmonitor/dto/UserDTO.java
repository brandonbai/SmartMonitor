package com.github.brandonbai.smartmonitor.dto;

import com.github.brandonbai.smartmonitor.exception.MsgException;
import com.github.brandonbai.smartmonitor.pojo.User;
import com.github.brandonbai.smartmonitor.utils.TextUtils;
import org.apache.commons.lang3.StringUtils;

public class UserDTO extends User {

    private String newPassword;


    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * 修改检查
     * @throws MsgException 自定义异常
     */
    public void checkUpdate() throws MsgException {

        if(StringUtils.isEmpty(getUsername())) {
            throw new MsgException("用户名不能为空");
        }

//        if(TextUtils.isEmpty(getPassword())) {
//            throw new MsgException("密码不能为空");
//        }
//
//        if(TextUtils.isEmpty(newPassword)) {
//            throw new MsgException("新密码不能为空");
//        }

        if(StringUtils.isEmpty(getName())) {
            throw new MsgException("姓名不能为空");
        }

//        if(!newPassword.matches("\\w{6,15}")) {
//            throw new MsgException("密码位数为6~15");
//        }

        if(StringUtils.isEmpty(getTel())) {
            throw new MsgException("电话不能为空");
        }

        if(!getTel().matches("^\\d{11}$")) {
            throw new MsgException("手机号码格式不正确");
        }

    }
}
