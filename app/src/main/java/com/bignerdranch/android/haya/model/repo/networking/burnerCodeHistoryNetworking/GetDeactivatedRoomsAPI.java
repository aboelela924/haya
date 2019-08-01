package com.bignerdranch.android.haya.model.repo.networking.burnerCodeHistoryNetworking;

import com.bignerdranch.android.haya.model.repo.networking.chatNetworking.SyncMessageMaster;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface GetDeactivatedRoomsAPI {

    @GET("api/v1/chat-rooms/rooms/pending")
    Call<HistoryModel> getDeactivatedRooms(@Header("x-access-token") String token);

}
