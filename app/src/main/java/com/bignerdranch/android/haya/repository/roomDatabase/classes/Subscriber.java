package com.bignerdranch.android.haya.repository.roomDatabase.classes;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "Subscribers", foreignKeys = @ForeignKey(entity = Chat.class,
                                                            parentColumns = "burnercode",
                                                            childColumns = "chat_burnercode",
                                                            onDelete = CASCADE))
public class Subscriber {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "subscriber_id")
        String subscriber_id;
    @ColumnInfo(name = "user_id")
        String user_id;
    @ColumnInfo(name = "nickname")
        String nickname;
    @ColumnInfo(name = "chat_burnercode")
        String chat_burnercode;

    public Subscriber(String subscriber_id, String user_id, String nickname, String chat_burnercode) {
        this.subscriber_id = subscriber_id;
        this.user_id = user_id;
        this.nickname = nickname;
        this.chat_burnercode = chat_burnercode;
    }

    public void setSubscriber_id(String subscriber_id) {
        this.subscriber_id = subscriber_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public void setChat_burnercode(String chat_burnercode) {
        this.chat_burnercode = chat_burnercode;
    }

    public String getSubscriber_id() {
        return subscriber_id;
    }
    public String getUser_id() {
        return user_id;
    }
    public String getNickname() {
        return nickname;
    }
    public String getChat_burnercode() {
        return chat_burnercode;
    }
}