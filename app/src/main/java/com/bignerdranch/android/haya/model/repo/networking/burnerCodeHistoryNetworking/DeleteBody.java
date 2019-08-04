package com.bignerdranch.android.haya.model.repo.networking.burnerCodeHistoryNetworking;

import java.util.ArrayList;
import java.util.List;

public class DeleteBody {
    public List<String> rooms = new ArrayList<>();

    public List<String> getRooms() {
        return rooms;
    }

    public void setRooms(List<String> rooms) {
        this.rooms = rooms;
    }
}
