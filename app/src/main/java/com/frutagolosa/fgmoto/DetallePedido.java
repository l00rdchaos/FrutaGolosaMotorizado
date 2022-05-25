package com.frutagolosa.fgmoto;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.frutagolosa.fgmoto.api.RegisterApi2;
import com.frutagolosa.fgmoto.api.RegisterApiMotorizado;
import com.frutagolosa.fgmoto.api.RegisterCerrarPedido;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DetallePedido extends AppCompatActivity {
  public static final String ROOT_URL="https://frutagolosa.com/FrutaGolosaApp";
  Bitmap bitmap;
  int PICK_IMAGE_REQUEST = 1;
  String UPLOAD_URL = "https://frutagolosa.com/FrutaGolosaApp/Upload.php";
  String KEY_IMAGE = "foto";
  String KEY_NOMBRE = "nombre";
  private final int xf=(int)(Math.random()*10000);
  private final String xf2=String.valueOf(xf);
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detalle_pedido);

    final String IDPEDIDO=getIntent().getStringExtra(PedidosAsignados.IdPEDIDOA);
    final String idarrelgo=getIntent().getStringExtra(PedidosAsignados.IdArregloA);
    final String  k=idarrelgo.replace(" ","").toLowerCase().trim();
    final String Fecha_Pedido=getIntent().getStringExtra(PedidosAsignados.FechaPedidoA);
    final String Nombre_Cliente=getIntent().getStringExtra(PedidosAsignados.NombreClientesA);
    final String Telefono_Cliente=getIntent().getStringExtra(PedidosAsignados.TelefonoClienteA);
    final String Correo_Cliente=getIntent().getStringExtra(PedidosAsignados.CorreoClienteA);
    final  String Nombre_qRecibe=getIntent().getStringExtra(PedidosAsignados.NombreqRecibeA);
    final String Fecha_qRecibe=getIntent().getStringExtra(PedidosAsignados.FechaQrecibeA);
    final String Telefono_qrecibe=getIntent().getStringExtra(PedidosAsignados.TelefonoQrecibeA);
    final String Franaja_horara=getIntent().getStringExtra(PedidosAsignados.FranjaHorariaA);
    final String Calle_principal=getIntent().getStringExtra(PedidosAsignados.CallePrincipalA);
    final String Calle_secundaria=getIntent().getStringExtra(PedidosAsignados.CalleSecundariaA);
    final String Casaempresaedificio=getIntent().getStringExtra(PedidosAsignados.CasaempresaedifcioA);
    final String referencia=getIntent().getStringExtra(PedidosAsignados.referenciaA);
    final  String Portada_tarjeta=getIntent().getStringExtra(PedidosAsignados.PortadaTarjetaA);
    final String Texto_tarjeta=getIntent().getStringExtra(PedidosAsignados.TextoTarjetaA);
    final String Especificacion=getIntent().getStringExtra(PedidosAsignados.EspecificacionA);
    final String Estado=getIntent().getStringExtra(PedidosAsignados.EstadoA);
    final String Keyaccount=getIntent().getStringExtra(PedidosAsignados.KeyAccountA);
    final String Parroquia=getIntent().getStringExtra(PedidosAsignados.ParroquiaA);
    final String Costo_envio=getIntent().getStringExtra(PedidosAsignados.Costo_EnvioA);
    final String Globo=getIntent().getStringExtra(PedidosAsignados.GloboA);
    final String sector=getIntent().getStringExtra(PedidosAsignados.SectorA);
    final  String coordenadas=getIntent().getStringExtra(PedidosAsignados.CoordenadaA);
    final  String imgaent=getIntent().getStringExtra(PedidosAsignados.imgaentA);
    final  String ciudad=getIntent().getStringExtra(PedidosAsignados.Ciudad);
    final TextView IDPEDIDOTXTt=(TextView) findViewById(R.id.IDPEDIDOTXT2);
    final TextView Fecha_Pedidotxt=(TextView) findViewById(R.id.FechaPedidoTxt2);
    final TextView Nombre_Clientetxt=(TextView) findViewById(R.id.Nombreclientetxt2);
    final TextView Telefono_Clientetxt=(TextView) findViewById(R.id.TelefonoClienteTXT2);
    final TextView Correo_Clientetxt=(TextView) findViewById(R.id.CorreoCienteTXT2);
    final  TextView Nombre_qrecibetxt=(TextView) findViewById(R.id.vNombreQrecibetxt2);
    final TextView Fecha_qRecibetxt=(TextView) findViewById(R.id.vFechaEnt);
    final  TextView idArreglotxt=(TextView) findViewById(R.id.vArreglo);
    final TextView TelefoqRecibetxt=(TextView) findViewById(R.id.vTelefonoQrecibetxt22);


    final TextView FranjaHorariatxt=(TextView) findViewById(R.id.vFranjaHorariatxt2);
    final TextView CallePrincipaltxt=(TextView) findViewById(R.id.vCallePrincipaltxt2);
    final TextView CalleSecundariattxt=(TextView) findViewById(R.id.vCalleSecunadariatxt2);
    final TextView CasaempresaEdifciotxt=(TextView) findViewById(R.id.vCasaEmpresaEdifciotxt2);
    final TextView Referenciatxt=(TextView) findViewById(R.id.vReferenciatxt2);
    final TextView Portadatarjetatxt=(TextView) findViewById(R.id.vPortadaTarjetatxt2);
    final TextView Textotarjetatxt=(TextView) findViewById(R.id.vtextoTarjetatxt2);
    final TextView Especificaciontxt=(TextView) findViewById(R.id.vEspecificaciontxt2);
    final TextView KeyAccounttxt=(TextView) findViewById(R.id.vkeyaccounttxt2);
    final TextView Parroquiatxt=(TextView) findViewById(R.id.vparroquiatxt2);
    final TextView Costoenviotxt=(TextView) findViewById(R.id.vcostoenviotxt2);
    final TextView globotxt=(TextView) findViewById(R.id.vglobotxt2);
    final TextView sectortxt=(TextView) findViewById(R.id.vSectortxt2);
    final  TextView coordenadatxt=(TextView) findViewById(R.id.Coordenadastxt2);
    final  TextView textarregl=(TextView) findViewById(R.id.textviewfotoli);

    final ImageView ImageArreglo=(ImageView) findViewById(R.id.imageviewMOTO);

    ImageView ArregloL=(ImageView) findViewById(R.id.imgFotoEnt);
    Button btnMapa=findViewById(R.id.btnVerMapa);
    Button btnSoliRut=findViewById(R.id.btnSoliRuta);
    Button btnCancelRut=findViewById(R.id.btnCancelarRuta);
    Button btnWhatsapp=findViewById(R.id.btnEscWhat);
    Button btnEntregCer=findViewById(R.id.btnEntregCerrar);
    Button btnLLamaClient=findViewById(R.id.btnLlamaClient);

    btnLLamaClient.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Telefono_Cliente));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

      }
    });

    if(Estado.equals("En Ruta")){

      textarregl.setVisibility(View.VISIBLE);
      ArregloL.setVisibility(View.VISIBLE);
      btnSoliRut.setVisibility(View.GONE);

    }

    if(Estado.equals("Fabricado")){
      btnLLamaClient.setVisibility(View.GONE);
      btnSoliRut.setVisibility(View.VISIBLE);
      btnWhatsapp.setVisibility(View.GONE);
      ArregloL.setVisibility(View.GONE);
      btnCancelRut.setVisibility(View.GONE);

    }
    if(Estado.equals("En Espera")){

      btnSoliRut.setVisibility(View.GONE);
      textarregl.setVisibility(View.GONE);
      ArregloL.setVisibility(View.GONE);
      btnLLamaClient.setVisibility(View.GONE);
      btnWhatsapp.setVisibility(View.GONE);
      btnCancelRut.setVisibility(View.GONE);

    }

    if(coordenadas.length()<5){

      btnMapa.setVisibility(View.GONE);

    }

