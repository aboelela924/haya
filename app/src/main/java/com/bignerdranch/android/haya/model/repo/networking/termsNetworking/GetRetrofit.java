package com.bignerdranch.android.haya.model.repo.networking.termsNetworking;

import com.bignerdranch.android.haya.model.repo.BaseURL;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetRetrofit {
    private static Retrofit sRetrofit;
    private static final String TAG = "Retrofit";

    public static Retrofit getRetrofitInstance(){
        if(sRetrofit == null){
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor())
                    .build();

            sRetrofit = new Retrofit.Builder()
                    .baseUrl(BaseURL.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return sRetrofit;
    }

    private GetRetrofit(){

    }
}
