package com.bignerdranch.android.haya.model.repo.networking.chatSettingsNetworking;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.bignerdranch.android.haya.model.repo.DeleteResult;
import com.bignerdranch.android.haya.model.repo.Room;
import com.bignerdranch.android.haya.model.repo.Subscriber;
import com.bignerdranch.android.haya.model.repo.SubscriptionExample;
import com.bignerdranch.android.haya.model.repo.networking.GetRetrofit;
import com.bignerdranch.android.haya.model.repo.networking.GetSocket;
import com.bignerdranch.android.haya.model.repo.networking.SocketActions;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hadilq.liveevent.LiveEvent;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChatSettingsRepo {
    private static final String TAG = "ChatSettingsRepo";

    private static ChatSettingsRepo sRepo;

    public MutableLiveData<SubscriptionExample> mSubscriptionData = new MutableLiveData<>();
    public MutableLiveData<DeleteResult> mDeleteData = new MutableLiveData<>();
    public MutableLiveData<Room> mRoomData = new MutableLiveData<>();
    public MutableLiveData<Boolean> mBurnerCodeStatus = new MutableLiveData<>();
    public MutableLiveData<Subscriber> mSubscriberData = new MutableLiveData<>();
    private LiveEvent<String> mError = new LiveEvent<>();

    private ChatSettingsRepo(){

    }
    public static ChatSettingsRepo getInstance(){
        if(sRepo == null){
            sRepo = new ChatSettingsRepo();
        }
        return sRepo;
    }

    public void updateChatCustomName(String accessToken, String roomId, String name){
        Retrofit retrofit = GetRetrofit.getRetrofitInstance();

        ChatSettingsAPI api = retrofit.create(ChatSettingsAPI.class);
        Call<SubscriptionExample> call = api.changeRoomCustomName(accessToken, roomId, name);
        call.enqueue(new Callback<SubscriptionExample>() {
            @Override
            public void onResponse(Call<SubscriptionExample> call, Response<SubscriptionExample> response) {
                if(response.isSuccessful()){
                    mSubscriptionData.setValue(response.body());
                }else{
                    mError.setValue("Failed to update chat name. Try again.");
                }
            }

            @Override
            public void onFailure(Call<SubscriptionExample> call, Throwable t) {
                mError.setValue("Failed to update chat name. Try again.");
            }
        });
    }

    public void toggleChat(String accessToken, String roomId){
        Retrofit retrofit = GetRetrofit.getRetrofitInstance();

        ChatSettingsAPI api = retrofit.create(ChatSettingsAPI.class);
        Call<SubscriptionExample> call = api.toggleChatSecretOrNormal(accessToken, roomId);
        call.enqueue(new Callback<SubscriptionExample>() {
            @Override
            public void onResponse(Call<SubscriptionExample> call, Response<SubscriptionExample> response) {
                if (response.isSuccessful()){
                    mSubscriptionData.setValue(response.body());
                }else{

                }
            }

            @Override
            public void onFailure(Call<SubscriptionExample> call, Throwable t) {

            }
        });
    }

    public void toggleBurnerCodeStatus(String accessToken, String roomToken){
        Retrofit retrofit = GetRetrofit.getRetrofitInstance();

        ChatSettingsAPI api = retrofit.create(ChatSettingsAPI.class);
        Call<StatusResponse> call = api.toggleBurnerCodeStatus(accessToken,roomToken);
        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                if (response.isSuccessful()){
                    mBurnerCodeStatus.postValue(Boolean.valueOf(response.body().getRoom_status()));
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {

            }
        });
    }

    public void deleteAllMessages(String roomId){
        Socket socket = GetSocket.getSocket();
        try{
            JSONObject json = new JSONObject();
            json.put("room_id", roomId);
            socket.on(SocketActions.OBSERVE_MSG_DELETE_ALL, onAllMessagesDeleted);
            socket.emit(SocketActions.EMIT_DELETE_ALL, json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void disconnect(String roomId){
        Socket socket = GetSocket.getSocket();
        try{
            JSONObject json = new JSONObject();
            json.put("room_id", roomId);
            socket.on(SocketActions.OBSERVE_USER_LEAVE, onAllMessagesDeleted);
            socket.emit(SocketActions.EMIT_LEAVE_ROOM, json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void observeUsersJoin(){
        Socket socket = GetSocket.getSocket();
        socket.on(SocketActions.OBSERVE_USER_JOIN, onUserJoin);
    }

    public void observeUserLeave(){
        Socket socket = GetSocket.getSocket();
        socket.on(SocketActions.OBSERVE_USER_LEAVE, onUserLeave);
    }

    Emitter.Listener onUserJoin = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject jsonObject = (JSONObject) args[0];
            Gson gson = new Gson();
            Room room = gson.fromJson(jsonObject.toString(), Room.class);
            mRoomData.postValue(room);
        }
    };

    Emitter.Listener onUserLeave = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject jsonObject = (JSONObject) args[0];
            Gson gson = new Gson();
        }
    };

    Emitter.Listener onAllMessagesDeleted = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject jsonObject = (JSONObject) args[0];
            Gson gson = new Gson();
            DeleteResult result = gson.fromJson(jsonObject.toString(),DeleteResult.class);
            mDeleteData.postValue(result);
        }
    };

    public void changeNickname(String nickname, String roomId){
        try{
            Socket socket = GetSocket.getSocket();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nickname", nickname);
            jsonObject.put("room_id", roomId);
            socket.on(SocketActions.OBSERVE_NICKNAME_UPDATED, onNicknameUpdate);
            socket.emit(SocketActions.EMIT_UPDATE_NICKNAME, jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Emitter.Listener onNicknameUpdate = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject jsonObject = (JSONObject) args[0];
            Gson gson = new Gson();
            Subscriber subscriber = gson.fromJson(jsonObject.toString(), Subscriber.class);
            mSubscriberData.postValue(subscriber);
        }
    };
}
