package com.frutagolosa.fgmoto;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.frutagolosa.fgmoto.api.RegisterAPI;
import com.frutagolosa.fgmoto.api.RegisterFotoMotorizado;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Login_ValidActivity extends AppCompatActivity {
  FirebaseAuth auth;
  private final int xf=(int)(Math.random()*10000);
  private final String xf2=String.valueOf(xf);
  PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
  String verification_code;
  int PICK_IMAGE_REQUEST = 1;
  String KEY_IMAGE = "foto";
  Bitmap bitmap;
  String UPLOAD_URL = "https://frutagolosa.com/FrutaGolosaApp/Upload2.php";
  String KEY_NOMBRE = "nombre";
  Boolean img = false;

  public static final String ROOT_URL="https://frutagolosa.com/FrutaGolosaApp";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login__valid);
    FirebaseApp.initializeApp(this);


    Button envcodcod = (Button) findViewById(R.id.btnEnviacod);

    ImageView idfotoMoto=(ImageView) findViewById(R.id.idfotomotorizado);
    Spinner ciudadsp=(Spinner) findViewById(R.id.SpCiudades);
    Spinner sptr=(Spinner) findViewById(R.id.spinnerTrab);
    envcodcod.setVisibility(View.VISIBLE);
    idfotoMoto.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showFileChooser();
      }
    });

    String[] ciudades={"QUITO","GUAYAQUIL"};
    String[] empresas={"FRUTA GOLOSA","ROMA"};
    ArrayAdapter <String> adapter1= new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, ciudades);
    ciudadsp.setAdapter(adapter1);
    ArrayAdapter <String>     adapter2= new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, empresas);
    sptr.setAdapter(adapter2);


envcodcod.setOnClickListener(new View.OnClickListener() {
  @Override
  public void onClick(View view) {
    singIn();
  }
});



  }


  public void singIn() {

    if(!img){
      Toast.makeText(this, "Falta la imagen", Toast.LENGTH_SHORT).show();
      return;
    }
                  EditText n= (EditText) findViewById(R.id.editTextName);
                  EditText c= (EditText) findViewById(R.id.editTextMail);
                  final EditText t= (EditText) findViewById(R.id.txtpohne);
                  Spinner ciudadsp=(Spinner) findViewById(R.id.SpCiudades);
                  String telefono=t.getText().toString().trim().replace(" ","");
                  String nombre=n.getText().toString().trim();
                  String correo=c.getText().toString().trim().replace(" ","").toLowerCase();
                  String foto="https://frutagolosa.com/FrutaGolosaApp/FotoMotorizado/"+xf2+".png";
                  String ciudad=ciudadsp.getSelectedItem().toString();

                  RestAdapter adapter = new RestAdapter.Builder()
                          .setEndpoint(ROOT_URL)
                          .build();

                  RegisterAPI api = adapter.create(RegisterAPI.class);
                  api.inserCliente(
                          telefono,
                          nombre,
                          correo,
                          foto,
                          ciudad,



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

                              Toast.makeText(Login_ValidActivity.this, output, Toast.LENGTH_LONG).show();
                             if(!output.equals("No se registro" )&&!output.equals("Datos incorrectos") ){
                            savepreferences(output);

                              // notificacion();
                               uploadImage();

                              }


                            }

                            @Override
                            public void failure(RetrofitError error) {
                              Toast.makeText(Login_ValidActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                              t.setVisibility(View.VISIBLE);
                            }
                          }
                  );


  }









  private void savepreferences(String id){
    EditText n= (EditText) findViewById(R.id.editTextName);
    EditText t= (EditText) findViewById(R.id.editTextMail);
    EditText c= (EditText) findViewById(R.id.txtpohne);
    Spinner ciudadsp=(Spinner) findViewById(R.id.SpCiudades);
    Spinner trbsp=(Spinner) findViewById(R.id.spinnerTrab);
    String ciudad=ciudadsp.getSelectedItem().toString();
    String empresa=trbsp.getSelectedItem().toString();
    SharedPreferences preferences=getSharedPreferences("login", Context.MODE_PRIVATE);
    String nombre=n.getText().toString();
    String mail=t.getText().toString();
    String telefono=c.getText().toString();

    SharedPreferences.Editor editor=preferences.edit();
    editor.putString("nombreus",nombre);
    editor.putString("mailus",mail);
    editor.putString("telefonous",telefono);
    editor.putString("ciudad",ciudad);
    editor.putString("empresa",empresa);
    editor.putString("id",id);
    editor.putString("verify","No");
    editor.commit();

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
    if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
      Uri filePath = data.getData();
      String filePatha = MediaStore.Images.Media.DATA;
      try {
        ImageView fotom=(ImageView) findViewById(R.id.idfotomotorizado);
        //Cómo obtener el mapa de bits de la Galería
        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

        //Cómo obtener el mapa de bits de la Galería
        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
        bitmap= Bitmap.createScaledBitmap(bitmap,440,520,true);
        //Configuración del mapa de bits en ImageView


        //Configuración del mapa de bits en ImageView
        Glide.with(this)
                .load(bitmap)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(fotom);
             img=true;

        Button btnEnv=findViewById(R.id.btnEnviacod);
        btnEnv.setVisibility(View.VISIBLE);

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }


  public void uploadImage() {
    SharedPreferences preferences=getSharedPreferences("login", Context.MODE_PRIVATE);

    final ProgressDialog loading = ProgressDialog.show(this, "Subiendo...", "Espere por favor");

    StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
            response -> {

            //  Toast.makeText(Login_ValidActivity.this, response, Toast.LENGTH_LONG).show();
              Intent f = new Intent(Login_ValidActivity.this, CodeActivity.class);
              startActivity(f);
              finish();
              loading.dismiss();
            }, error -> {
              loading.dismiss();
              Toast.makeText(Login_ValidActivity.this, "No se cargo la imagen intenta de nuevo", Toast.LENGTH_SHORT).show();

            }){
      @Override
      protected Map<String, String> getParams() throws AuthFailureError {
        String imagen = getStringImagen(bitmap);
        String nombre = xf2;

        Map<String, String> params = new Hashtable<String, String>();
        params.put(KEY_IMAGE, imagen);
        params.put(KEY_NOMBRE, nombre);

        return params;
      }
    };

    RequestQueue requestQueue = Volley.newRequestQueue(this);
    requestQueue.add(stringRequest);
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
    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
    notificationBuilder.setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.mipmap.frutagolosa)
            .setTicker("FrutaGolosa")
            //.setPriority(Notification.PRIORITY_MAX)
            .setContentTitle("Fruta Golosa App")
            .setContentText("Registro exitoso")
            .setContentInfo("Ya puede acceder a nuestros pedidos");
    notificationManager.notify(1, notificationBuilder.build());
  }




}