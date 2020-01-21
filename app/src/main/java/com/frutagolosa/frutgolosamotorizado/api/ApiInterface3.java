package com.frutagolosa.frutgolosamotorizado.api;

import com.frutagolosa.frutgolosamotorizado.model.Contact;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface3 {

  @GET("Pedidos_Fabricados.php")
  Call<List<Contact>> getContacts();
}
