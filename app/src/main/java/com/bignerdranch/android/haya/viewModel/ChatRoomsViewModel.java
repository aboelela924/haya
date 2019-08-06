package com.bignerdranch.android.haya.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bignerdranch.android.haya.model.repo.Room;
import com.bignerdranch.android.haya.model.repo.networking.SubscribedRoomsNetworking.SubscribedRoomsRepo;
import com.hadilq.liveevent.LiveEvent;

import java.util.List;
import java.util.Map;

public class ChatRoomsViewModel extends ViewModel {
    private LiveData<List<Room>> mRoomList;
    private SubscribedRoomsRepo mRoomsRepo;

    public ChatRoomsViewModel(){
        mRoomsRepo = SubscribedRoomsRepo.getInstance();
        mRoomList = mRoomsRepo.getRoomList();
    }

    public void sync(String accessToken){
        mRoomsRepo.sync(accessToken);
    }

    public void set1to1RoomList(){
        mRoomsRepo.setRoomList("1");
    }
    public void setGroupRoomList(){
        mRoomsRepo.setRoomList("2");
    }
    public void setPrivateRoomList(){
        mRoomsRepo.setRoomList("3");
    }


    public void observeNewChatCreated(){
        mRoomsRepo.observeNewChatCreated();
    }
    public void stopNewChatObserver(){
        mRoomsRepo.stopNewMessageObserver();
    }
    public void observewNewMessage(){
        mRoomsRepo.observeNewMessage();
    }
    //Called from the mainactivity whenever needed
    public LiveData<List<Room>> getRoomList(){
        return mRoomList;
    }
}
