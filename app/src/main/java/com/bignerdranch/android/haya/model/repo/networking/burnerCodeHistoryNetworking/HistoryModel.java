package com.bignerdranch.android.haya.model.repo.networking.burnerCodeHistoryNetworking;

public class HistoryModel {
    private History[] rooms;

    public History[] getRooms ()
    {
        return rooms;
    }

    public void setRooms (History[] rooms)
    {
        this.rooms = rooms;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [rooms = "+rooms+"]";
    }
}
