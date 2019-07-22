package com.bignerdranch.android.haya.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bignerdranch.android.haya.model.repo.Room;
import com.bignerdranch.android.haya.model.repo.RoomExample;
import com.bignerdranch.android.haya.model.repo.Subscriber;
import com.bignerdranch.android.haya.model.repo.networking.joinRoomNetworking.JoinRoomRepo;

public class JoinRoomViewModel extends ViewModel {
    public MutableLiveData<Room> mRoomData;
    public MutableLiveData<Subscriber> mSubscriberData;
    private JoinRoomRepo mRepo;

    public JoinRoomViewModel(){
        mRepo = JoinRoomRepo.getRoomRepoInstance();
        mSubscriberData = mRepo.mSubscriberData;
        mRoomData = mRepo.mData;
    }

    public void joinRoom(String nickname, String roomToken){
        mRepo.joinRoom(nickname, roomToken);
    }

    public void updateNickname(String nickname, String roomId){
        mRepo.changeNickname(nickname, roomId);
    }
}
