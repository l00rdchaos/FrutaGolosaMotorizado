package com.frutagolosa.fgmoto.api;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface ApiVerificateCode {

    @FormUrlEncoded
    @POST("/validateCode.php")
    public void sendSms(

            @Field("code") String code,

            Callback<Response> callback
    );

}
