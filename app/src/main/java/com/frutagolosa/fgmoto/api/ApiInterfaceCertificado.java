package com.frutagolosa.fgmoto.api;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface ApiInterfaceCertificado {
  @FormUrlEncoded
  @POST("/certificadomotorizado.php")
  public void comprarver(

          @Field("c") String c,
          @Field("t") String t,


          Callback<Response> callback
  );
}
