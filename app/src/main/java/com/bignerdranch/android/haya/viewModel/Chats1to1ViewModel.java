package com.bignerdranch.android.haya.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bignerdranch.android.haya.CurrentUser;
import com.bignerdranch.android.haya.model.repo.Room;
import com.bignerdranch.android.haya.model.repo.networking.SubscribedRoomsNetworking.SubscribedRoomsRepo;
import com.bignerdranch.android.haya.model.repo.networking.chatNetworking.ChatNetworkingRepo;
import com.bignerdranch.android.haya.model.repo.networking.chatNetworking.SyncBody;
import com.bignerdranch.android.haya.model.repo.networking.signinNetworking.SigninRepo;
import com.bignerdranch.android.haya.model.repo.roomDatabase.classes.Chat;

import java.util.ArrayList;
import java.util.List;

public class Chats1to1ViewModel extends ViewModel {
    private MutableLiveData<List<Room>>  mRoomList;
    private SubscribedRoomsRepo mRoomsRepo;

    public Chats1to1ViewModel(){
        mRoomsRepo = SubscribedRoomsRepo.getInstance();
        mRoomList = mRoomsRepo.getRoomList();
    }

    public void setmRoomList(){
        mRoomsRepo.setRoomList();
    }

    //Called from the mainactivity whenever needed
    public LiveData<List<Room>> getRoomList(){
        return mRoomList;
    }
}
