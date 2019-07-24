package com.bignerdranch.android.haya.model.repo.networking.chatNetworking;

import com.bignerdranch.android.haya.model.repo.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface SyncAPI {
    @POST("api/v1/chat-rooms/messages/sync")
    Call<SyncMessageMaster> getOldMessages(@Header("x-access-token") String token, @Body List<SyncBody> body);
}
