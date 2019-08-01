package com.bignerdranch.android.haya.model.repo.networking.chatNetworking;

import com.bignerdranch.android.haya.model.repo.Message;
import com.bignerdranch.android.haya.model.repo.Subscriber;

public class SyncMessage {

    private String room_id;

    private String isDeleted;

    private String updated_at;

    private String __v;

    private String created_at;

    private String _id;

    private String id;

    private String type;

    private String message;

    private String user;

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

    public String getUser ()
    {
        return user;
    }

    public void setUser (String user)
    {
        this.user = user;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [room_id = "+room_id+", isDeleted = "+isDeleted+", updated_at = "+updated_at+", __v = "+__v+", created_at = "+created_at+", _id = "+_id+", id = "+id+", type = "+type+", message = "+message+", user = "+user+"]";
    }

    public Message toMessage(Subscriber[] subscribers){
        Message message = new Message();
        message.setMessage(this.message);
        message.setUpdated_at(this.updated_at);
        message.setCreated_at(this.created_at);
        message.setType(this.type);
        message.setIsDeleted(this.isDeleted);
        message.setRoom_id(this.room_id);
        message.set_id(this._id);
        message.set__v(this.__v);
        message.setId(this.id);
        for (Subscriber subscriber: subscribers){
            if(subscriber.getId().equals(this.user)){
                message.setUser(subscriber);
                break;
            }
        }
        return message;
    }

    public static SyncMessage fromMessage(Message message){
        SyncMessage syncMessage = new SyncMessage();
        syncMessage.setUser(message.getUser().getId());
        syncMessage.set__v(message.get__v());
        syncMessage.set_id(message.get__v());
        syncMessage.setCreated_at(message.getCreated_at());
        syncMessage.setId(message.getId());
        syncMessage.setIsDeleted(message.getIsDeleted());
        syncMessage.setRoom_id(message.getRoom_id());
        syncMessage.setType(message.getType());
        syncMessage.setUpdated_at(message.getUpdated_at());
        syncMessage.setMessage(message.getMessage());
        return syncMessage;
    }

}
