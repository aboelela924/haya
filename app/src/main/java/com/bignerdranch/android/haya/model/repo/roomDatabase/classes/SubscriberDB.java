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


    public static SubscriberDB fromSubscriber(Subscriber subscriber){
        SubscriberDB sb = new SubscriberDB();
        sb.id = subscriber.getId();
        sb.name = subscriber.getName();
        sb.createdAt = subscriber.getCreated_at();
        sb.updatedAt = subscriber.getUpdated_at();
        sb.isAdmin = Boolean.valueOf(subscriber.getIs_admin());
        sb.isLeave = Boolean.valueOf(subscriber.getIs_leave());
        sb.isSecret = Boolean.valueOf(subscriber.getIs_secret());
        sb.userId = subscriber.getUser_id();
        sb.chatId = subscriber.getRoom_id();
        return sb;
    }

    public Subscriber toSubscriber(){
        Subscriber subscriber = new Subscriber();
        subscriber.setId(this.id);
        subscriber.setName(this.name);
        subscriber.setCreated_at(this.createdAt);
        subscriber.setUpdated_at(this.updatedAt);
        subscriber.setIs_admin(String.valueOf(this.isAdmin));
        subscriber.setIs_leave(String.valueOf(this.isLeave));
        subscriber.setIs_secret(String.valueOf(this.isSecret));
        subscriber.setUser_id(this.userId);
        subscriber.setRoom_id(this.chatId);
        return subscriber;
    }

    public static SubscriberDB[] toSubscriberDBArray(Subscriber[] subscribers){
        SubscriberDB[] subscriberDBS = new SubscriberDB[subscribers.length];
        for(int i=0; i<subscribers.length; i++){
            Subscriber subscriber = subscribers[i];
            subscriberDBS[i] = SubscriberDB.fromSubscriber(subscriber);
        }
        return subscriberDBS;
    }

    public static Subscriber[] toSubscriberArray(SubscriberDB[] subscriberDBS){
        Subscriber[] subscribers = new Subscriber[subscriberDBS.length];
        for (int i = 0; i < subscriberDBS.length; i++){
            SubscriberDB subscriberDB = subscriberDBS[i];
            subscribers[i] = subscriberDB.toSubscriber();
        }
        return subscribers;
    }
}