package com.bignerdranch.android.haya.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bignerdranch.android.haya.model.repo.RoomExample;
import com.bignerdranch.android.haya.model.repo.networking.createRoomNetworking.CreateRoomRepo;
import com.bignerdranch.android.haya.model.repo.networking.createRoomNetworking.RoomBody;
import com.bignerdranch.android.haya.model.repo.networking.createRoomNetworking.UpdateRoomBody;

public class CreateRoomViewModel extends ViewModel {
    public MutableLiveData<RoomExample> mRoomData;
    private CreateRoomRepo mCreateRoomRepo;

    public CreateRoomViewModel() {
        mCreateRoomRepo = CreateRoomRepo.getInstance();
        mRoomData = mCreateRoomRepo.mData;
    }

    public void createSingleRoom(String token, String roomName){
        RoomBody body = new RoomBody(roomName, 1);
        mCreateRoomRepo.createRoom(token,body);
    }

    public void createGroupRoom(String token, String roomName){
        RoomBody body = new RoomBody(roomName, 2);
        mCreateRoomRepo.createRoom(token,body);
    }

    public void updateInfo(String token,String roomId, String roomName, String userName){
        UpdateRoomBody body = new UpdateRoomBody(roomName, userName);
        mCreateRoomRepo.updateInfo(token,roomId, body);
    }
}
