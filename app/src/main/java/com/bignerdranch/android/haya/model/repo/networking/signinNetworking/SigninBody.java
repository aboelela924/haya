package com.bignerdranch.android.haya.model.repo.networking.signinNetworking;

public class SigninBody {
    String user_id;
    String password;
    String device_id;
    String os = "android";

    public SigninBody(String user_id, String password, String device_id) {
        this.user_id = user_id;
        this.password = password;
        this.device_id = device_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }
}
