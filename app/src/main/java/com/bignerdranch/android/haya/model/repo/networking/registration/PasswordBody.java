package com.bignerdranch.android.haya.model.repo.networking.registration;

public class PasswordBody {
    String current_password;
    String password;

    public PasswordBody(String current_password, String password) {
        this.current_password = current_password;
        this.password = password;
    }

    public String getCurrent_password() {
        return current_password;
    }

    public void setCurrent_password(String current_password) {
        this.current_password = current_password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
