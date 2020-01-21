package com.frutagolosa.frutgolosamotorizado.api;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;

public interface RegisterFotoMotorizado {
  public void inseruser(
          @Field("a") String a,
          @Field("b") String b,
          Callback<Response> callback


  );

}
