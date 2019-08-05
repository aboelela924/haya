package com.bignerdranch.android.haya.model.repo.networking.mainSettingsNetworking;

public class User
{
    private String access_token;

    private String password;

    private String device_id;

    private String os;

    private String updated_at;

    private String user_id;

    private String __v;

    private Options options;

    private String created_at;

    private String _id;

    private String id;

    public String getAccess_token ()
    {
        return access_token;
    }

    public void setAccess_token (String access_token)
    {
        this.access_token = access_token;
    }

    public String getPassword ()
    {
        return password;
    }

    public void setPassword (String password)
    {
        this.password = password;
    }

    public String getDevice_id ()
    {
        return device_id;
    }

    public void setDevice_id (String device_id)
    {
        this.device_id = device_id;
    }

    public String getOs ()
    {
        return os;
    }

    public void setOs (String os)
    {
        this.os = os;
    }

    public String getUpdated_at ()
    {
        return updated_at;
    }

    public void setUpdated_at (String updated_at)
    {
        this.updated_at = updated_at;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String get__v ()
    {
        return __v;
    }

    public void set__v (String __v)
    {
        this.__v = __v;
    }

    public Options getOptions ()
    {
        return options;
    }

    public void setOptions (Options options)
    {
        this.options = options;
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
        return "ClassPojo [access_token = "+access_token+", password = "+password+", device_id = "+device_id+", os = "+os+", updated_at = "+updated_at+", user_id = "+user_id+", __v = "+__v+", options = "+options+", created_at = "+created_at+", _id = "+_id+", id = "+id+"]";
    }
}



