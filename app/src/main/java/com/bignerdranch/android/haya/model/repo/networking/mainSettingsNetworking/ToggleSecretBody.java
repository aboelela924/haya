package com.bignerdranch.android.haya.model.repo.networking.mainSettingsNetworking;

public class ToggleSecretBody {
    private String current_password;

    public ToggleSecretBody(String current_password) {
        this.current_password = current_password;
    }

    public String getCurrent_password() {
        return current_password;
    }

    public void setCurrent_password(String current_password) {
        this.current_password = current_password;
    }
}
