package com.frutagolosa.fgmoto;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.frutagolosa.fgmoto.api.ApiInterface2;
import com.frutagolosa.fgmoto.model.Contact;
import com.frutagolosa.fgmoto.api.ApiClient;

import com.frutagolosa.fgmoto.adapter.RecyclerAdapter;
import com.google.android.gms.common.data.DataHolder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PedidosFabricados extends AppCompatActivity {
  private RecyclerView recyclerView;
  private RecyclerView.LayoutManager layoutManager;
  private RecyclerAdapter adapter;
  private List<Contact> contacts;
  private List<Contact> contactssh=new ArrayList<Contact>();
  private ApiInterface2 apiInterface;
  public static final String IdPEDIDOA="id0" ;
  public static final String IdArregloA="id2" ;
  public static final String FechaPedidoA="id3" ;
  public static final String NombreClientesA="id4" ;
  public static final String CorreoClienteA="id5" ;
  public static final String TelefonoClienteA="id6" ;
  public static final String NombreqRecibeA="id7" ;
  public static final String FechaQrecibeA="id8" ;
  public static final String TelefonoQrecibeA="id9" ;
  public static final String FranjaHorariaA="id10" ;
  public static final String CallePrincipalA="id11" ;
  public static final String CalleSecundariaA="id12" ;
  public static final String CasaempresaedifcioA="id13" ;
  public static final String referenciaA="id14" ;
  public static final String PortadaTarjetaA="id15" ;
  public static final String TextoTarjetaA="id16" ;
  public static final String EspecificacionA="id17" ;
  public static final String EstadoA="id18" ;
  public static final String KeyAccountA="id19" ;
  public static final String ParroquiaA="id20" ;
  public static final String Costo_EnvioA="id21" ;
  public static final String GloboA="id24" ;
  public static final String SectorA="id22" ;
  public static final String CoordenadaA="id23" ;
  int LAUNCH_SECOND_ACTIVITY = 1;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_pedidos_fabricados);
    setTitle("Fabricados");

    EditText editTextSerch=findViewById(R.id.search);
    editTextSerch.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        try {
          filter(charSequence.toString().toLowerCase());

          Toast.makeText(PedidosFabricados.this, charSequence.length(), Toast.LENGTH_SHORT).show();


        }catch (Exception e){


        }
      }

      @Override
      public void afterTextChanged(Editable editable) {

      }
    });
    cargare();



  }

  void filter(String text){
    List<Contact> temp = new ArrayList();
    for(Contact d: contacts){
      //or use .equal(text) with you want equal match
      //use .toLowerCase() for better matches
      if(d.getNombre_qRecibe().toLowerCase().contains(text)){
        temp.add(d);
      }



    }
    //update recyclerview
    adapter.updateList(temp);
  }
  private void cargare(){
    SharedPreferences preferences=getSharedPreferences("login", Context.MODE_PRIVATE);
    final String nombreus=preferences.getString("nombreus","Registrese");
    String c=preferences.getString("mailus","No");
    String t=preferences.getString("telefonous","No");
    String ciudad=preferences.getString("ciudad","No");

    recyclerView = findViewById(R.id.recyclerViewt);
    layoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(layoutManager);

    apiInterface = ApiClient.getApiClient().create(ApiInterface2.class);

    Call<List<Contact>> call = apiInterface.getContacts("https://frutagolosa.com/FrutaGolosaApp/AdmPedidosApp.php?c="+ciudad+"&e=Fabricado");

    call.enqueue(new Callback<List<Contact>>() {
      @Override
      public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
        contacts = response.body();
        adapter = new RecyclerAdapter(contacts);
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

            String ID_PEDIDO1=contacts.get(recyclerView.getChildAdapterPosition(view)).getID_PEDIDO();
            String Idarreglot=contacts.get(recyclerView.getChildAdapterPosition(view)).getNombre_arreglo();
            String FechaPedidot=contacts.get(recyclerView.getChildAdapterPosition(view)).getFecha_pedido();
            String NombreClientet=contacts.get(recyclerView.getChildAdapterPosition(view)).getNombreCliente();
            String Correo_Clientet=contacts.get(recyclerView.getChildAdapterPosition(view)).getCorreo_cliente();
            String Telefono_Clientet=contacts.get(recyclerView.getChildAdapterPosition(view)).getTelefono_cliente();
            String Nombre_qrecibet=contacts.get(recyclerView.getChildAdapterPosition(view)).getNombre_qRecibe();
            String Telefono_qrecibet=contacts.get(recyclerView.getChildAdapterPosition(view)).getTelefono_qRecibe();
            String Fecha_Qrecibet=contacts.get(recyclerView.getChildAdapterPosition(view)).getFechaEntrega();

            String Franja_Horariat=contacts.get(recyclerView.getChildAdapterPosition(view)).getFranja_horaria();
            String Calle_Principalt=contacts.get(recyclerView.getChildAdapterPosition(view)).getCalle_principal();
            String Calle_Secundariat=contacts.get(recyclerView.getChildAdapterPosition(view)).getCalle_secundaria();
            String casaempresaedificiot=contacts.get(recyclerView.getChildAdapterPosition(view)).getCasaempresaedificio();
            String referenciat=contacts.get(recyclerView.getChildAdapterPosition(view)).getReferencia();
            String portada_tarjetat=contacts.get(recyclerView.getChildAdapterPosition(view)).getPortada_tarjeta();
            String texto_tarjetat=contacts.get(recyclerView.getChildAdapterPosition(view)).getTexto_tarjeta();
            String especificaciont=contacts.get(recyclerView.getChildAdapterPosition(view)).getEspecificacion();
            String estadot=contacts.get(recyclerView.getChildAdapterPosition(view)).getEstado();
            String keyaccountt=contacts.get(recyclerView.getChildAdapterPosition(view)).getKeyaccount();
            String globot=contacts.get(recyclerView.getChildAdapterPosition(view)).getGlobo();
            String sectort=contacts.get(recyclerView.getChildAdapterPosition(view)).getSector();
            String coordenadast=contacts.get(recyclerView.getChildAdapterPosition(view)).getCoordenadas();

            Intent c= new Intent(PedidosFabricados.this, DetallePedido.class);
            c.putExtra(IdPEDIDOA,ID_PEDIDO1);
            c.putExtra(FechaPedidoA,FechaPedidot);
            c.putExtra(IdArregloA,Idarreglot);
            c.putExtra(NombreClientesA,NombreClientet);
            c.putExtra(CorreoClienteA,Correo_Clientet);
            c.putExtra(TelefonoClienteA,Telefono_Clientet);
            c.putExtra(NombreqRecibeA,Nombre_qrecibet);
            c.putExtra(TelefonoQrecibeA,Telefono_qrecibet);
            c.putExtra(FechaQrecibeA,Fecha_Qrecibet);

            c.putExtra(FranjaHorariaA,Franja_Horariat);
            c.putExtra(CallePrincipalA,Calle_Principalt);
            c.putExtra(CalleSecundariaA,Calle_Secundariat);
            c.putExtra(CasaempresaedifcioA,casaempresaedificiot);
            c.putExtra(referenciaA,referenciat);
            c.putExtra(PortadaTarjetaA,portada_tarjetat);
            c.putExtra(TextoTarjetaA,texto_tarjetat);
            c.putExtra(EspecificacionA,especificaciont);

            c.putExtra(EstadoA,estadot);
            c.putExtra(KeyAccountA,keyaccountt);
            c.putExtra(GloboA,globot);
            c.putExtra(SectorA,sectort);
            c.putExtra(CoordenadaA,coordenadast);


            startActivityForResult(c,LAUNCH_SECOND_ACTIVITY);

          }
        });

      }



      @Override
      public void onFailure(Call<List<Contact>> call, Throwable t) {

      }
    });




  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.refrescar, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        return true;
    }
    //noinspection SimplifiableIfStatement
    if (id == R.id.refres) {
      cargare();
      Toast.makeText(this, "Se ha actualizado", Toast.LENGTH_SHORT).show();
    }

    if (id == R.id.atras) {
      finish();
    }
    return true;
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == LAUNCH_SECOND_ACTIVITY) {
      if(resultCode == Activity.RESULT_OK){
        cargare();
      }
      if (resultCode == Activity.RESULT_CANCELED) {
      // Toast.makeText(this, "Ningun cambio", Toast.LENGTH_SHORT).show();
      }
    }
  } //o

}
