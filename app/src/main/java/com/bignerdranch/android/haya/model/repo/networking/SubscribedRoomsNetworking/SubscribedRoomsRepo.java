package com.bignerdranch.android.haya.model.repo.networking.SubscribedRoomsNetworking;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.bignerdranch.android.haya.model.repo.CurrentUser;
import com.bignerdranch.android.haya.model.repo.Room;
import com.bignerdranch.android.haya.model.repo.RoomsExample;
import com.bignerdranch.android.haya.model.repo.User;
import com.bignerdranch.android.haya.model.repo.networking.GetRetrofit;
import com.hadilq.liveevent.LiveEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SubscribedRoomsRepo {
    private static SubscribedRoomsRepo instance;
    private RoomsExample roomsExample;
    private LiveEvent<List<Room>> mRoomList = new LiveEvent<>();
    private String roomType;

    //Singelton Pattern
    public static SubscribedRoomsRepo getInstance(){
        if(instance == null)
            instance = new SubscribedRoomsRepo();

        return instance;
    }

    //Called by the ViewModel for initialization the mutable data.
    public void setRoomList(String roomType){
        this.roomType = roomType;
        Retrofit retrofit = GetRetrofit.getRetrofitInstance();
        SubscribedRoomsAPI apiObj = retrofit.create(SubscribedRoomsAPI.class);
        User user = CurrentUser.user;
        Log.i("User", CurrentUser.user.getAccessToken());
        Call<RoomsExample> apiCall = apiObj.getSubscribedRooms(CurrentUser.user.getAccessToken());
        apiCall.enqueue(new Callback<RoomsExample>(){

            @Override
            public void onResponse(Call<RoomsExample> call, Response<RoomsExample> response) {
                roomsExample = response.body();
                List<Room> toBeSortedRooms = getRoomsAccordingToType(roomsExample.getRooms());
                Collections.sort(toBeSortedRooms, Collections.reverseOrder());
                mRoomList.setValue(toBeSortedRooms);

                Log.i("SubscribedRoomsRepo", "API Responded");
            }

            public List<Room> getRoomsAccordingToType(List<Room> allRooms) {
                List<Room> roomsAccordingToType = new ArrayList<>();
                    for(Room r : allRooms){
                        if(r.getType().equals(roomType))
                            roomsAccordingToType.add(r);
                    }
                return roomsAccordingToType;
            }

            @Override
            public void onFailure(Call<RoomsExample> call, Throwable t) {
                Log.i("SubscribedRoomsRepo", "No Response, error: " + t.getMessage());
            }
        });
    }

    public LiveEvent<List<Room>> getRoomList() {
        return mRoomList;
    }
}
