package com.bignerdranch.android.haya.model.repo.networking.SubscribedRoomsNetworking;

public interface RoomOptionsCallBacks {
    void deleteRoom(String room_id);
    void setToPrivate(String room_id);
}
