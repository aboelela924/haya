package com.bignerdranch.android.haya.model.repo.roomDatabase.classes;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bignerdranch.android.haya.model.repo.Room;
import com.bignerdranch.android.haya.model.repo.Subscriber;

import java.util.Date;
import java.util.List;

@Entity(tableName = "chat")
public class ChatDB {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    public   String id;
    @ColumnInfo(name = "name")
    public   String name;
    @ColumnInfo(name = "updated_at")
    public   String updatedAt;
    @ColumnInfo(name = "created_at")
    public   String createdAt;
    @ColumnInfo(name = "type")
    public   String type;
    @ColumnInfo(name="status")
    public  String status;
    @ColumnInfo(name = "token")
    public  String token;

    public static ChatDB fromChat(Room room){
        ChatDB chatDB = new ChatDB();
        chatDB.id = room.getId();
        chatDB.name = room.getName();
        chatDB.updatedAt = room.getUpdated_at();
        chatDB.createdAt = room.getCreated_at();
        chatDB.type = room.getType();
        chatDB.status = room.getStatus();
        chatDB.token =room.getToken();
        return chatDB;
    }

    public Room toChat(Subscriber[] subscribers){
        Room room = new Room();
        room.setId(this.id);
        room.setName(this.name);
        room.setUpdated_at(this.updatedAt);
        room.setCreated_at(this.createdAt);
        room.setType(this.type);
        room.setStatus(this.status);
        room.setType(this.token);
        room.set_id(this.id);
        room.setSubscribers(subscribers);
        return room;
    }

}
