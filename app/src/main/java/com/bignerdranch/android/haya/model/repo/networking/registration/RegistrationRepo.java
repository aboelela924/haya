package com.bignerdranch.android.haya.model.repo.networking.registration;

import androidx.lifecycle.MutableLiveData;

import com.bignerdranch.android.haya.model.repo.User;
import com.bignerdranch.android.haya.model.repo.UserExample;
import com.bignerdranch.android.haya.model.repo.networking.GetRetrofit;
import com.bignerdranch.android.haya.model.repo.networking.GetSocket;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegistrationRepo implements Callback<UserExample> {
    private static RegistrationRepo sRepo = null;
    public MutableLiveData<UserExample> mData =  new MutableLiveData<>();

    private RegistrationRepo(){ }

    public static RegistrationRepo getInstance(){
        if (sRepo == null){
            sRepo = new RegistrationRepo();
        }
        return sRepo;
    }

    public void createUser(RegistrationBody body){
        Retrofit retrofit = GetRetrofit.getRetrofitInstance();

        RegistrationAPI registrationAPI = retrofit.create(RegistrationAPI.class);
        Call<UserExample> call = registrationAPI.postRegister(body);
        call.enqueue(this);
    }


    @Override
    public void onResponse(Call<UserExample> call, Response<UserExample> response) {
        if(response.isSuccessful()){
            mData.setValue(response.body());
            GetSocket.getInstance(response.body().getUser().getAccessToken());
        }
    }

    @Override
    public void onFailure(Call<UserExample> call, Throwable t) {

    }
}
