package com.bignerdranch.android.haya.model.repo.roomDatabase.classes;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bignerdranch.android.haya.model.repo.Room;
import com.bignerdranch.android.haya.model.repo.Subscriber;

import java.util.ArrayList;
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
    public   Long updatedAt;
    @ColumnInfo(name = "created_at")
    public   String createdAt;
    @ColumnInfo(name = "type")
    public   String type;
    @ColumnInfo(name="status")
    public  String status;
    @ColumnInfo(name = "token")
    public  String token;

    public static ChatDB toChat(Room room){
        ChatDB chatDB = new ChatDB();
        chatDB.id = room.getId();
        chatDB.name = room.getName();
        chatDB.updatedAt = Long.parseLong(room.getUpdated_at());
        chatDB.createdAt = room.getCreated_at();
        chatDB.type = room.getType();
        chatDB.status = room.getStatus();
        chatDB.token =room.getToken();
        return chatDB;
    }

    public static List<ChatDB> toChatDBList(List<Room> roomList){
        List<ChatDB> chatDB = new ArrayList<>();
        for(Room r : roomList){
            chatDB.add(toChat(r));
        }

        return chatDB;
    }
    public Room toRoom( Subscriber[] subscriber) {
        Room room = new Room();
        room.set_id(this.id);
        room.setId(this.id);
        room.setName(this.name);
        room.setUpdated_at(String.valueOf(this.updatedAt));
        room.setCreated_at(this.createdAt);
        room.setType(this.type);
        room.setStatus(this.status);
        room.setToken(this.token);
        room.setSubscribers(subscriber);
        return room;
    }

    /*public static List<Room> toRoomList(List<ChatDB> chatDBList){
        List<Room> rooms = new ArrayList<Room>();
        for(ChatDB c: chatDBList){
            rooms.add(toRoom(c));
        }
        return rooms;
    }*/
}
