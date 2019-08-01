package com.bignerdranch.android.haya.model.repo.networking.mainSettingsNetworking;

import com.bignerdranch.android.haya.model.repo.networking.GetRetrofit;
import com.hadilq.liveevent.LiveEvent;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainSettingsRepo {
    private static final MainSettingsRepo ourInstance = new MainSettingsRepo();

    public LiveEvent<ToggleSecretResponse> mLiveEvent = new LiveEvent<>();
    public LiveEvent<FAQSMaster> mFAQSData = new LiveEvent<>();
    public LiveEvent<ContactUsResponse> mContactData = new LiveEvent<>();

    public static MainSettingsRepo getInstance() {
        return ourInstance;
    }

    private MainSettingsRepo() {
    }

    public void toggleSecretState(String accessToken, String password){
        Retrofit retrofit = GetRetrofit.getRetrofitInstance();
        MainSettingsAPI api = retrofit.create(MainSettingsAPI.class);
        ToggleSecretBody body = new ToggleSecretBody(password);
        Call<ToggleSecretResponse> call = api.toggleSecret(accessToken,body);
        call.enqueue(new Callback<ToggleSecretResponse>() {
            @Override
            public void onResponse(Call<ToggleSecretResponse> call, Response<ToggleSecretResponse> response) {
                if(response.isSuccessful()){
                    mLiveEvent.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ToggleSecretResponse> call, Throwable t) {

            }
        });
    }

    public void getFAQs(){
        Retrofit retrofit = GetRetrofit.getRetrofitInstance();
        MainSettingsAPI api = retrofit.create(MainSettingsAPI.class);
        Call<FAQSMaster> call = api.getFAQS();
        call.enqueue(new Callback<FAQSMaster>() {
            @Override
            public void onResponse(Call<FAQSMaster> call, Response<FAQSMaster> response) {
                if(response.isSuccessful()){
                    mFAQSData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<FAQSMaster> call, Throwable t) {

            }
        });
    }

    public void contact(ContactUsBody body){
        Retrofit retrofit = GetRetrofit.getRetrofitInstance();
        MainSettingsAPI api = retrofit.create(MainSettingsAPI.class);
        Call<ContactUsResponse> call = api.contactUs(body);
        call.enqueue(new Callback<ContactUsResponse>() {
            @Override
            public void onResponse(Call<ContactUsResponse> call, Response<ContactUsResponse> response) {
                if(response.isSuccessful()){
                    mContactData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ContactUsResponse> call, Throwable t) {

            }
        });
    }

}
