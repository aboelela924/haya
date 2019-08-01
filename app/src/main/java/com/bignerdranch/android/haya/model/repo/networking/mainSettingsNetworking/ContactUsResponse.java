package com.bignerdranch.android.haya.model.repo.networking.mainSettingsNetworking;

public class ContactUsResponse {
    private ContactUs contact_us;

    private String status;

    public ContactUs getContact_us ()
    {
        return contact_us;
    }

    public void setContact_us (ContactUs contact_us)
    {
        this.contact_us = contact_us;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [contact_us = "+contact_us+", status = "+status+"]";
    }
}
