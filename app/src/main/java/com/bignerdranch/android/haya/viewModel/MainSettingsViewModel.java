package com.bignerdranch.android.haya.viewModel;

import androidx.lifecycle.ViewModel;

import com.bignerdranch.android.haya.model.repo.networking.mainSettingsNetworking.ContactUsBody;
import com.bignerdranch.android.haya.model.repo.networking.mainSettingsNetworking.ContactUsResponse;
import com.bignerdranch.android.haya.model.repo.networking.mainSettingsNetworking.FAQSMaster;
import com.bignerdranch.android.haya.model.repo.networking.mainSettingsNetworking.MainSettingsRepo;
import com.bignerdranch.android.haya.model.repo.networking.mainSettingsNetworking.ToggleSecretResponse;
import com.hadilq.liveevent.LiveEvent;

public class MainSettingsViewModel extends ViewModel {
    public LiveEvent<ToggleSecretResponse> mLiveEvent;
    public LiveEvent<FAQSMaster> mFAQsData;
    public LiveEvent<ContactUsResponse> mContactData;
    public LiveEvent<String> mError;
    private MainSettingsRepo mRepo;

    public MainSettingsViewModel(){
        mRepo = MainSettingsRepo.getInstance();
        mLiveEvent = mRepo.mLiveEvent;
        mFAQsData = mRepo.mFAQSData;
        mContactData = mRepo.mContactData;
        mError = mRepo.mError;
    }

    public void getFAQs(){
        mRepo.getFAQs();
    }

    public void toggleSecret(String accessToken, String password){
        mRepo.toggleSecretState(accessToken, password);
    }

    public void contactUs(String name, String email, String mobileNumber, String message){
        ContactUsBody body = new ContactUsBody(name, email, mobileNumber, message);
        mRepo.contact(body);
    }
}
