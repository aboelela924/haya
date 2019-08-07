package com.bignerdranch.android.haya.model.repo.networking.SubscribedRoomsNetworking;

import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.RoomDatabase;

import com.bignerdranch.android.haya.App;
import com.bignerdranch.android.haya.model.repo.CurrentUser;
import com.bignerdranch.android.haya.model.repo.DeleteResult;
import com.bignerdranch.android.haya.model.repo.Message;
import com.bignerdranch.android.haya.model.repo.Room;
import com.bignerdranch.android.haya.model.repo.RoomsExample;
import com.bignerdranch.android.haya.model.repo.Subscriber;
import com.bignerdranch.android.haya.model.repo.User;
import com.bignerdranch.android.haya.model.repo.networking.GetRetrofit;
import com.bignerdranch.android.haya.model.repo.networking.GetSocket;
import com.bignerdranch.android.haya.model.repo.networking.SocketActions;
import com.bignerdranch.android.haya.model.repo.networking.chatNetworking.ChatNetworkingRepo;
import com.bignerdranch.android.haya.model.repo.networking.chatNetworking.SyncAPI;
import com.bignerdranch.android.haya.model.repo.networking.chatNetworking.SyncBody;
import com.bignerdranch.android.haya.model.repo.networking.chatNetworking.SyncMessage;
import com.bignerdranch.android.haya.model.repo.networking.chatNetworking.SyncMessageMaster;
import com.bignerdranch.android.haya.model.repo.roomDatabase.HayaDatabase;
import com.bignerdranch.android.haya.model.repo.roomDatabase.classes.ChatDB;
import com.bignerdranch.android.haya.model.repo.roomDatabase.classes.MessageDB;
import com.bignerdranch.android.haya.model.repo.roomDatabase.classes.SubscriberDB;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.hadilq.liveevent.LiveEvent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
    private HayaDatabase mDatabase;
    private String roomType;

    //Singelton Pattern
    public static SubscribedRoomsRepo getInstance(){
        if(instance == null)
            instance = new SubscribedRoomsRepo();

        return instance;
    }

    public SubscribedRoomsRepo(){
        mDatabase = App.getInstance().getMyDatabase();
    }


    public void sync(String accessToken){
        Retrofit retrofit = GetRetrofit.getRetrofitInstance();
        SubscribedRoomsAPI api = retrofit.create(SubscribedRoomsAPI.class);
        Call<RoomsExample> call = api.getSubscribedRooms(accessToken);
        call.enqueue(new Callback<RoomsExample>() {
            @Override
            public void onResponse(Call<RoomsExample> call, Response<RoomsExample> response) {
                if (response.isSuccessful()){
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            List<Room> rooms = response.body().getRooms();
                            ChatNetworkingRepo repo = ChatNetworkingRepo.getInstance();
                            for (Room room : rooms){
                                MessageDB messageDB = mDatabase.message_dao().getLastMessageForChat(room.getId());
                                if (messageDB == null) continue;
                                if(messageDB != null){
                                    SyncBody body = new SyncBody(room.getId(), Long.valueOf(messageDB.updatedAt));
                                    List<SyncBody> bodies = new ArrayList<>();
                                    bodies.add(body);
                                    repo.syncMessages(accessToken, bodies, room.getSubscribers());
                                }

                            }
                            return null;
                        }
                    }.execute();
                }
            }

            @Override
            public void onFailure(Call<RoomsExample> call, Throwable t) {

            }
        });
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
    public void observeRoomDeleted(){
        Socket socket = GetSocket.getSocket();
        socket.on(SocketActions.OBSERVE_ROOM_DELETED, onRoomDeleted);
    }

    public void disconnectRoom(String room_id){
        Socket socket = GetSocket.getSocket();
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("room_id", room_id);
            socket.emit(SocketActions.EMIT_LEAVE_ROOM, jsonObject);
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void stopNewChatObserver(){
        Socket socket = GetSocket.getSocket();
        socket.off(SocketActions.OBSERVE_USER_JOIN);
    }
    public void stopNewMessageObserver(){
        Socket socket = GetSocket.getSocket();
        socket.off(SocketActions.OBSERVE_MESSAGE);
    }
    public void stopOnRoomDeleted(){
        Socket socket = GetSocket.getSocket();
        socket.off(SocketActions.OBSERVE_ROOM_DELETED);
    }

    private Emitter.Listener onUserJoinNewChat = args -> {
        JSONObject jsonObject = (JSONObject) args[0];
        Gson gson = new Gson();
        Room room = gson.fromJson(jsonObject.toString(), Room.class);
        if(room.getType().equals(roomType)){
            List<Room> roomList = new ArrayList<>();
            roomList.add(room);
            roomList.addAll(mRoomList.getValue());
            roomList.addAll(mRoomList.getValue());
            mRoomList.postValue(roomList);
        }
    };
    private Emitter.Listener onNewMessage = args -> {
        JSONObject jsonObject = (JSONObject) args[0];
        Gson gson = new Gson();
        Message message = gson.fromJson(jsonObject.toString(), Message.class);
        String room_id_ofMessage = message.getRoom_id();

        List<Room> finalRoomList = new ArrayList<>();
        Room tempRoom = new Room();
        finalRoomList.add(tempRoom);
        List<Room> copymRoomList = mRoomList.getValue();
        for (Room room : copymRoomList) { //O(N)

            if (room.get_id().equals(room_id_ofMessage)) {
                room.setLastMessage(message);
                finalRoomList.set(0, room);
            } else
                finalRoomList.add(room);
        }
        mRoomList.postValue(finalRoomList); //O(N)
    };
    private Emitter.Listener onRoomDeleted = args->{
        JSONObject jsonObject = (JSONObject) args[0];
        Gson gson = new Gson();
        DeletedRoom deletedRoom = gson.fromJson(jsonObject.toString(),DeletedRoom.class);

        List<Room> copymRoomList = mRoomList.getValue();
        List<Room> finalRoomList = new ArrayList<>();
        for(Room room : copymRoomList){ //O(N)
            if(!room.get_id().equals(deletedRoom.getRoom_id()))
                finalRoomList.add(room);
            else
            {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        App.getInstance().getMyDatabase().chat_dao().deleteChat(ChatDB.toChat(room));
                        return null;
                    }
                }.execute();
            }
        }
        mRoomList.postValue(finalRoomList); //O(N)
    };
}
