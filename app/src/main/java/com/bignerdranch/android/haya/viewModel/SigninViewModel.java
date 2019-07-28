package com.bignerdranch.android.haya.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bignerdranch.android.haya.model.repo.UserExample;
import com.bignerdranch.android.haya.model.repo.networking.signinNetworking.SigninBody;
import com.bignerdranch.android.haya.model.repo.networking.signinNetworking.SigninRepo;

public class SigninViewModel extends ViewModel {
    private SigninRepo mRepo;
    public MutableLiveData<UserExample> mData;

    public SigninViewModel(){
        mRepo = SigninRepo.getInstance();
        mData = mRepo.mData;
    }

    public void singUser(SigninBody body){
        mRepo.signinUser(body);
    }
}
