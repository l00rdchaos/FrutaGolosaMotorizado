package com.frutagolosa.frutgolosamotorizado.api;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface RegisterApiMotorizado {

  @FormUrlEncoded
  @POST("/UpdateMotorizado.php")
  public void inserusera(
          @Field("a") String a,
          @Field("b") String b,
          @Field("c") String c,
          Callback<Response> callback


  );

}
