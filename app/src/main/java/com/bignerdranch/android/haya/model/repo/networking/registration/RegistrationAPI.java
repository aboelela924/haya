package com.bignerdranch.android.haya.model.repo.networking.registration;

import com.bignerdranch.android.haya.model.repo.MessageResponseUpdatePassword;
import com.bignerdranch.android.haya.model.repo.UserExample;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface RegistrationAPI {
    @POST("api/v1/auth/signup")
    Call<UserExample> postRegister(@Body RegistrationBody body);

    @PUT("/api/v1/auth/update-password")
    Call<MessageResponseUpdatePassword> updatePassword(@Header("x-access-token") String token,
                                                       @Body PasswordBody body);
}
