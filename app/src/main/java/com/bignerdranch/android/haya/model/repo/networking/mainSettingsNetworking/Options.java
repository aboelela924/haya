package com.bignerdranch.android.haya.model.repo.networking.mainSettingsNetworking;

public class Options
{
    private String enable_secret_messages;

    private String updated_at;

    private String created_at;

    private String _id;

    private String id;

    public String getEnable_secret_messages ()
    {
        return enable_secret_messages;
    }

    public void setEnable_secret_messages (String enable_secret_messages)
    {
        this.enable_secret_messages = enable_secret_messages;
    }

    public String getUpdated_at ()
    {
        return updated_at;
    }

    public void setUpdated_at (String updated_at)
    {
        this.updated_at = updated_at;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public void setCreated_at (String created_at)
    {
        this.created_at = created_at;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [enable_secret_messages = "+enable_secret_messages+", updated_at = "+updated_at+", created_at = "+created_at+", _id = "+_id+", id = "+id+"]";
    }
}