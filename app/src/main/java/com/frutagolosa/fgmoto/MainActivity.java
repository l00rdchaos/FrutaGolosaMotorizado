package com.frutagolosa.fgmoto;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.frutagolosa.fgmoto.api.ApiInterfaceCertificado;
import com.frutagolosa.fgmoto.api.ApiInterfaceVersion;
import com.frutagolosa.fgmoto.api.RegisterApi3;
import com.google.firebase.FirebaseApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity {
  TextView mensaje1;
  TextView mensaje2;
  TextView efectivot;
  String User;

  public static final String ROOT_URL="https://frutagolosa.com/FrutaGolosaApp";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mensaje1 = (TextView) findViewById(R.id.mensaje1);
    mensaje2 = (TextView) findViewById(R.id.mensaje2);

    efectivot = (TextView) findViewById(R.id.txtefectivo);

    SharedPreferences preferences=getSharedPreferences("login", Context.MODE_PRIVATE);
    final String nombreus=preferences.getString("nombreus","Registrese");
    final String id=preferences.getString("id","Registrese");
    String c=preferences.getString("mailus","No");
    String t=preferences.getString("telefonous","No");
    String v=preferences.getString("verify","No");
    String empresa=preferences.getString("empresa","No");
    User=nombreus;

    final Button PediFab=findViewById(R.id.pedidosFabricadosbtn);
    final Button PediAsig=findViewById(R.id.btnPdAsginados);
    final Button PediEsp=findViewById(R.id.btnPedidoEspera);
    final Button PediEngHoy=findViewById(R.id.btnEntregHoy);
    final TextView TxtBien=findViewById(R.id.texBienve);


   // startService(new Intent(MainActivity.this,TheService.class));



    TxtBien.setText("Bienvenido "+nombreus+" Recuerde no salir en ruta sin haberse asignado, si su pedido no sale en FABRICADOS y/o no tiene foto, solicitelo.");
    FirebaseApp.initializeApp(this);
    if(v.equals("No")) {
      Intent ra = new Intent(MainActivity.this, Login_ValidActivity.class);

      startActivity(ra);
      finish();

    }else
    {

      RestAdapter adapter2 = new RestAdapter.Builder()
              .setEndpoint("https://frutagolosa.com/FrutaGolosaApp/certificadomotorizado.php?t="+t)
              .build();

      ApiInterfaceCertificado api2 = adapter2.create(ApiInterfaceCertificado.class);
      setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
      api2.comprarver(
              c,
              t,



              new Callback<Response>() {
                @Override
                public void success(Response result, Response response) {

                  BufferedReader reader = null;
                  String output = "";


                  try {
                    reader = new BufferedReader(new InputStreamReader(result.getBody().in()));


                    output = reader.readLine();

                    if(output.equals("no")){

                      PediFab.setVisibility(View.GONE);
                      PediAsig.setVisibility(View.GONE);
                      PediEsp.setVisibility(View.GONE);
                      PediEngHoy.setVisibility(View.GONE);
                      TxtBien.setText(nombreus+" ,Usted no esta certificado por fruta golosa para ser motorizado, contactese con nosotros.");
                    }



                  } catch (IOException e) {
                    e.printStackTrace();
                  }

                }

                @Override
                public void failure(RetrofitError error) {

                  TxtBien.setText("Bienvenido "+nombreus+"Revise su conexion para acceder a los botones de pedidos ");
                  PediFab.setVisibility(View.GONE);
                  PediAsig.setVisibility(View.GONE);
                  PediEsp.setVisibility(View.GONE);
                  PediEngHoy.setVisibility(View.GONE);
                }
              }

      );



      RestAdapter adapter = new RestAdapter.Builder()
              .setEndpoint("https://frutagolosa.com/FrutaGolosaApp/MotorizadoEfectivo.php?z="+nombreus)
              .build();

      ApiInterfaceVersion api = adapter.create(ApiInterfaceVersion.class);

      String z=nombreus;
      api.evaluaversion(
              z,
              id,




              new Callback<Response>() {
                @Override
                public void success(Response result, Response response) {

                  BufferedReader reader = null;
                  String output = "";

                  try {
                    reader = new BufferedReader(new InputStreamReader(result.getBody().in()));

                    output = reader.readLine();
                  } catch (IOException e) {
                    e.printStackTrace();
                  }
                if(empresa.equals("FRUTA GOLOSA")){ efectivot.setText(output);}
                else{

                  efectivot.setText("Eres motorizado de la empresa "+empresa);
                }

                }

                @Override
                public void failure(RetrofitError error) {

                }
              }
      );


    }



    final CountDownTimer mcountdowntimer = new CountDownTimer(100000, 1000) {
      @Override
      public void onTick(long millisUntilFinished) {

      }

      @Override
      public void onFinish() {
        start();
      //  EnviarUbicacion();


      }
    }.start();


    PediEngHoy.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent a= new Intent(MainActivity.this, PedidosEntregadosMi.class);
        startActivity(a);
      }
    });
    PediFab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent a= new Intent(MainActivity.this, PedidosFabricados.class);
        startActivity(a);
      }
    });

    PediAsig.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent a= new Intent(MainActivity.this, PedidosAsignados.class);
        startActivity(a);
      }
    });

    PediEsp.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent a= new Intent(MainActivity.this, PedidosEnEspera.class);
        startActivity(a);
      }
    });

    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
    } else {
      //locationStart();
    }
  }

  private void notificacion() {
    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    String NOTIFICATION_CHANNEL_ID = "frutagolosa_01";
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_MAX);
      // Configure the notification channel.
      notificationChannel.setDescription("Fruta Golosa Notifica");
      notificationChannel.enableLights(true);
      notificationChannel.setLightColor(Color.GREEN);
      notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
      notificationChannel.enableVibration(true);
      notificationManager.createNotificationChannel(notificationChannel);
    }
    PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0,
            new Intent(getApplicationContext(), MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), NOTIFICATION_CHANNEL_ID);
    notificationBuilder.setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.mipmap.frutagolosa)
            .setTicker("FrutaGolosa")
            //.setPriority(Notification.PRIORITY_MAX)
            .setContentTitle("Fruta Golosa Notifica")
            .setContentText("Se ha enviado su ubicacion")
            .setContentInfo("Ubicacion")
            .setContentIntent(contentIntent)
    ;

    notificationManager.notify(1, notificationBuilder.build());
  }

  private void notificacion2() {
    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    String NOTIFICATION_CHANNEL_ID = "frutagolosa_01";
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_MAX);
      // Configure the notification channel.
      notificationChannel.setDescription("Fruta Golosa Notifica");
      notificationChannel.enableLights(true);
      notificationChannel.setLightColor(Color.GREEN);
      notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
      notificationChannel.enableVibration(true);
      notificationManager.createNotificationChannel(notificationChannel);
    }
    PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0,
            new Intent(getApplicationContext(), MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), NOTIFICATION_CHANNEL_ID);
    notificationBuilder.setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.mipmap.frutagolosa)
            .setTicker("FrutaGolosa")
            //.setPriority(Notification.PRIORITY_MAX)
            .setContentTitle("Fruta Golosa Notifica")
            .setContentText("No se envio ubicacion")
            .setContentInfo("Ubicacion")
            .setContentIntent(contentIntent)
    ;

    notificationManager.notify(1, notificationBuilder.build());
  }

  private void EnviarUbicacion() {


    RestAdapter adapter = new RestAdapter.Builder()
            .setEndpoint(ROOT_URL)
            .build();

    RegisterApi3 api = adapter.create(RegisterApi3.class);
    String a=mensaje1.getText().toString();
    String b=mensaje2.getText().toString();
    SharedPreferences preferences=getSharedPreferences("login", Context.MODE_PRIVATE);
    String nombreus=preferences.getString("nombreus","Registrese");
    String mailus=preferences.getString("mailus","No");
    String c=mailus;
    api.inseruser(
            a,
            b,
            c,


            new Callback<retrofit.client.Response>() {
              @Override
              public void success(retrofit.client.Response result, Response response) {
                BufferedReader reader = null;

                String output = "";

                try {
                  reader = new BufferedReader(new InputStreamReader(result.getBody().in()));

                  output = reader.readLine();

                  Toast.makeText(MainActivity.this, "Fruta Golosa: "+output, Toast.LENGTH_SHORT).show();
                  if(output.equals("Coordenadas enviadas")){




                  }else {

                    notificacion2();

                  }

                } catch (IOException e) {
                  e.printStackTrace();
                }




              }

              @Override
              public void failure(RetrofitError error) {
                //notificacion2();

              }
            }
    );

  }


  private void locationStart() {
    LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    Localizacion Local = new Localizacion();
    Local.setMainActivity(this);
    final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    if (!gpsEnabled) {
      Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
      startActivity(settingsIntent);
    }
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
      return;
    }
    mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
    mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);
    mensaje1.setText("Localización agregada");
    mensaje2.setText("");
  }
  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    if (requestCode == 1000) {
      if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        //locationStart();
        return;
      }
    }
  }
  public void setLocation(Location loc) {
    //Obtener la direccion de la calle a partir de la latitud y la longitud
    if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
      try {
        TextClock tc=(TextClock)findViewById(R.id.clock);
        String hora= tc.getText().toString();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> list = geocoder.getFromLocation(
                loc.getLatitude(), loc.getLongitude(), 1);
        if (!list.isEmpty()) {
          Address DirCalle = list.get(0);
          mensaje2.setText(DirCalle.getAddressLine(0)+" "+hora);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
  /* Aqui empieza la Clase Localizacion */
  public class Localizacion implements LocationListener {
    MainActivity mainActivity;
    public MainActivity getMainActivity() {
      return mainActivity;
    }
    public void setMainActivity(MainActivity mainActivity) {
      this.mainActivity = mainActivity;
    }
    @Override
    public void onLocationChanged(Location loc) {
      // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
      // debido a la deteccion de un cambio de ubicacion
      loc.getLatitude();
      loc.getLongitude();
      String Text = loc.getLatitude() + "," + loc.getLongitude();
      mensaje1.setText(Text);
      this.mainActivity.setLocation(loc);
    }
    @Override
    public void onProviderDisabled(String provider) {
      // Este metodo se ejecuta cuando el GPS es desactivado
      mensaje1.setText("GPS Desactivado");
    }
    @Override
    public void onProviderEnabled(String provider) {
      // Este metodo se ejecuta cuando el GPS es activado
      mensaje1.setText("GPS Activado");
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
      switch (status) {
        case LocationProvider.AVAILABLE:
          Log.d("debug", "LocationProvider.AVAILABLE");
          break;
        case LocationProvider.OUT_OF_SERVICE:
          Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
          break;
        case LocationProvider.TEMPORARILY_UNAVAILABLE:
          Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
          break;
      }
    }




  }
}