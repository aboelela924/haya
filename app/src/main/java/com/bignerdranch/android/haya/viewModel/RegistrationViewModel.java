package com.bignerdranch.android.haya.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bignerdranch.android.haya.model.repo.MessageResponseUpdatePassword;
import com.bignerdranch.android.haya.model.repo.UserExample;
import com.bignerdranch.android.haya.model.repo.networking.registration.PasswordBody;
import com.bignerdranch.android.haya.model.repo.networking.registration.RegistrationBody;
import com.bignerdranch.android.haya.model.repo.networking.registration.RegistrationRepo;
import com.bignerdranch.android.haya.model.repo.networking.registration.UpdatePasswordRepo;

public class RegistrationViewModel extends ViewModel {
    public MutableLiveData<UserExample> mData;
    public MutableLiveData<MessageResponseUpdatePassword> mMessageData;
    private RegistrationRepo mRepo;
    private UpdatePasswordRepo mPasswordRepo;

    public RegistrationViewModel(){
        mRepo = RegistrationRepo.getInstance();
        mPasswordRepo = UpdatePasswordRepo.getInstance();
        mData = mRepo.mData;
        mMessageData = mPasswordRepo.mData;
    }

    public void createUserId(RegistrationBody body){
        mRepo.createUser(body);
    }
    public void updateUserPassword(String token, PasswordBody body){
        mPasswordRepo.updatePassword(token, body);
    }
}
