package com.bignerdranch.android.haya.model.repo.networking.chatNetworking;

public class SyncBody {

    String room_id;
    long timestamp;

    public SyncBody(String room_id, long timestamp) {
        this.room_id = room_id;
        this.timestamp = timestamp;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
