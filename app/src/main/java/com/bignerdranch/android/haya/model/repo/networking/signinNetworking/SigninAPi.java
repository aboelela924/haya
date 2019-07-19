package com.bignerdranch.android.haya.model.repo.networking.signinNetworking;

import com.bignerdranch.android.haya.model.repo.UserExample;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SigninAPi {
    @POST("api/v1/auth/signin")
    Call<UserExample> signIn(@Body SigninBody body);
}
