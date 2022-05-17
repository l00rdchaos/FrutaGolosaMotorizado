package com.frutagolosa.fgmoto.api;

import com.frutagolosa.fgmoto.model.Contact;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;



public interface ApiInterface4 {



  @GET
  Call<List<Contact>>

  getContacts(@Url String url);


}