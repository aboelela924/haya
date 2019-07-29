package com.bignerdranch.android.haya.model.repo.networking.chatSettingsNetworking;

import com.bignerdranch.android.haya.model.repo.BaseURL;
import com.bignerdranch.android.haya.model.repo.SubscriptionExample;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ChatSettingsAPI {

    @PUT("api/v1/chat-rooms/{roomId}/update-room")
    Call<SubscriptionExample> changeRoomCustomName(@Header("x-access-token") String token,
                                                   @Path("roomId") String roomId,
                                                   @Query("custom_room_name") String newName);

    @PUT("api/v1/chat-rooms/{roomId}/secret-status")
    Call<SubscriptionExample> toggleChatSecretOrNormal(@Header("x-access-token") String token,
                                                        @Path("roomId") String roomId);



    @PUT("api/v1/chat-rooms/{roomToken}/toggle-token-status")
    Call<StatusResponse> toggleBurnerCodeStatus(@Header("x-access-token") String token,
                                                @Path("roomToken") String roomToken);
}
