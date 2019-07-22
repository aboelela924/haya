package com.bignerdranch.android.haya.model.repo.networking.createRoomNetworking;

import com.bignerdranch.android.haya.model.repo.RoomExample;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CreateRoomAPI {
    @POST("api/v1/chat-rooms")
    Call<RoomExample> createRoom(@Header("x-access-token") String token,
                                 @Body RoomBody body);
    @PUT("api/v1/chat-rooms/{roomId}/update-init-data")
    Call<RoomExample> updateInfo(@Header("x-access-token") String token,
                                 @Path("roomId") String roomId,
                                 @Body UpdateRoomBody body);
}
