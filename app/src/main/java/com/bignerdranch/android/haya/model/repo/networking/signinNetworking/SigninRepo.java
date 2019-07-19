package com.bignerdranch.android.haya.model.repo.networking.signinNetworking;

import androidx.lifecycle.MutableLiveData;

import com.bignerdranch.android.haya.model.repo.UserExample;
import com.bignerdranch.android.haya.model.repo.networking.GetRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SigninRepo implements Callback<UserExample> {
    private static final SigninRepo ourInstance = new SigninRepo();
    public MutableLiveData<UserExample> mData = new MutableLiveData<>();
    public static SigninRepo getInstance() {
        return ourInstance;
    }

    private SigninRepo() {
    }

    public void signinUser(SigninBody body){
        Retrofit retrofit = GetRetrofit.getRetrofitInstance();

        SigninAPi api = retrofit.create(SigninAPi.class);
        Call<UserExample> call = api.signIn(body);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<UserExample> call, Response<UserExample> response) {
        if (response.isSuccessful()){
            mData.setValue(response.body());
        }
    }

    @Override
    public void onFailure(Call<UserExample> call, Throwable t) {

    }
}