Glide.with(this).asBitmap().load("https://frutagolosa.com/FrutaGolosaApp/Administrador/images/"+k+".jpg").into(ImageArreglo);



    btnWhatsapp.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        final  String imgaent=getIntent().getStringExtra(PedidosAsignados.imgaentA);
        String numberWithCountryCode=Telefono_Cliente.replaceFirst("0","+593");
          SharedPreferences preferences=getSharedPreferences("login", Context.MODE_PRIVATE);
          String t=preferences.getString("ciudad","No");
         String message="";
        if(t.equals("QUITO")){
         message="Gracias por contar con Fruta Golosa, su presente ya fue entregado. \n" +
                "Recuerde que tiene descuento del 5, 10 y 15 por ciento en sus siguientes compras. \n" +
                "Ayúdenos con la encuesta, que nos permitirá seguir mejorando a cada instante. \n" +
                "https://forms.gle/1CfiKZPzhYS4ZFnA8\n" +
                "Muchas graciias. \n" +
                "Fruta Golosa Ecuador.";}
        if(t.equals("GUAYAQUIL")){
          message="Gracias por contar con Fruta Golosa Guayaquil, su presente ya fue entregado. \n" +
                  "*Recuerde que tiene descuento del 5, 10 y 15 por ciento en sus siguientes compras. \n" +
                  "Ayúdenos con la encuesta, que nos permitirá seguir mejorando a cada instante. \n" +
                  "https://forms.gle/ozn5pWFvG9zA9khZA\n" +
                  "Muchas gracias. \n" +
                  "Fruta Golosa Ecuador.*";}
        Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + numberWithCountryCode + "&text=" + message);

        Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(sendIntent);



      }
    });


    btnSoliRut.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        SharedPreferences preferences=getSharedPreferences("login", Context.MODE_PRIVATE);
        String nombreus=preferences.getString("nombreus","Registrese");
        String id=preferences.getString("id","Registrese");

        String a=nombreus;
        String b=IDPEDIDO;
        String c="En Ruta";

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();

        RegisterApiMotorizado api = adapter.create(RegisterApiMotorizado.class);

        api.inserusera(
                a,
                b,
                c,
                id,

                new Callback<retrofit.client.Response>() {
                  @Override
                  public void success(retrofit.client.Response result, Response response) {

                    BufferedReader reader = null;

                    String output = "";

                    try {
                      reader = new BufferedReader(new InputStreamReader(result.getBody().in()));

                      output = reader.readLine();
                    } catch (IOException e) {
                      e.printStackTrace();
                    }

                    Toast.makeText(DetallePedido.this, output, Toast.LENGTH_LONG).show();
                      Intent returnIntent = new Intent();
                      int resultx=2;
                      returnIntent.putExtra("result",resultx);
                      setResult(Activity.RESULT_OK,returnIntent);
                      finish();
                  }

                  @Override
                  public void failure(RetrofitError error) {
                    Toast.makeText(DetallePedido.this, error.toString(), Toast.LENGTH_LONG).show();

                  }
                }
        );



      }

    });


    btnCancelRut.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        SharedPreferences preferences=getSharedPreferences("login", Context.MODE_PRIVATE);
        String id=preferences.getString("id","Registrese");
        String b=IDPEDIDO;
        String a="motorizado";
        String c="Fabricado";

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();

        RegisterApiMotorizado api = adapter.create(RegisterApiMotorizado.class);

        api.inserusera(
                a,
                b,
                c,
                id,


                new Callback<retrofit.client.Response>() {
                  @Override
                  public void success(retrofit.client.Response result, Response response) {

                    BufferedReader reader = null;

                    String output = "";

                    try {
                      reader = new BufferedReader(new InputStreamReader(result.getBody().in()));

                      output = reader.readLine();
                    } catch (IOException e) {
                      e.printStackTrace();
                    }

                    Toast.makeText(DetallePedido.this, output, Toast.LENGTH_LONG).show();
                      Intent returnIntent = new Intent();
                      int resultx=3;
                      returnIntent.putExtra("result",resultx);
                      setResult(Activity.RESULT_OK,returnIntent);
                      finish();
                  }

                  @Override
                  public void failure(RetrofitError error) {
                    Toast.makeText(DetallePedido.this, error.toString(), Toast.LENGTH_LONG).show();

                  }
                }
        );


        finish();
      }

    });


    IDPEDIDOTXTt.setText("ID de Pedido: "+IDPEDIDO);
    idArreglotxt.setText("Arreglo: "+idarrelgo);
    Fecha_Pedidotxt.setText("Fecha Pedido: "+Fecha_Pedido);
    Nombre_Clientetxt.setText("Nombre Cliente:"+Nombre_Cliente);
    Telefono_Clientetxt.setText("Telefono Cliente: "+Telefono_Cliente);
    Correo_Clientetxt.setText("Correo Cliente: "+Correo_Cliente);
    Nombre_qrecibetxt.setText("Recibe: "+Nombre_qRecibe);
    Fecha_qRecibetxt.setText("Fecha a Entregar: "+Fecha_qRecibe);
    TelefoqRecibetxt.setText("Telefono quien recibe: "+Telefono_qrecibe);
    FranjaHorariatxt.setText("Horario: "+Franaja_horara);
    CallePrincipaltxt.setText("Calle Principal: "+Calle_principal);
    CalleSecundariattxt.setText("Calle Secundaria: "+Calle_secundaria);
    CasaempresaEdifciotxt.setText("Estructura: "+Casaempresaedificio);
    Referenciatxt.setText("Referencia: "+referencia);
    Portadatarjetatxt.setText("Portada Tarjeta: "+Portada_tarjeta);
    Textotarjetatxt.setText("Texto Tarjeta: "+Texto_tarjeta);
    Especificaciontxt.setText("Especificacion: "+Especificacion);
    KeyAccounttxt.setText("Keyaccount: "+Keyaccount);
    Parroquiatxt.setText("Parroquia: "+Parroquia);
    Costoenviotxt.setText("Costo Envio: "+Costo_envio);
    globotxt.setText("Globo: "+Globo);
    sectortxt.setText("Sector: "+sector);



    coordenadatxt.setText("Coordenadas: "+coordenadas);

    String Coordenadas2=coordenadas.replace("lat/lng","");
    String Coordenadas3=Coordenadas2.replace("(","");
    String Coordenadas4=Coordenadas3.replace(")","");
    final String Coordenadas5=Coordenadas4.replace(":","");
    coordenadatxt.setText("Coordenadas: "+Coordenadas5);
    ImageView imgent=findViewById(R.id.imgFotoEnt);







    Glide.with(this).load(imgaent).into(imgent);







    btnMapa.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        String map = "http://maps.google.co.in/maps?q=" + Coordenadas5;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getBaseContext().startActivity(intent);
      }
    });

    btnEntregCer.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        SharedPreferences preferences=getSharedPreferences("login", Context.MODE_PRIVATE);
        String b=IDPEDIDO;
        String a="Entregado";
        String id=preferences.getString("id","Registrese");
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();

        RegisterCerrarPedido api = adapter.create(RegisterCerrarPedido.class);

        api.inseruser(
                a,
                b,
                id,


                new Callback<retrofit.client.Response>() {
                  @Override
                  public void success(retrofit.client.Response result, Response response) {


                    BufferedReader reader = null;

                    String output = "";

                    try {
                      reader = new BufferedReader(new InputStreamReader(result.getBody().in()));

                      output = reader.readLine();
                    } catch (IOException e) {
                      e.printStackTrace();
                    }

                    Toast.makeText(DetallePedido.this, output, Toast.LENGTH_LONG).show();
                      Intent returnIntent = new Intent();
                      int resultx=2;
                      returnIntent.putExtra("result",resultx);
                      setResult(Activity.RESULT_OK,returnIntent);
                      finish();
                  }

                  @Override
                  public void failure(RetrofitError error) {
                    Toast.makeText(DetallePedido.this, error.toString(), Toast.LENGTH_LONG).show();

                  }
                }
        );
      }
    });



    ArregloL.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showFileChooser();
      }
    });

  }

  public String getStringImagen(Bitmap bmp) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
    byte[] imageBytes = baos.toByteArray();
    String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
    return encodedImage;
  }



  private void showFileChooser() {
    Intent intent = new Intent();
    intent.setType("image/*");
    intent.setAction(Intent.ACTION_GET_CONTENT);
    startActivityForResult(Intent.createChooser(intent, "Seleciona imagen"), PICK_IMAGE_REQUEST);

  }



  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    ImageView ArregloL=(ImageView) findViewById(R.id.imgFotoEnt);
    if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
      Uri filePath = data.getData();
      try {
          //Cómo obtener el mapa de bits de la Galería
          bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
          bitmap= Bitmap.createScaledBitmap(bitmap,440,520,true);
          //Configuración del mapa de bits en ImageView
          Glide.with(this).asBitmap().load(bitmap).into(ArregloL);

          //Configuración del mapa de bits en ImageView
        uploadImage();
        insetaimagenarreglolisto();


      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }


  public void uploadImage() {
    final String Fecha_qRecibe=getIntent().getStringExtra(PedidosAsignados.FechaQrecibeA);
    SharedPreferences preferences=getSharedPreferences("login", Context.MODE_PRIVATE);
    final String nombreus=preferences.getString("nombreus","Registrese");
    final String telefonous=preferences.getString("telefonous","No");

    final ProgressDialog loading = ProgressDialog.show(this, "Subiendo...", "Espere por favor");

    StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
            response -> {
              loading.dismiss();
              Toast.makeText(DetallePedido.this, response, Toast.LENGTH_LONG).show();
              if(response.equals("Imagen Enviada")){

                Button btnEntregCer=findViewById(R.id.btnEntregCerrar);
                btnEntregCer.setVisibility(View.VISIBLE);
               //   Log.d("Testttttttttt ",  getStringImagen(bitmap));

              }
            }, error -> loading.dismiss()){
      @Override
      protected Map<String, String> getParams() throws AuthFailureError {
        String imagen = getStringImagen(bitmap);
        String nombre = Fecha_qRecibe.replace("/","a")+xf2;

        Map<String, String> params = new Hashtable<String, String>();
        params.put(KEY_IMAGE, imagen);
        params.put(KEY_NOMBRE, nombre);

        return params;
      }
    };

    RequestQueue requestQueue = Volley.newRequestQueue(this);
    requestQueue.add(stringRequest);
  }
  private void insetaimagenarreglolisto(){
    final String IDPEDIDO=getIntent().getStringExtra(PedidosAsignados.IdPEDIDOA);
    final String Fecha_qRecibe=getIntent().getStringExtra(PedidosAsignados.FechaQrecibeA);
    SharedPreferences preferences=getSharedPreferences("login", Context.MODE_PRIVATE);
    final String telefonous=preferences.getString("telefonous","No");

    String a="https://frutagolosa.com/FrutaGolosaApp/uploads/"+Fecha_qRecibe.replace("/","a")+xf2+".png";
    String b=IDPEDIDO;

    RestAdapter adapter = new RestAdapter.Builder()
            .setEndpoint(ROOT_URL)
            .build();

    RegisterApi2 api = adapter.create(RegisterApi2.class);

    api.inseruser(
            a,
            b,


            new Callback<Response>() {
              @Override
              public void success(retrofit.client.Response result, Response response) {

                BufferedReader reader = null;

                String output = "";

                try {
                  reader = new BufferedReader(new InputStreamReader(result.getBody().in()));

                  output = reader.readLine();
                } catch (IOException e) {
                  e.printStackTrace();
                }

                Toast.makeText(DetallePedido.this, output, Toast.LENGTH_LONG).show();
              }

              @Override
              public void failure(RetrofitError error) {
                Toast.makeText(DetallePedido.this, error.toString(), Toast.LENGTH_LONG).show();

              }
            }
    );





  }




}
