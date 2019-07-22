package com.bignerdranch.android.haya.model.repo;

public class Subscriber {
    private String is_admin;

    private String room_id;

    private String updated_at;

    private String user_id;

    private String is_secret;

    private String is_leave;

    private String custom_room_name;

    private String __v;

    private String name;

    private String created_at;

    private String _id;

    private String id;

    public String getIs_admin ()
    {
        return is_admin;
    }

    public void setIs_admin (String is_admin)
    {
        this.is_admin = is_admin;
    }

    public String getRoom_id ()
    {
        return room_id;
    }

    public void setRoom_id (String room_id)
    {
        this.room_id = room_id;
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

    public String getIs_secret ()
    {
        return is_secret;
    }

    public void setIs_secret (String is_secret)
    {
        this.is_secret = is_secret;
    }

    public String getIs_leave ()
    {
        return is_leave;
    }

    public void setIs_leave (String is_leave)
    {
        this.is_leave = is_leave;
    }

    public String getCustom_room_name ()
    {
        return custom_room_name;
    }

    public void setCustom_room_name (String custom_room_name)
    {
        this.custom_room_name = custom_room_name;
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

    @Override
    public String toString()
    {
        return "ClassPojo [is_admin = "+is_admin+", room_id = "+room_id+", updated_at = "+updated_at+", user_id = "+user_id+", is_secret = "+is_secret+", is_leave = "+is_leave+", custom_room_name = "+custom_room_name+", __v = "+__v+", name = "+name+", created_at = "+created_at+", _id = "+_id+", id = "+id+"]";
    }
}
