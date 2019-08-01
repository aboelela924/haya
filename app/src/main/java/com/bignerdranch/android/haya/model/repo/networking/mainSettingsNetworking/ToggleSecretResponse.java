package com.bignerdranch.android.haya.model.repo.networking.mainSettingsNetworking;

public class ToggleSecretResponse {
    private User user;

    public User getUser ()
    {
        return user;
    }

    public void setUser (User user)
    {
        this.user = user;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [user = "+user+"]";
    }
}


