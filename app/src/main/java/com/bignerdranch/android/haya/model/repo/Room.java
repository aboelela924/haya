package com.bignerdranch.android.haya.model.repo;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Room implements Parcelable, Comparable<Room> {
    //
    private String updated_at;

    private Subscriber[] subscribers;

    private String __v;
    //
    private String name;
    //
    private String created_at;
    //
    private String _id;
    //
    private String id;
    //
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

    public Subscriber[] getSubscribers ()
    {
        return subscribers;
    }

    public void setSubscribers (Subscriber[] subscribers)
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.updated_at);
        dest.writeTypedArray(this.subscribers, flags);
        dest.writeString(this.__v);
        dest.writeString(this.name);
        dest.writeString(this.created_at);
        dest.writeString(this._id);
        dest.writeString(this.id);
        dest.writeString(this.type);
        dest.writeString(this.status);
        dest.writeString(this.token);
    }

    public Room() {
    }

    protected Room(Parcel in) {
        this.updated_at = in.readString();
        this.subscribers = in.createTypedArray(Subscriber.CREATOR);
        this.__v = in.readString();
        this.name = in.readString();
        this.created_at = in.readString();
        this._id = in.readString();
        this.id = in.readString();
        this.type = in.readString();
        this.status = in.readString();
        this.token = in.readString();
    }

    public static final Parcelable.Creator<Room> CREATOR = new Parcelable.Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel source) {
            return new Room(source);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };

    @Override
    public int compareTo(Room o) {
        return this.updated_at.compareTo(o.updated_at);
    }
}
