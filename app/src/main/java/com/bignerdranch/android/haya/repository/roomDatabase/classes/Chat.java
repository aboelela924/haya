package com.bignerdranch.android.haya.repository.roomDatabase.classes;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "Chats")
public class Chat {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "burnercode")
         String burnercode;
    @ColumnInfo(name = "conversation_nickname")
         String conversation_nickname;
    @ColumnInfo(name = "secret_status")
         boolean is_secret_chat;
    @ColumnInfo(name = "admin_subscriber_id")
         String admin_subscriber_id;
    @ColumnInfo(name = "last_message_Date")
    Date last_message_date;

    public Chat(String burnercode, String conversation_nickname, String admin_subscriber_id, Date last_message_date) {
        this.burnercode = burnercode;
        this.conversation_nickname = conversation_nickname;
        this.admin_subscriber_id = admin_subscriber_id;
        this.last_message_date = last_message_date;

        this.is_secret_chat = false;
    }

    public void setBurnercode(String burnercode) {
        this.burnercode = burnercode;
    }
    public void setConversation_nickname(String conversation_nickname) {
        this.conversation_nickname = conversation_nickname;
    }
    public void setIs_secret_chat(boolean is_secret_chat) {
        this.is_secret_chat = is_secret_chat;
    }
    public void setAdmin_subscriber_id(String admin_subscriber_id) {
        this.admin_subscriber_id = admin_subscriber_id;
    }
    public void setLast_message_date(Date last_message_date) {
        this.last_message_date = last_message_date;
    }

    public String getBurnercode() {
        return burnercode;
    }
    public String getConversation_nickname() {
        return conversation_nickname;
    }
    public boolean isIs_secret_chat() {
        return is_secret_chat;
    }
    public String getAdmin_subscriber_id() {
        return admin_subscriber_id;
    }
    public Date getLast_message_date() {
        return last_message_date;
    }
}
