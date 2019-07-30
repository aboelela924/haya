package com.bignerdranch.android.haya.model.repo.networking.createRoomNetworking;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.bignerdranch.android.haya.App;
import com.bignerdranch.android.haya.model.repo.Room;
import com.bignerdranch.android.haya.model.repo.RoomExample;
import com.bignerdranch.android.haya.model.repo.Subscriber;
import com.bignerdranch.android.haya.model.repo.networking.GetRetrofit;
import com.bignerdranch.android.haya.model.repo.roomDatabase.HayaDatabase;
import com.bignerdranch.android.haya.model.repo.roomDatabase.classes.ChatDB;
import com.bignerdranch.android.haya.model.repo.roomDatabase.classes.SubscriberDB;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CreateRoomRepo implements Callback<RoomExample> {
    private static final CreateRoomRepo ourInstance = new CreateRoomRepo();
    public MutableLiveData<RoomExample> mData = new MutableLiveData<>();

    private HayaDatabase mHayaDatabase;

    public static CreateRoomRepo getInstance() {
        return ourInstance;
    }

    private CreateRoomRepo() {
        mHayaDatabase = App.getInstance().getMyDatabase();
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

            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    Room room = response.body().getRoom();
                    mHayaDatabase.chat_dao().addNewChat(ChatDB.fromChat(room));
                    SubscriberDB[] subscriberDBS = new SubscriberDB[room.getSubscribers().length];
                    for(int i=0; i<room.getSubscribers().length; i++){
                        Subscriber subscriber = room.getSubscribers()[i];
                        subscriberDBS[i] = SubscriberDB.fromSubscriber(subscriber);
                    }
                    mHayaDatabase.subscriber_dao().insertSubscribers(subscriberDBS);
                    return null;
                }
            }.execute();
        }
    }

    @Override
    public void onFailure(Call<RoomExample> call, Throwable t) {

    }
}
