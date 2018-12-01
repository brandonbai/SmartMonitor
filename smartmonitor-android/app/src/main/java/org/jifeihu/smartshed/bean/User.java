package org.jifeihu.smartshed.bean;

/**
 * Created by Administrator on 2017/1/23.
 */
public class User {

    private String username;
    private String password;
    private String newPassword;
    private int power;
    private String tel;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "User [username=" + username + ", password=" + password
                + ", newPassword=" + newPassword + ", power=" + power
                + ", tel=" + tel + "]";
    }
}