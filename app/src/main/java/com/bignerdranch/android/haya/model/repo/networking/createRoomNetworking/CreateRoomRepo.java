package com.bignerdranch.android.haya.model.repo.networking.createRoomNetworking;

import androidx.lifecycle.MutableLiveData;

import com.bignerdranch.android.haya.model.repo.RoomExample;
import com.bignerdranch.android.haya.model.repo.networking.GetRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CreateRoomRepo implements Callback<RoomExample> {
    private static final CreateRoomRepo ourInstance = new CreateRoomRepo();
    public MutableLiveData<RoomExample> mData = new MutableLiveData<>();

    public static CreateRoomRepo getInstance() {
        return ourInstance;
    }

    private CreateRoomRepo() {
    }

    public void createRoom(String token, RoomBody body){
        Retrofit retrofit = GetRetrofit.getRetrofitInstance();
        CreateRoomAPI repo = retrofit.create(CreateRoomAPI.class);
        Call<RoomExample> call = repo.createRoom(token,body);
        call.enqueue(this);
    }

    public void updateInfo(String token,String roomId, UpdateRoomBody body){
        Retrofit retrofit = GetRetrofit.getRetrofitInstance();
        CreateRoomAPI repo = retrofit.create(CreateRoomAPI.class);
        Call<RoomExample> call = repo.updateInfo(token,roomId, body);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<RoomExample> call, Response<RoomExample> response) {
        if (response.isSuccessful()){
            mData.setValue(response.body());
        }
    }

    @Override
    public void onFailure(Call<RoomExample> call, Throwable t) {

    }
}
