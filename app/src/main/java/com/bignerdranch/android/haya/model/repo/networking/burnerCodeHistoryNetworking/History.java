package com.bignerdranch.android.haya.model.repo.networking.burnerCodeHistoryNetworking;

public class History {
    private String updated_at;

    private String[] subscribers;

    private String __v;

    private String name;

    private String created_at;

    private String _id;

    private String id;

    private String type;

    private String status;

    private String token;

    public String getUpdated_at ()
    {
        return updated_at;
    }

    public void setUpdated_at (String updated_at)
    {
        this.updated_at = updated_at;
    }

    public String[] getSubscribers ()
    {
        return subscribers;
    }

    public void setSubscribers (String[] subscribers)
    {
        this.subscribers = subscribers;
    }

    public String get__v ()
    {
        return __v;
    }

    public void set__v (String __v)
    {
        this.__v = __v;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
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

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getToken ()
    {
        return token;
    }

    public void setToken (String token)
    {
        this.token = token;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [updated_at = "+updated_at+", subscribers = "+subscribers+", __v = "+__v+", name = "+name+", created_at = "+created_at+", _id = "+_id+", id = "+id+", type = "+type+", status = "+status+", token = "+token+"]";
    }
}
