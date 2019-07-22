package com.bignerdranch.android.haya.model.repo.networking.createRoomNetworking;

public class UpdateRoomInfoBody {

    String room_name;
    String nickname;

    public UpdateRoomInfoBody(String room_name, String nickname) {
        this.room_name = room_name;
        this.nickname = nickname;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
