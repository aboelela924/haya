package com.bignerdranch.android.haya.model.repo;

import androidx.annotation.Nullable;

public class Message {
    private String room_id;

    private String isDeleted;

    private String updated_at;

    private String __v;

    private String created_at;

    private String _id;

    private String id;

    private String type;

    private String message;

    private Subscriber user;

    public String getRoom_id ()
    {
        return room_id;
    }

    public void setRoom_id (String room_id)
    {
        this.room_id = room_id;
    }

    public String getIsDeleted ()
    {
        return isDeleted;
    }

    public void setIsDeleted (String isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    public String getUpdated_at ()
    {
        return updated_at;
    }

    public void setUpdated_at (String updated_at)
    {
        this.updated_at = updated_at;
    }

    public String get__v ()
    {
        return __v;
    }

    public void set__v (String __v)
    {
        this.__v = __v;
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

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public Subscriber getUser ()
    {
        return user;
    }

    public void setUser (Subscriber user)
    {
        this.user = user;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [room_id = "+room_id+", isDeleted = "+isDeleted+", updated_at = "+updated_at+", __v = "+__v+", created_at = "+created_at+", _id = "+_id+", id = "+id+", type = "+type+", message = "+message+", user = "+user+"]";
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Message message = (Message) obj;
        return message.getId().equals(id);
    }
}