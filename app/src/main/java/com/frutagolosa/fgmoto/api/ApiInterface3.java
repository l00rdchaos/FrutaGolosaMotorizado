package com.frutagolosa.fgmoto.api;

import com.frutagolosa.fgmoto.model.Contact;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface3 {

  @GET("Pedidos_Fabricados.php")
  Call<List<Contact>> getContacts();
}
