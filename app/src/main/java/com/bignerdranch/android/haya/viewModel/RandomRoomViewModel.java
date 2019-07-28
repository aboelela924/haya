package com.bignerdranch.android.haya.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bignerdranch.android.haya.model.repo.Room;
import com.bignerdranch.android.haya.model.repo.Subscriber;
import com.bignerdranch.android.haya.model.repo.networking.joinRandomRoomNetworking.RandomRoomRepo;

public class RandomRoomViewModel extends ViewModel {
    public MutableLiveData<Room> mRoomData;
    private RandomRoomRepo mJoinRandomRepo;

    public RandomRoomViewModel(){
        mJoinRandomRepo = RandomRoomRepo.getInstance();

        mRoomData = mJoinRandomRepo.mRoomData;
    }

    public void joinQueue(){
        mJoinRandomRepo.joinQueue();
    }

    public void leaveQueue()
    {
        //Leave.
    }
}
