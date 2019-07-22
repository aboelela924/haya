package com.bignerdranch.android.haya.model.repo;

public class RoomExample {
    private Room room;

    public Room getRoom ()
    {
        return room;
    }

    public void setRoom (Room room)
    {
        this.room = room;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [room = "+room+"]";
    }
}
