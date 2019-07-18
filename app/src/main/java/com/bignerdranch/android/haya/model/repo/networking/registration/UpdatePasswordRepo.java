package com.bignerdranch.android.haya.model.repo.networking.registration;

import androidx.lifecycle.MutableLiveData;

import com.bignerdranch.android.haya.model.repo.MessageResponseUpdatePassword;
import com.bignerdranch.android.haya.model.repo.networking.GetRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdatePasswordRepo implements Callback<MessageResponseUpdatePassword> {
    private static final UpdatePasswordRepo ourInstance = new UpdatePasswordRepo();
    MutableLiveData<MessageResponseUpdatePassword> mData = new MutableLiveData<>();

    public static UpdatePasswordRepo getInstance() {
        return ourInstance;
    }

    private UpdatePasswordRepo() {
    }

    public void updatePassword(String token, PasswordBody body){
        Retrofit retrofit = GetRetrofit.getRetrofitInstance();

        RegistrationAPI api = retrofit.create(RegistrationAPI.class);
        Call<MessageResponseUpdatePassword> call = api.updatePassword(token, body);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<MessageResponseUpdatePassword> call, Response<MessageResponseUpdatePassword> response) {
        if (response.isSuccessful()){

        }
    }

    @Override
    public void onFailure(Call<MessageResponseUpdatePassword> call, Throwable t) {

    }
}
