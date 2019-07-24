package com.bignerdranch.android.haya.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bignerdranch.android.haya.model.repo.Message;
import com.bignerdranch.android.haya.model.repo.networking.chatNetworking.ChatNetworkingRepo;
import com.bignerdranch.android.haya.model.repo.networking.chatNetworking.DeleteMessageResponse;
import com.bignerdranch.android.haya.model.repo.networking.chatNetworking.SyncBody;
import com.bignerdranch.android.haya.model.repo.networking.chatNetworking.SyncMessageMaster;
import com.bignerdranch.android.haya.model.repo.networking.joinRoomNetworking.JoinRoomRepo;

import java.util.List;

public class ChatViewModel extends ViewModel {
    private ChatNetworkingRepo mRepo;
    public MutableLiveData<Message> mData;
    public MutableLiveData<SyncMessageMaster> mMessages;
    public MutableLiveData<DeleteMessageResponse> mDeleteResponse;

    public ChatViewModel(){
        mRepo = ChatNetworkingRepo.getInstance();
        mData = mRepo.mData;
        mMessages = mRepo.mMessages;
        mDeleteResponse = mRepo.mDeleteResponse;
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

    public void syncMessages(String token,List<SyncBody> body){
        mRepo.syncMessages(token,body);
    }

    public void observeMessages(){
        mRepo.observeMessages();
    }

    public void observeMessageDelete(){
        mRepo.observeMessageDelete();
    }
}
