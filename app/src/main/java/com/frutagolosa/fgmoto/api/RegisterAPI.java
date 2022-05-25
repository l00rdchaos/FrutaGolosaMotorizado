package com.frutagolosa.fgmoto.api;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface RegisterAPI {


  @FormUrlEncoded
  @POST("/insertmotorizado.php")
  public void inserCliente(
          @Field("telefono") String telefono,
          @Field("nombre") String nombre,
          @Field("correo") String correo,
          @Field("foto") String foto,
          @Field("ciudad") String ciudad,
          @Field("codigo") String codigo,
          Callback<Response> callback


  );



}
