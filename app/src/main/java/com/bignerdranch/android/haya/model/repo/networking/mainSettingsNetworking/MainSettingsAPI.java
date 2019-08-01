package com.bignerdranch.android.haya.model.repo.networking.mainSettingsNetworking;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface MainSettingsAPI {
    @PUT("api/v1/auth/toggle-secret-inbox")
    Call<ToggleSecretResponse> toggleSecret(@Header("x-access-token") String accessToken,
                                            @Body ToggleSecretBody body);

    @GET("api/v1/faq/index")
    Call<FAQSMaster> getFAQS();

    @POST("api/v1/contact-us/store")
    Call<ContactUsResponse> contactUs(@Body ContactUsBody body);
}
