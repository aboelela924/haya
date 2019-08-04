package com.bignerdranch.android.haya.viewModel;

import androidx.lifecycle.ViewModel;

import com.bignerdranch.android.haya.model.repo.networking.burnerCodeHistoryNetworking.BurnerCodeHistoryRepo;
import com.bignerdranch.android.haya.model.repo.networking.burnerCodeHistoryNetworking.HistoryModel;
import com.hadilq.liveevent.LiveEvent;

public class BurnerCodeHistoryViewModel extends ViewModel {

    public LiveEvent<HistoryModel>  mHistoryData;
    public LiveEvent<String> mErrorData;
    public LiveEvent<String> mDeletedRoomId;
    private BurnerCodeHistoryRepo mRepo;

    public BurnerCodeHistoryViewModel(){
        mRepo = BurnerCodeHistoryRepo.getInstance();
        mHistoryData = mRepo.mUnusedBurnerCodes;
        mErrorData = mRepo.mError;
        mDeletedRoomId = mRepo.mDeletedRoomIdEvent;
    }

    public void getHistroyBurnerCodes(String userToken){
        mRepo.getBurnerCodeHistory(userToken);
    }
    public void deleteRoom(String userToken, String roomId){
        mRepo.deleteRoom(userToken, roomId);
    }

}
