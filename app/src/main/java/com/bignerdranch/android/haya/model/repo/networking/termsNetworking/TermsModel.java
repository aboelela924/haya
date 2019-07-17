package com.bignerdranch.android.haya.model.repo.networking.termsNetworking;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.bignerdranch.android.haya.model.repo.BaseURL;
import com.bignerdranch.android.haya.model.repo.TermsExample;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TermsModel implements Callback<TermsExample> {

    private static TermsModel sTermsModel = null;
    public MutableLiveData<TermsExample> mData =new MutableLiveData<>();

    public static TermsModel getTermsModel(){
        if (sTermsModel == null){
            sTermsModel = new TermsModel();
        }
        return sTermsModel;
    }

    private TermsModel(){

    }

    public void loadTerms(){

        Retrofit retrofit = GetRetrofit.getRetrofitInstance();

        HayaAPITerms termsAPI = retrofit.create(HayaAPITerms.class);
        Call<TermsExample> call = termsAPI.loadTerms();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<TermsExample> call, Response<TermsExample> response) {
        if(response.isSuccessful()){
            mData.setValue(response.body());
        }
    }

    @Override
    public void onFailure(Call<TermsExample> call, Throwable t) {
        String message = t.getMessage();
        Log.d("Calm Down  1989", "onFailure: "+ t.getMessage());
    }
}
