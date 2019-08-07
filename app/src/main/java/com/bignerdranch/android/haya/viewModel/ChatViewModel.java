package com.bignerdranch.android.haya.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bignerdranch.android.haya.model.repo.DeleteResult;
import com.bignerdranch.android.haya.model.repo.Message;
import com.bignerdranch.android.haya.model.repo.Room;
import com.bignerdranch.android.haya.model.repo.Subscriber;
import com.bignerdranch.android.haya.model.repo.networking.chatNetworking.ChatNetworkingRepo;
import com.bignerdranch.android.haya.model.repo.networking.chatNetworking.DeleteMessageResponse;
import com.bignerdranch.android.haya.model.repo.networking.chatNetworking.SyncBody;
import com.bignerdranch.android.haya.model.repo.networking.chatNetworking.SyncMessageMaster;
import com.bignerdranch.android.haya.model.repo.networking.chatSettingsNetworking.ChatSettingsRepo;
import com.bignerdranch.android.haya.model.repo.networking.joinRoomNetworking.JoinRoomRepo;
import com.hadilq.liveevent.LiveEvent;

import java.util.List;

public class ChatViewModel extends ViewModel {
    private ChatNetworkingRepo mRepo;
    private ChatSettingsRepo mSettingsRepo;
    public MutableLiveData<DeleteResult> mDeleteData;
    public MutableLiveData<Room> mRoomData;
    public MutableLiveData<Message> mData;
    public MutableLiveData<SyncMessageMaster> mMessages;
    public MutableLiveData<DeleteMessageResponse> mDeleteResponse;
    public LiveEvent<String> mLastMessageTime;

    public ChatViewModel(){
        mRepo = ChatNetworkingRepo.getInstance();
        mSettingsRepo = ChatSettingsRepo.getInstance();
        mData = mRepo.mData;
        mMessages = mRepo.mMessages;
        mDeleteResponse = mRepo.mDeleteResponse;
        mLastMessageTime = mRepo.mLastMessageTime;
        mDeleteData = mSettingsRepo.mDeleteData;
        mRoomData = mSettingsRepo.mRoomData;
    }

    public void observeSystemActions(){
        mSettingsRepo.observeUsersJoin();
        mSettingsRepo.observeUserLeave();
        mSettingsRepo.observeAllMessagesDeleted();
    }

    public void sendUserMessage(String message, String roomId){
        mRepo.sendMessage(message,roomId,0);
    }

    public void sendSystemMessage(String message, String roomId){
        mRepo.sendMessage(message,roomId,1);
    }

    public void deleteMessage(String roomId, String messageId){
        mRepo.deleteMessage(roomId, messageId);
    }

    public void getLastMessageTime(String chatId){
        mRepo.getLastMessageTimeForChat(chatId);
    }

    public void syncMessages(String token, List<SyncBody> body, Subscriber[] subscribers){
        mRepo.syncMessages(token, body,subscribers);
    }

    public void observeMessages(String roomId){
        mRepo.observeMessages(roomId);
    }

    public void observeMessageDelete(){
        mRepo.observeMessageDelete();
    }
    public void stopObserveMessages(){
        mRepo.stopObserveMessages();
    }

    public void stopObserveMessageDelete(){
        mRepo.stopObserveMessageDelete();
    }
}
