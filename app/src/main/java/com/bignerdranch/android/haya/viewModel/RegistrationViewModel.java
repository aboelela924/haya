package com.bignerdranch.android.haya.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bignerdranch.android.haya.model.repo.UserExample;
import com.bignerdranch.android.haya.model.repo.networking.registration.RegistrationBody;
import com.bignerdranch.android.haya.model.repo.networking.registration.RegistrationRepo;

public class RegistrationViewModel extends ViewModel {
    public MutableLiveData<UserExample> mData;
    private RegistrationRepo mRepo;

    public RegistrationViewModel(){
        mRepo = RegistrationRepo.getInstance();
        mData = mRepo.mData;
    }

    public void createUserId(RegistrationBody body){
        mRepo.createUser(body);
    }
}
