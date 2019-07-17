package com.bignerdranch.android.haya.model.repo.networking.termsNetworking;

import com.bignerdranch.android.haya.model.repo.TermsExample;

import retrofit2.Call;
import retrofit2.http.GET;

public interface HayaAPITerms {
    @GET("api/v1/term/")
    Call<TermsExample> loadTerms();
}
