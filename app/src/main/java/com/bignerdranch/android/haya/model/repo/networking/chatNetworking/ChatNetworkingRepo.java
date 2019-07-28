package com.bignerdranch.android.haya.model.repo.networking.chatNetworking;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bignerdranch.android.haya.model.repo.Message;
import com.bignerdranch.android.haya.model.repo.Room;
import com.bignerdranch.android.haya.model.repo.networking.GetRetrofit;
import com.bignerdranch.android.haya.model.repo.networking.GetSocket;
import com.bignerdranch.android.haya.model.repo.networking.SocketActions;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChatNetworkingRepo {
    private static final ChatNetworkingRepo ourInstance = new ChatNetworkingRepo();
    private static final String TAG = "ChatNetworkingRepo";

    public static ChatNetworkingRepo getInstance() {
        return ourInstance;
    }
    public MutableLiveData<Message> mData = new MutableLiveData<>();
    public MutableLiveData<SyncMessageMaster> mMessages = new MutableLiveData<>();
    public MutableLiveData<DeleteMessageResponse> mDeleteResponse = new MutableLiveData<>();
    public MutableLiveData<List<String>> mLastMessage = new MutableLiveData<>();

    private ChatNetworkingRepo() {
    }

    public void syncMessages(String token, List<SyncBody> body){
        Retrofit retrofit = GetRetrofit.getRetrofitInstance();
        SyncAPI syncAPI = retrofit.create(SyncAPI.class);
        Call<SyncMessageMaster> call = syncAPI.getOldMessages(token, body);
        call.enqueue(new Callback<SyncMessageMaster>() {
            @Override
            public void onResponse(Call<SyncMessageMaster> call, Response<SyncMessageMaster> response) {
                if (response.isSuccessful()){
                    mMessages.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<SyncMessageMaster> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void syncLastMessages(String token, List<SyncBody> body){
        Retrofit retrofit = GetRetrofit.getRetrofitInstance();
        SyncAPI syncAPI = retrofit.create(SyncAPI.class);
        Call<SyncMessageMaster> call = syncAPI.getOldMessages(token, body);
        call.enqueue(new Callback<SyncMessageMaster>() {
            @Override
            public void onResponse(Call<SyncMessageMaster> call, Response<SyncMessageMaster> response) {
                if (response.isSuccessful()){
                    Log.i("Test", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<SyncMessageMaster> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
    public MutableLiveData<List<String>> getRoomLastMessages() {
        return this.mLastMessage;
    }
    public void sendMessage(String message, String roomId, int type){
        Socket socket = GetSocket.getSocket();
        try{
            JSONObject json = new JSONObject();
            json.put("message", message);
            json.put("room_id", roomId);
            json.put("type", type);
            socket.emit(SocketActions.EMIT_MESSAGE, json);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void deleteMessage(String roomId, String messageId){
        Socket socket = GetSocket.getSocket();
        JSONObject json = new JSONObject();
        try {
            json.put("room_id", roomId);
            json.put("message_id", messageId);
            socket.emit(SocketActions.EMIT_DELETE_MSG, json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void observeMessageDelete(){
        Socket socket = GetSocket.getSocket();
        socket.on(SocketActions.OBSERVE_MSG_DELETED, onDelete);
    }

    public void observeMessages(){
        Socket socket = GetSocket.getSocket();
        socket.on(SocketActions.OBSERVE_MESSAGE, onGetMessage);
    }

    private Emitter.Listener onGetMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject jsonObject = (JSONObject) args[0];
            Log.d(TAG, "call: "+jsonObject.toString());
            Gson gson = new Gson();
            Message message = gson.fromJson(jsonObject.toString(), Message.class);
            mData.postValue(message);
        }
    };

    private Emitter.Listener onDelete =  new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject jsonObject = (JSONObject) args[0];
            Gson gson = new Gson();
            DeleteMessageResponse response = gson.fromJson(jsonObject.toString(), DeleteMessageResponse.class);
            mDeleteResponse.postValue(response);
        }
    };

}
