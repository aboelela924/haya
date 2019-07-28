package com.bignerdranch.android.haya.model.repo;

import java.util.List;

public class RoomsExample {
    private List<Room> rooms;

    public RoomsExample(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
