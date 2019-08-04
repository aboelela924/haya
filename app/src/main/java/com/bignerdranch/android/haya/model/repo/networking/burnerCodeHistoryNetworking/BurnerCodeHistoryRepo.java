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
    public LiveEvent<String> mDeletedRoomIdEvent = new LiveEvent<>();
    public LiveEvent<String> mError = new LiveEvent<>();

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
                }else{
                    mError.setValue(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<HistoryModel> call, Throwable t) {
                mError.setValue(t.getMessage());
            }
        });
    }

    public void deleteRoom(String userToken, String roomId){
        DeleteBody body = new DeleteBody();
        body.rooms.add(roomId);

        Retrofit retrofit = GetRetrofit.getRetrofitInstance();
        GetDeactivatedRoomsAPI api = retrofit.create(GetDeactivatedRoomsAPI.class);
        Call<StatusMaster> call = api.deleteRoom(userToken, body);
        call.enqueue(new Callback<StatusMaster>() {
            @Override
            public void onResponse(Call<StatusMaster> call, Response<StatusMaster> response) {
                if (response.isSuccessful()){
                    mDeletedRoomIdEvent.setValue(roomId);
                }else{
                    mError.setValue(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<StatusMaster> call, Throwable t) {
                mError.setValue(t.getMessage());
            }
        });
    }
}
