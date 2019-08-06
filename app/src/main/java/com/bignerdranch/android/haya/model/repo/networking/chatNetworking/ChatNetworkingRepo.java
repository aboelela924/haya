package com.bignerdranch.android.haya.model.repo.networking.chatNetworking;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bignerdranch.android.haya.App;
import com.bignerdranch.android.haya.model.repo.Message;
import com.bignerdranch.android.haya.model.repo.Room;
import com.bignerdranch.android.haya.model.repo.Subscriber;
import com.bignerdranch.android.haya.model.repo.networking.GetRetrofit;
import com.bignerdranch.android.haya.model.repo.networking.GetSocket;
import com.bignerdranch.android.haya.model.repo.networking.SocketActions;
import com.bignerdranch.android.haya.model.repo.roomDatabase.HayaDatabase;
import com.bignerdranch.android.haya.model.repo.roomDatabase.classes.MessageDB;
import com.bignerdranch.android.haya.model.repo.roomDatabase.classes.SubscriberDB;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hadilq.liveevent.LiveEvent;

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

    private HayaDatabase mDatabase;

    public static ChatNetworkingRepo getInstance() {
        return ourInstance;
    }
    public LiveEvent<Message> mData = new LiveEvent<>();
    public LiveEvent<SyncMessageMaster> mMessages = new LiveEvent<>();
    public LiveEvent<DeleteMessageResponse> mDeleteResponse = new LiveEvent<>();
    public LiveEvent<List<String>> mLastMessage = new LiveEvent<>();
    public LiveEvent<String> mLastMessageTime = new LiveEvent<>();
    public LiveEvent<String> mErrorEvent = new LiveEvent<>();

    private ChatNetworkingRepo() {
        mDatabase = App.getInstance().getMyDatabase();
    }

    public void syncMessages(String token, List<SyncBody> body, Subscriber[] subscribers){
        Retrofit retrofit = GetRetrofit.getRetrofitInstance();
        SyncAPI syncAPI = retrofit.create(SyncAPI.class);
        Call<SyncMessageMaster> call = syncAPI.getOldMessages(token, body);
        call.enqueue(new Callback<SyncMessageMaster>() {
            @Override
            public void onResponse(Call<SyncMessageMaster> call, Response<SyncMessageMaster> response) {
                if (response.isSuccessful()){
                    addMessagesToDB(body.get(0).room_id,response.body().getMessages(), subscribers);
                }else{
                    mErrorEvent.setValue("An error occurred while syncing messages, Try again later");
                }
            }

            @Override
            public void onFailure(Call<SyncMessageMaster> call, Throwable t) {
                System.out.println(t.getMessage());
                readMessageFromDB(body.get(0).room_id);
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

    public void getLastMessageTimeForChat(String chatId){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                MessageDB messageDB = mDatabase.message_dao().getLastMessageForChat(chatId);
                if(messageDB == null){
                    mLastMessageTime.postValue("0");
                }else{
                    mLastMessageTime.postValue(messageDB.createdAt);
                }

                return null;
            }
        }.execute();
    }

    private void addMessagesToDB(String chatId,SyncMessage[] syncMessages, Subscriber[] subscribers){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                if(syncMessages.length != 0){
                    Message[] messages = new Message[syncMessages.length];
                    for (int i=0; i < syncMessages.length; i++){
                        messages[i] = syncMessages[i].toMessage(subscribers);
                    }
                    MessageDB[] messageDBS = MessageDB.fromMessagerray(messages);
                    try{
                        mDatabase.message_dao().insertMessages(messageDBS);
                    }catch (SQLiteConstraintException e){
                        SubscriberDB[] subscriberDBS = SubscriberDB.toSubscriberDBArray(subscribers);
                        mDatabase.subscriber_dao().insertSubscribers(subscriberDBS);
                        mDatabase.message_dao().insertMessages(messageDBS);
                    }
                }
                readMessageFromDB(chatId);
                return null;
            }
        }.execute();
    }

   private void readMessageFromDB(String chatId){
       new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mDatabase.message_dao().getLastMessageForChat(chatId);
                List<SubscriberDB> subscriberDBS  = mDatabase.subscriber_dao().getAllSubscribersOfChat(chatId);
                List<MessageDB> chatMessages = mDatabase.message_dao().getAllMessageForChat(chatId);
                List<Message> messages = getAllChatMessages(chatMessages, subscriberDBS);
                Message[] messagesArray = new Message[messages.size()];
                messages.toArray(messagesArray);
                SyncMessageMaster master = new SyncMessageMaster();
                SyncMessage[] syncMessages = new SyncMessage[messagesArray.length];
                for (int i = 0; i < syncMessages.length; i++){
                    syncMessages[i] = SyncMessage.fromMessage(messagesArray[i]);
                }
                master.setMessages(syncMessages);
                mMessages.postValue(master);
                return null;
            }
        }.execute();
    }

    private List<Message> getAllChatMessages(List<MessageDB> messageDBS, List<SubscriberDB> subscriberDBS){
        List<Message> messages = new ArrayList<>();
        for (MessageDB messageDB : messageDBS){
            Subscriber subscriber = getSubscriberForMessage(subscriberDBS, messageDB).toSubscriber();
            messages.add(messageDB.toMessage(subscriber));
        }
        return messages;
    }

    private SubscriberDB getSubscriberForMessage(List<SubscriberDB> subscribers, MessageDB chatMessage){
        for (SubscriberDB subscriberDB: subscribers){
            if(subscriberDB.id.equals(chatMessage.subScriberId)){
                return subscriberDB;
            }
        }
        return null;
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

    public void stopObserveMessageDelete(){
        Socket socket = GetSocket.getSocket();
        socket.off(SocketActions.OBSERVE_MSG_DELETED, onDelete);
    }

    public void stopObserveMessages(){
        Socket socket = GetSocket.getSocket();
        socket.off(SocketActions.OBSERVE_MESSAGE, onGetMessage);
    }

    private Emitter.Listener onGetMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            JSONObject jsonObject = (JSONObject) args[0];
            Log.d(TAG, "call: "+jsonObject.toString());
            Gson gson = new Gson();
            Message message = gson.fromJson(jsonObject.toString(), Message.class);
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    MessageDB messageDB = MessageDB.fromMessage(message);
                    try{
                        mDatabase.message_dao().insertMessage(messageDB);
                    }catch (SQLiteConstraintException e){
                        Subscriber subscriber = message.getUser();
                        mDatabase.subscriber_dao().insertSubscriber(SubscriberDB.fromSubscriber(subscriber));
                        mDatabase.message_dao().insertMessage(messageDB);
                    }

                    return null;
                }
            }.execute();
            mData.postValue(message);
        }
    };

    private Emitter.Listener onDelete =  new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject jsonObject = (JSONObject) args[0];
            Gson gson = new Gson();
            DeleteMessageResponse response = gson.fromJson(jsonObject.toString(), DeleteMessageResponse.class);
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    mDatabase.message_dao().updateIsDeleted(response.message_id,true);
                    return null;
                }
            }.execute();
            mDeleteResponse.postValue(response);
        }
    };

}
