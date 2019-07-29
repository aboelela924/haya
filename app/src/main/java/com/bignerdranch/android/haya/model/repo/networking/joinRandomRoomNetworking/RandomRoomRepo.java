package com.bignerdranch.android.haya.model.repo.networking.joinRandomRoomNetworking;

import androidx.lifecycle.MutableLiveData;

import com.bignerdranch.android.haya.model.repo.Room;
import com.bignerdranch.android.haya.model.repo.Subscriber;
import com.bignerdranch.android.haya.model.repo.networking.GetSocket;
import com.bignerdranch.android.haya.model.repo.networking.SocketActions;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.hadilq.liveevent.LiveEvent;

import org.json.JSONException;
import org.json.JSONObject;

public class RandomRoomRepo {
    public LiveEvent<Room> mRoomData = new LiveEvent<>();

    private static RandomRoomRepo randomRoomInstance;
    public static RandomRoomRepo getInstance() {
        if(randomRoomInstance == null)
            randomRoomInstance = new RandomRoomRepo();

        return randomRoomInstance;
    }

    public void joinQueue() {
        try{
            Socket socket = GetSocket.getSocket();

            //Add the observer. To be triggered later when a random chat starts.
            socket.on(SocketActions.OBSERVE_START_RANDOM_CHAT, startRandomChat);
            //Send to server the required data for the operation.
            JSONObject jsonObject = new JSONObject();
            socket.emit(SocketActions.EMIT_JOIN_RANDOM_CHAT, jsonObject);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void leaveQueue() {
        try{
            Socket socket = GetSocket.getSocket();
            JSONObject jsonObject = new JSONObject();
            socket.emit(SocketActions.EMIT_LEAVE_RANDOM_CHAT, jsonObject);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    //----------------------------------------------------
    private Emitter.Listener startRandomChat = new Emitter.Listener() {
        //Triggered when two random users are met together.
        //Open chat.
        @Override
        public void call(Object... args) {
            JSONObject jsonObject = (JSONObject) args[0];
            Gson gson = new Gson();
            Room room = gson.fromJson(jsonObject.toString(), Room.class);
            mRoomData.postValue(room);
        }
    };

}

