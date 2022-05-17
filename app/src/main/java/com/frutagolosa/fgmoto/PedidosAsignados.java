package com.frutagolosa.fgmoto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.frutagolosa.fgmoto.adapter.RecyclerAdapter;
import com.frutagolosa.fgmoto.api.ApiClient;

import com.frutagolosa.fgmoto.api.ApiInterface4;
import com.frutagolosa.fgmoto.model.Contact;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class PedidosAsignados extends AppCompatActivity {
  private RecyclerView recyclerView;
  private RecyclerView.LayoutManager layoutManager;
  private RecyclerAdapter adapter;
  private List<Contact> contacts;
  private ApiInterface4 apiInterface;
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
  public static final String GloboA="id24a" ;
  public static final String SectorA="id22" ;
  public static final String CoordenadaA="id23" ;
  public static final String imgaentA="sid23asdasd" ;
  public static final String Ciudad="sid23asdasdjh" ;
  int LAUNCH_3_ACTIVITY = 3;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_pedidos_asignados);

    setTitle("Asignados a mi");

    cargar();




  }
  private void cargar(){     recyclerView = findViewById(R.id.recyclerView4);
    layoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setHasFixedSize(true);

    apiInterface = ApiClient.getApiClient().create(ApiInterface4.class);
    SharedPreferences preferences=getSharedPreferences("login", Context.MODE_PRIVATE);
    String nombreus=preferences.getString("nombreus","Registrese");
    String mailus=preferences.getString("mailus","No");
    String telefonous=preferences.getString("telefonous","No");
    Call<List<Contact>> call = apiInterface.getContacts("https://frutagolosa.com/FrutaGolosaApp/PedidoAsignado.php?nombre="+nombreus);

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
            String imgaent=contacts.get(recyclerView.getChildAdapterPosition(view)).getImgaent();
            String ciudad=contacts.get(recyclerView.getChildAdapterPosition(view)).getCiudad();

            Intent c= new Intent(PedidosAsignados.this, DetallePedido.class);
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
            c.putExtra(imgaentA,imgaent);
            c.putExtra(Ciudad,imgaent);
            startActivityForResult(c,LAUNCH_3_ACTIVITY);

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
      cargar();
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

      if(resultCode == Activity.RESULT_OK){
        cargar();
      }


  } //o
}