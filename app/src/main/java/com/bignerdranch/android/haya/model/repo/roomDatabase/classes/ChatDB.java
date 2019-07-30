package com.bignerdranch.android.haya.model.repo.roomDatabase.classes;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bignerdranch.android.haya.model.repo.Room;

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
    public static Room toRoom(ChatDB chat) {
        Room room = new Room();
        room.set_id(chat.id);
        room.setName(chat.name);
        room.setUpdated_at(String.valueOf(chat.updatedAt));
        room.setCreated_at(chat.createdAt);
        room.setType(chat.type);
        room.setStatus(chat.status);
        room.setToken(chat.token);

        return room;
    }

    public static List<Room> toRoomList(List<ChatDB> chatDBList){
        List<Room> rooms = new ArrayList<Room>();
        for(ChatDB c: chatDBList){
            rooms.add(toRoom(c));
        }
        return rooms;
    }
}
