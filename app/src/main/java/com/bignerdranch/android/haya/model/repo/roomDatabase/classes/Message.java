package com.bignerdranch.android.haya.model.repo.roomDatabase.classes;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "Messages",foreignKeys = {
                                                @ForeignKey(entity = Chat.class,
                                                        parentColumns = "burnercode",
                                                        childColumns = "chat_burnercode",
                                                        onDelete = CASCADE),
                                                @ForeignKey(entity = Subscriber.class,
                                                        parentColumns = "subscriber_id",
                                                        childColumns = "subscriber_id")
                                            })
public class Message {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
        String id;
    @ColumnInfo(name = "date")
        Date date;
    @ColumnInfo(name = "text")
        String text;
    @ColumnInfo(name = "sent_status")
         boolean is_sent;
    @ColumnInfo(name = "chat_burnercode")
         String chat_burnercode;
    @ColumnInfo(name = "subscriber_id")
         String subscriber_id;

    public Message(@NonNull String id, Date date, String text, String chat_burnercode, String subscriber_id) {
        this.id = id;
        this.date = date;
        this.text = text;
        this.chat_burnercode = chat_burnercode;
        this.subscriber_id = subscriber_id;

        is_sent = false;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setIs_sent(boolean is_sent) {
        this.is_sent = is_sent;
    }
    public void setChat_burnercode(String chat_burnercode) {
        this.chat_burnercode = chat_burnercode;
    }
    public void setSubscriber_id(String subscriber_id) {
        this.subscriber_id = subscriber_id;
    }

    public String getId() {
        return id;
    }
    public Date getDate() {
        return date;
    }
    public String getText() {
        return text;
    }
    public boolean isIs_sent() {
        return is_sent;
    }
    public String getChat_burnercode() {
        return chat_burnercode;
    }
    public String getSubscriber_id() {
        return subscriber_id;
    }
}
