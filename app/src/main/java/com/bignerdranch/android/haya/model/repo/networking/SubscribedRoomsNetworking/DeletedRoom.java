package com.bignerdranch.android.haya.model.repo.networking.SubscribedRoomsNetworking;

public class DeletedRoom {

    private String room_id;

    public DeletedRoom(String room_id) {
        this.room_id = room_id;
    }
    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }
    public String getRoom_id() {
        return room_id;
    }
}
