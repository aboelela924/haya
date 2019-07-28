package com.bignerdranch.android.haya.model.repo.networking.SubscribedRoomsNetworking;

import androidx.room.Query;

import com.bignerdranch.android.haya.model.repo.Room;
import com.bignerdranch.android.haya.model.repo.RoomsExample;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface SubscribedRoomsAPI {
    @GET("api/v1/chat-rooms")
    Call<RoomsExample> getSubscribedRooms(@Header("x-access-token") String token);
}
