package com.frutagolosa.fgmoto.api;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface ApiSMS {

    @FormUrlEncoded
    @POST("/sendSms.php")
    public void sendSms(

            @Field("t") String t,

            Callback<Response> callback
    );

}
