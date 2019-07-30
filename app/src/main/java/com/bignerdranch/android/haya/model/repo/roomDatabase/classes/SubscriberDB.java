package com.bignerdranch.android.haya.model.repo.roomDatabase.classes;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.bignerdranch.android.haya.model.repo.Subscriber;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "subscriber", foreignKeys = @ForeignKey(entity = ChatDB.class,
                                                            parentColumns = "id",
                                                            childColumns = "chat_id",
                                                            onDelete = CASCADE))
public class SubscriberDB {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
        public String id;
    @ColumnInfo(name = "name")
        public String name;
    @ColumnInfo(name = "created_at")
        public String createdAt;
    @ColumnInfo(name = "updated_at")
        public  String updatedAt;
    @ColumnInfo(name = "is_admin")
    public boolean isAdmin;
    @ColumnInfo(name = "is_leave")
    public boolean isLeave;
    @ColumnInfo(name = "is_secret")
    public boolean isSecret;
    @ColumnInfo(name = "user_id")
    public String userId;
    @ColumnInfo(name = "custom_room_name")
    public String customRoomName;
    @ColumnInfo(name = "chat_id")
    public String chatId;


    public static SubscriberDB fromSubscriber(Subscriber subscriber, String chatId){
        SubscriberDB sb = new SubscriberDB();
        sb.id = subscriber.getId();
        sb.name = subscriber.getName();
        sb.createdAt = subscriber.getCreated_at();
        sb.updatedAt = subscriber.getUpdated_at();
        sb.isAdmin = Boolean.valueOf(subscriber.getIs_admin());
        sb.isLeave = Boolean.valueOf(subscriber.getIs_leave());
        sb.isSecret = Boolean.valueOf(subscriber.getIs_secret());
        sb.userId = subscriber.getUser_id();
        sb.chatId = chatId;
        return sb;
    }
}