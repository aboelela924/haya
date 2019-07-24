package com.bignerdranch.android.haya.model.repo.networking.chatNetworking;

public class DeleteMessageResponse {
    String room_id;
    String message_id;

    public DeleteMessageResponse(){

    }

    public DeleteMessageResponse(String room_id, String message_id) {
        this.room_id = room_id;
        this.message_id = message_id;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }
}
