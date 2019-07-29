package com.bignerdranch.android.haya.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bignerdranch.android.haya.model.repo.DeleteResult;
import com.bignerdranch.android.haya.model.repo.Room;
import com.bignerdranch.android.haya.model.repo.Subscriber;
import com.bignerdranch.android.haya.model.repo.SubscriptionExample;
import com.bignerdranch.android.haya.model.repo.networking.chatSettingsNetworking.ChatSettingsRepo;

public class ChatSettingsViewModel extends ViewModel {
    public MutableLiveData<SubscriptionExample> mSubscriptionData;
    public MutableLiveData<DeleteResult> mDeleteData;
    public MutableLiveData<Room> mRoomData;
    public MutableLiveData<Boolean> mBurnerCodeStatus;
    public MutableLiveData<Subscriber> mSubscriberData;
    private ChatSettingsRepo mRepo;

    public ChatSettingsViewModel(){
        mRepo = ChatSettingsRepo.getInstance();
        mSubscriptionData = mRepo.mSubscriptionData;
        mDeleteData = mRepo.mDeleteData;
        mRoomData = mRepo.mRoomData;
        mBurnerCodeStatus = mRepo.mBurnerCodeStatus;
        mSubscriberData = mRepo.mSubscriberData;
    }

    public void updateRoomCustomName(String accessToken, String roomId, String name){
        mRepo.updateChatCustomName(accessToken, roomId, name);
    }

    public void toggleChat(String accessToken, String roomId){
        mRepo.toggleChat(accessToken, roomId);
    }

    public void toggleBurnerCode(String accessToken, String roomToken){
        mRepo.toggleBurnerCodeStatus(accessToken, roomToken);
    }

    public void deleteAll(String roomId){
        mRepo.deleteAllMessages(roomId);
    }

    public void deleteAddAndLeave(String roomId){
        mRepo.disconnect(roomId);
    }

    public void observeUserJoin(){
        mRepo.observeUsersJoin();
    }

    public void observeUserLeave(){
        mRepo.observeUserLeave();
    }

    public void updateNickName(String roomId, String newNickname){
        mRepo.changeNickname(newNickname,roomId);
    }
}
