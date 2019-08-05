package com.bignerdranch.android.haya.model.repo.networking.SubscribedRoomsNetworking;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bignerdranch.android.haya.App;
import com.bignerdranch.android.haya.model.repo.CurrentUser;
import com.bignerdranch.android.haya.model.repo.Message;
import com.bignerdranch.android.haya.model.repo.Room;
import com.bignerdranch.android.haya.model.repo.RoomsExample;
import com.bignerdranch.android.haya.model.repo.Subscriber;
import com.bignerdranch.android.haya.model.repo.User;
import com.bignerdranch.android.haya.model.repo.networking.GetRetrofit;
import com.bignerdranch.android.haya.model.repo.networking.GetSocket;
import com.bignerdranch.android.haya.model.repo.networking.SocketActions;
import com.bignerdranch.android.haya.model.repo.roomDatabase.HayaDatabase;
import com.bignerdranch.android.haya.model.repo.roomDatabase.classes.ChatDB;
import com.bignerdranch.android.haya.model.repo.roomDatabase.classes.MessageDB;
import com.bignerdranch.android.haya.model.repo.roomDatabase.classes.SubscriberDB;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.hadilq.liveevent.LiveEvent;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

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
                readFromDB();
            }
            private void readFromDB() {
                new AsyncTask<Void,Void, Void>(){

                    @Override
                    protected Void doInBackground(Void... voids) {
                        List<ChatDB> chatsDB = App.getInstance().getMyDatabase().chat_dao().selectAllChats();
                        List<Room> rooms = new ArrayList<>();
                        for (ChatDB chatDB : chatsDB){
                            List<SubscriberDB> subscriberDBS = App.getInstance().getMyDatabase()
                                    .subscriber_dao().getAllSubscribersOfChat(chatDB.id);
                            SubscriberDB[] subscriberArrary = new SubscriberDB[subscriberDBS.size()];
                            subscriberDBS.toArray(subscriberArrary);
                            Subscriber[] roomSubscribers = SubscriberDB.toSubscriberArray(subscriberArrary);
                            Room room = chatDB.toRoom(roomSubscribers);
                            MessageDB messageDB = App.getInstance().getMyDatabase().message_dao().getLastMessageForChat(chatDB.get_id());
                            if(messageDB != null)
                                room.setLastMessage(MessageDB.toMessage(messageDB));
                            rooms.add(room);
                        }
                        mRoomList.postValue(getRoomsAccordingToType(rooms));
                        return null;
                    }
                }.execute();
            }
        });
    }

    public LiveData<List<Room>> getRoomList() {
        return mRoomList;
    }

    public void observeNewChatCreated(){
        Socket socket = GetSocket.getSocket();
        socket.on(SocketActions.OBSERVE_USER_JOIN, onUserJoinNewChat);
    }

    public void observeNewMessage(){
        Socket socket = GetSocket.getSocket();
        socket.on(SocketActions.OBSERVE_MESSAGE, onNewMessage);
    }

    public void stopNewMessageObserver(){
        Socket socket = GetSocket.getSocket();
        socket.off(SocketActions.OBSERVE_MESSAGE);
    }

    Emitter.Listener onUserJoinNewChat = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject jsonObject = (JSONObject) args[0];
            Gson gson = new Gson();
            Room room = gson.fromJson(jsonObject.toString(), Room.class);

            List<Room> roomList = new ArrayList<>();
            roomList.add(room);
            roomList.addAll(mRoomList.getValue());
            mRoomList.postValue(roomList);
        }
    };

    Emitter.Listener onNewMessage = new Emitter.Listener(){
        @Override
        public void call(Object... args) {
            JSONObject jsonObject = (JSONObject) args[0];
            Gson gson = new Gson();
            Message message = gson.fromJson(jsonObject.toString(), Message.class);
            List<Room> roomList = new ArrayList<>();
            Room tempRoom = new Room();
            roomList.add(tempRoom);
            for(Room room: mRoomList.getValue())
            {
                if(room.get_id().equals(message.getRoom_id()))
                {
                    room.setLastMessage(message);
                    roomList.set(0,room);
                }
                roomList.add(room);
            }
            mRoomList.postValue(roomList);
        }
    };
}
