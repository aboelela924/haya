package com.bignerdranch.android.haya.model.repo.networking.chatSettingsNetworking;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.bignerdranch.android.haya.model.repo.DeleteResult;
import com.bignerdranch.android.haya.model.repo.SubscriptionExample;
import com.bignerdranch.android.haya.model.repo.networking.GetRetrofit;
import com.bignerdranch.android.haya.model.repo.networking.GetSocket;
import com.bignerdranch.android.haya.model.repo.networking.SocketActions;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

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
                }
            }

            @Override
            public void onFailure(Call<SubscriptionExample> call, Throwable t) {

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
                }
            }

            @Override
            public void onFailure(Call<SubscriptionExample> call, Throwable t) {

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

    Emitter.Listener onAllMessagesDeleted = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject jsonObject = (JSONObject) args[0];
            Gson gson = new Gson();
            DeleteResult result = gson.fromJson(jsonObject.toString(),DeleteResult.class);
            mDeleteData.postValue(result);
        }
    };
}
