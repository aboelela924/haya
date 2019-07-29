package com.bignerdranch.android.haya.model.repo.networking.chatSettingsNetworking;

public class StatusResponse {
    private RoomResponse room;

    private String room_status;

    public RoomResponse getRoom ()
    {
        return room;
    }

    public void setRoom (RoomResponse room)
    {
        this.room = room;
    }

    public String getRoom_status ()
    {
        return room_status;
    }

    public void setRoom_status (String room_status)
    {
        this.room_status = room_status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [room = "+room+", room_status = "+room_status+"]";
    }
}
