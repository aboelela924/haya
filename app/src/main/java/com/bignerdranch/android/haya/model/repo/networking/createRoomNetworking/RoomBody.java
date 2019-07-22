package com.bignerdranch.android.haya.model.repo.networking.createRoomNetworking;

public class RoomBody {
    String room_name;
    int type;

    public RoomBody(String room_name, int type) {
        this.room_name = room_name;
        this.type = type;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
