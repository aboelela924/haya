package com.bignerdranch.android.haya.viewModel;

import androidx.lifecycle.ViewModel;

import com.bignerdranch.android.haya.model.repo.networking.burnerCodeHistoryNetworking.BurnerCodeHistoryRepo;
import com.bignerdranch.android.haya.model.repo.networking.burnerCodeHistoryNetworking.HistoryModel;
import com.hadilq.liveevent.LiveEvent;

public class BurnerCodeHistoryViewModel extends ViewModel {

    public LiveEvent<HistoryModel>  mHistoryData;
    private BurnerCodeHistoryRepo mRepo;

    public BurnerCodeHistoryViewModel(){
        mRepo = BurnerCodeHistoryRepo.getInstance();
        mHistoryData = mRepo.mUnusedBurnerCodes;
    }

    public void getHistroyBurnerCodes(String userToken){
        mRepo.getBurnerCodeHistory(userToken);
    }

}
