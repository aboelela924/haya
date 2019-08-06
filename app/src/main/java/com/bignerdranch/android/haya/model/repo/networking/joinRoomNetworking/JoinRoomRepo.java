package com.bignerdranch.android.haya.model.repo.networking.joinRoomNetworking;

import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.os.Looper;

import androidx.lifecycle.MutableLiveData;

import com.bignerdranch.android.haya.App;
import com.bignerdranch.android.haya.model.repo.Room;
import com.bignerdranch.android.haya.model.repo.RoomExample;
import com.bignerdranch.android.haya.model.repo.Subscriber;
import com.bignerdranch.android.haya.model.repo.networking.GetSocket;
import com.bignerdranch.android.haya.model.repo.networking.SocketActions;
import com.bignerdranch.android.haya.model.repo.roomDatabase.HayaDatabase;
import com.bignerdranch.android.haya.model.repo.roomDatabase.classes.ChatDB;
import com.bignerdranch.android.haya.model.repo.roomDatabase.classes.SubscriberDB;
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
    private HayaDatabase mDatabase;

    public static JoinRoomRepo getRoomRepoInstance(){
        if(sRoomRepo == null){
            sRoomRepo = new JoinRoomRepo();
        }
        return sRoomRepo;
    }


    private JoinRoomRepo(){
        mDatabase = App.getInstance().getMyDatabase();
    }

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
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    try{
                        SubscriberDB[] subscriberDBS = SubscriberDB.toSubscriberDBArray(room.getSubscribers());
                        mDatabase.subscriber_dao().insertSubscribers(subscriberDBS);
                    }catch (SQLiteConstraintException e){
                        ChatDB chatDB = ChatDB.toChat(room);
                        mDatabase.chat_dao().addNewChat(chatDB);
                        SubscriberDB[] subscriberDBS = SubscriberDB.toSubscriberDBArray(room.getSubscribers());
                        mDatabase.subscriber_dao().insertSubscribers(subscriberDBS);
                    }

                    return null;
                }
            }.execute();
            mData.postValue(room);
        }
    };

    private Emitter.Listener onNicknameUpdate = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject jsonObject = (JSONObject) args[0];
            Gson gson = new Gson();
            Subscriber subscriber = gson.fromJson(jsonObject.toString(), Subscriber.class);
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    mDatabase.subscriber_dao().updateCustomName(subscriber.getId(),
                            subscriber.getCustom_room_name());
                    return null;
                }
            }.execute();
            mSubscriberData.postValue(subscriber);
        }
    };

}
