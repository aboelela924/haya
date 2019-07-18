package com.bignerdranch.android.haya.model.repo.networking.registration;

public class RegistrationBody {
    String device_id;
    String os;

    public RegistrationBody(String device_id, String os) {
        this.device_id = device_id;
        this.os = os;
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
