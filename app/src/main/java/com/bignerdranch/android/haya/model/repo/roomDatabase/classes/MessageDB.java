package com.bignerdranch.android.haya.model.repo.roomDatabase.classes;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.bignerdranch.android.haya.model.repo.Message;
import com.bignerdranch.android.haya.model.repo.Subscriber;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "message",foreignKeys = {
                                                @ForeignKey(entity = ChatDB.class,
                                                        parentColumns = "id",
                                                        childColumns = "chat_id",
                                                        onDelete = CASCADE),
                                                @ForeignKey(entity = SubscriberDB.class,
                                                        parentColumns = "id",
                                                        childColumns = "subscriber_id")
                                            })
public class MessageDB {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    public    String id;
    @ColumnInfo(name = "message")
    public   String message;
    @ColumnInfo(name = "updated_at")
    public  String updatedAt;
    @ColumnInfo(name = "created_at")
    public  String createdAt;
    @ColumnInfo(name = "type")
    public  String type;
    @ColumnInfo(name = "is_deleted")
    public   boolean isDeleted;
    @ColumnInfo(name = "chat_id")
    public   String chatId;
    @ColumnInfo(name = "subscriber_id")
    public String subScriberId;

    public MessageDB(){
        this.message = "";
    }

    public static MessageDB fromMessage(Message message){
        MessageDB msgDB = new MessageDB();
        msgDB.id = message.getId();
        msgDB.message = message.getMessage();
        msgDB.updatedAt = message.getUpdated_at();
        msgDB.createdAt = message.getCreated_at();
        msgDB.type = message.getType();
        msgDB.isDeleted = Boolean.valueOf(message.getIsDeleted());
        msgDB.chatId = message.getRoom_id();
        msgDB.subScriberId = message.getUser().getId();
        return msgDB;
    }

    public Message toMessage(Subscriber subscriber){
        Message message = new Message();
        message.setId(this.id);
        message.setMessage(this.message);
        message.setUpdated_at(this.updatedAt);
        message.setCreated_at(this.createdAt);
        message.setType(this.type);
        message.setIsDeleted(String.valueOf(this.isDeleted));
        message.setRoom_id(this.chatId);
        message.setUser(subscriber);
        return  message;
    }

    public static MessageDB[] fromMessagerray(Message[] messages){
        MessageDB[] messageDBS = new MessageDB[messages.length];
        for (int i = 0; i< messages.length; i++){
            messageDBS[i] = fromMessage(messages[i]);
        }
        return messageDBS;
    }

    public String get_message() {
        return this.message;
    }

    public static Message toMessage(MessageDB messageDB){
        Message message = new Message();
        message.setId(messageDB.id);
        message.setMessage(messageDB.message);
        message.setUpdated_at(messageDB.updatedAt);
        message.setCreated_at(messageDB.createdAt);
        message.setType(messageDB.type);
        message.setIsDeleted(String.valueOf(messageDB.isDeleted));
        message.setRoom_id(messageDB.chatId);

        return message;
    }

}
