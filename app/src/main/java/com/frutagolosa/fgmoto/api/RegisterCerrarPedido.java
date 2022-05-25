package com.frutagolosa.fgmoto.api;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface RegisterCerrarPedido {

  @FormUrlEncoded
  @POST("/CambioDeEstado.php")
  public void inseruser(
          @Field("a") String a,
          @Field("b") String b,
          @Field("id") String id,
          Callback<Response> callback


  );

}
