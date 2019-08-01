package com.bignerdranch.android.haya.model.repo.networking.burnerCodeHistoryNetworking;

import com.bignerdranch.android.haya.model.repo.networking.GetRetrofit;
import com.hadilq.liveevent.LiveEvent;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BurnerCodeHistoryRepo {
    private static final BurnerCodeHistoryRepo ourInstance = new BurnerCodeHistoryRepo();

    public LiveEvent<HistoryModel> mUnusedBurnerCodes = new LiveEvent<>();

    public static BurnerCodeHistoryRepo getInstance() {
        return ourInstance;
    }

    private BurnerCodeHistoryRepo() {
    }

    public void getBurnerCodeHistory(String userToken){
        Retrofit retrofit = GetRetrofit.getRetrofitInstance();

        GetDeactivatedRoomsAPI  api = retrofit.create(GetDeactivatedRoomsAPI.class);
        Call<HistoryModel> call = api.getDeactivatedRooms(userToken);
        call.enqueue(new Callback<HistoryModel>() {
            @Override
            public void onResponse(Call<HistoryModel> call, Response<HistoryModel> response) {
                if(response.isSuccessful()){
                    mUnusedBurnerCodes.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<HistoryModel> call, Throwable t) {

            }
        });
    }
}
