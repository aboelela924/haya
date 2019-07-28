package com.bignerdranch.android.haya.model.repo.networking.joinRoomNetworking;

import android.os.Looper;

import androidx.lifecycle.MutableLiveData;

import com.bignerdranch.android.haya.model.repo.Room;
import com.bignerdranch.android.haya.model.repo.RoomExample;
import com.bignerdranch.android.haya.model.repo.Subscriber;
import com.bignerdranch.android.haya.model.repo.networking.GetSocket;
import com.bignerdranch.android.haya.model.repo.networking.SocketActions;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.logging.Handler;

public class JoinRoomRepo {
    public MutableLiveData<Room> mData = new MutableLiveData<>();
    public MutableLiveData<Subscriber> mSubscriberData = new MutableLiveData<>();
    private static JoinRoomRepo sRoomRepo;

    public static JoinRoomRepo getRoomRepoInstance(){
        if(sRoomRepo == null){
            sRoomRepo = new JoinRoomRepo();
        }
        return sRoomRepo;
    }


    private JoinRoomRepo(){}

    public void joinRoom(String nickname, String roomToken){
        try{
            Socket socket = GetSocket.getSocket();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nickname", nickname);
            jsonObject.put("token", roomToken);
            socket.on(SocketActions.OBSERVE_USER_JOIN, onJoinChat);
            socket.emit(SocketActions.EMIT_JOIN_ROOM, jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

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

    //--------------------------------------------------------------

    private Emitter.Listener onJoinChat = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject json = (JSONObject) args[0];
            Gson gson = new Gson();
            Room room = gson.fromJson(json.toString(), Room.class);
            mData.postValue(room);
        }
    };

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
