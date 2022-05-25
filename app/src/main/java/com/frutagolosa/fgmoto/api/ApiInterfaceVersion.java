package com.frutagolosa.fgmoto.api;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface ApiInterfaceVersion {
  @FormUrlEncoded
  @POST("/version.php")
  public void evaluaversion(

          @Field("z") String z,
          @Field("id") String id,

          Callback<Response> callback
  );
}
