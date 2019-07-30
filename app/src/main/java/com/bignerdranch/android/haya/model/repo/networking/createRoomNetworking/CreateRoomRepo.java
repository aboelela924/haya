package com.bignerdranch.android.haya.model.repo.networking.createRoomNetworking;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.bignerdranch.android.haya.App;
import com.bignerdranch.android.haya.model.repo.RoomExample;
import com.bignerdranch.android.haya.model.repo.Subscriber;
import com.bignerdranch.android.haya.model.repo.networking.GetRetrofit;
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


            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    ModelMapper mapper = new ModelMapper();
                    mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
                    for (Subscriber sb : response.body().getRoom().getSubscribers()){
                        SubscriberDB subscriberDB = mapper.map(sb, SubscriberDB.class);
                        App.getInstance().getMyDatabase().subscriber_dao().insertSubscriber(subscriberDB);
                    }
                    ChatDB chatDB = mapper.map(response.body().getRoom(), ChatDB.class);
                    App.getInstance().getMyDatabase().chat_dao().addNewChat(chatDB);
                    return null;
                }
            }.execute();
        }
    }

    @Override
    public void onFailure(Call<RoomExample> call, Throwable t) {

    }
}
