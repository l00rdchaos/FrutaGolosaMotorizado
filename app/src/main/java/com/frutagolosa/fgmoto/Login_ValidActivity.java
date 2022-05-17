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
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
  public static final String ROOT_URL="https://frutagolosa.com/FrutaGolosaApp";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login__valid);
    FirebaseApp.initializeApp(this);
    TextView dig = (TextView) findViewById(R.id.txtdigitcode);
    EditText codee = (EditText) findViewById(R.id.txtcod);
    Button envcodcod = (Button) findViewById(R.id.btnEnviacod);
    TextView contt = (TextView) findViewById(R.id.conteotxt);
    Button btnpdcod = (Button) findViewById(R.id.btnpedcod);
    ImageView idfotoMoto=(ImageView) findViewById(R.id.idfotomotorizado);
    Spinner ciudadsp=(Spinner) findViewById(R.id.SpCiudades);
    dig.setVisibility(View.INVISIBLE);
    codee.setVisibility(View.INVISIBLE);
    envcodcod.setVisibility(View.INVISIBLE);
    contt.setVisibility(View.INVISIBLE);
    btnpdcod.setVisibility(View.VISIBLE);
    idfotoMoto.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showFileChooser();
      }
    });

    String[] ciudades={"QUITO","GUAYAQUIL"};

    ArrayAdapter <String> adapter1= new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, ciudades);
    ciudadsp.setAdapter(adapter1);

    FirebaseApp.initializeApp(getApplicationContext());
    auth = FirebaseAuth.getInstance();


    mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
      @Override
      public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

      }

      @Override
      public void onVerificationFailed(FirebaseException e) {
        Toast.makeText(getApplicationContext(), "Codigo no enviado, revise el numero por favor", Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
        super.onCodeSent(s, forceResendingToken);
        verification_code = s;
        Toast.makeText(getApplicationContext(), "Codigo enviado a su numero", Toast.LENGTH_SHORT).show();
        TextView dig = (TextView) findViewById(R.id.txtdigitcode);
        EditText codee = (EditText) findViewById(R.id.txtcod);
        Button envcodcod = (Button) findViewById(R.id.btnEnviacod);
        TextView contt = (TextView) findViewById(R.id.conteotxt);
        Button btnpdcod = (Button) findViewById(R.id.btnpedcod);
        dig.setVisibility(View.VISIBLE);
        codee.setVisibility(View.VISIBLE);
        envcodcod.setVisibility(View.VISIBLE);
        contt.setVisibility(View.VISIBLE);
        btnpdcod.setVisibility(View.INVISIBLE);
      }
    };


  }


  public void send_sms(View v) {
    EditText n= (EditText) findViewById(R.id.editTextName);
    EditText t= (EditText) findViewById(R.id.editTextMail);
    EditText c= (EditText) findViewById(R.id.txtpohne);
    String telefono=c.getText().toString();
    String nombre=n.getText().toString();
    String correo=t.getText().toString();

    if(n.getText().toString().trim().isEmpty()||t.getText().toString().trim().isEmpty()||c.getText().toString().trim().isEmpty()){
      Toast.makeText(this, "Por favor rellene los campos en blanco", Toast.LENGTH_SHORT).show();
    }
    else {
      EditText phones = (EditText) findViewById(R.id.txtpohne);
      String number = phones.getText().toString().replaceFirst("0","+593").trim();
      PhoneAuthProvider.getInstance().verifyPhoneNumber(
              number, 60,
              TimeUnit.SECONDS,

              this, mCallback
      );

      n.setVisibility(View.GONE);
      t.setVisibility(View.GONE);
      c.setFocusable(false);
      conteo();
    }
  }

  public void singInWithPhone(PhoneAuthCredential credential) {
    auth.signInWithCredential(credential)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                  savepreferences();
                  Toast.makeText(getApplicationContext(), "Usuario registrado con exito", Toast.LENGTH_SHORT).show();
                  EditText n= (EditText) findViewById(R.id.editTextName);
                  EditText c= (EditText) findViewById(R.id.editTextMail);
                  final EditText t= (EditText) findViewById(R.id.txtpohne);
                  Spinner ciudadsp=(Spinner) findViewById(R.id.SpCiudades);
                  c.setVisibility(View.INVISIBLE);
                  String telefono=t.getText().toString().trim().replace(" ","");
                  String nombre=n.getText().toString().trim();
                  String correo=c.getText().toString().trim().replace(" ","");
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
                              if(output.equals("No se registro")){
                                Toast.makeText(Login_ValidActivity.this, "problemas con el servidor", Toast.LENGTH_SHORT).show();
                                t.setVisibility(View.VISIBLE);
                              }
                              else {
                                Intent f = new Intent(Login_ValidActivity.this, Inicio.class);
                                notificacion();
                                uploadImage();
                                startActivity(f);
                                try {
                                  FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                  user.delete();
                                }catch (Exception e){

                                }
                                finish();
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

                else {  Toast.makeText(getApplicationContext(), "Codigo erroneo", Toast.LENGTH_SHORT).show();}


              }
            });


  }

  public void verify(View v) {

    EditText cod = (EditText) findViewById(R.id.txtcod);
    String input_code = cod.getText().toString();
    if (input_code.equals("")) {

      Toast.makeText(getApplicationContext(), "codigo en blanco", Toast.LENGTH_SHORT).show();

    }
    else {
      verifyPhoneNumber(verification_code, input_code);



    }
  }

  public void verifyPhoneNumber(String verifyCode, String input_code) {

    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verifyCode, input_code);
    singInWithPhone(credential);

  }





  public void conteo() {
    final EditText n= (EditText) findViewById(R.id.editTextName);
    final EditText c= (EditText) findViewById(R.id.editTextMail);
    final EditText t= (EditText) findViewById(R.id.txtpohne);
    final TextView contto = (TextView) findViewById(R.id.conteotxt);
    final Button btnpdcodo = (Button) findViewById(R.id.btnpedcod);
    final CountDownTimer mcountdowntimer = new CountDownTimer(120000, 1000) {
      @Override
      public void onTick(long millisUntilFinished) {
        contto.setText("Solicitar en: " + (millisUntilFinished /  1000));
        contto.setVisibility(View.VISIBLE);
        btnpdcodo.setVisibility(View.INVISIBLE);
        contto.setVisibility(View.VISIBLE);
      }

      @Override
      public void onFinish() {

        btnpdcodo.setVisibility(View.VISIBLE);
        contto.setText("");
        n.setVisibility(View.VISIBLE);
        t.setVisibility(View.VISIBLE);
        c.setVisibility(View.VISIBLE);
        t.setFocusable(true);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.delete();

      }
    }.start();

  }

  private void savepreferences(){
    EditText n= (EditText) findViewById(R.id.editTextName);
    EditText t= (EditText) findViewById(R.id.editTextMail);
    EditText c= (EditText) findViewById(R.id.txtpohne);
    Spinner ciudadsp=(Spinner) findViewById(R.id.SpCiudades);
    String ciudad=ciudadsp.getSelectedItem().toString();
    SharedPreferences preferences=getSharedPreferences("login", Context.MODE_PRIVATE);
    String nombre=n.getText().toString();
    String mail=t.getText().toString();
    String telefono=c.getText().toString();

    SharedPreferences.Editor editor=preferences.edit();
    editor.putString("nombreus",nombre);
    editor.putString("mailus",mail);
    editor.putString("telefonous",telefono);
    editor.putString("ciudad",ciudad);
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
        bitmap= Bitmap.createScaledBitmap(bitmap,440,520,true);
        Matrix matrix = new Matrix();

        matrix.postRotate(90);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 440, 520, true);

        Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);


        //Configuración del mapa de bits en ImageView
        Glide.with(this)
                .load(rotatedBitmap)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(fotom);


        Button btnEnv=findViewById(R.id.btnEnviacod);
        btnEnv.setVisibility(View.VISIBLE);

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }


  public void uploadImage() {
    final String Fecha_qRecibe=getIntent().getStringExtra(PedidosAsignados.FechaQrecibeA);
    SharedPreferences preferences=getSharedPreferences("login", Context.MODE_PRIVATE);
    final String nombreus=preferences.getString("nombreus","Registrese");
    final String mailus=preferences.getString("mailus","No");
    final String telefonous=preferences.getString("telefonous","No");

    final ProgressDialog loading = ProgressDialog.show(this, "Subiendo...", "Espere por favor");

    StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
            new com.android.volley.Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                loading.dismiss();
                Toast.makeText(Login_ValidActivity.this, response, Toast.LENGTH_LONG).show();
              }
            }, new com.android.volley.Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        loading.dismiss();

      }
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


  private void insertafotomotorizado(){


    String a="https://frutagolosa.com/FrutaGolosaApp/uploads/"+xf2+".png";

    String b="https://frutagolosa.com/FrutaGolosaApp/uploads/"+xf2+".png";

    RestAdapter adapter = new RestAdapter.Builder()
            .setEndpoint(ROOT_URL)
            .build();

    RegisterFotoMotorizado api = adapter.create(RegisterFotoMotorizado.class);

    api.inseruser(
            a,
            b,


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
              }

              @Override
              public void failure(RetrofitError error) {
                Toast.makeText(Login_ValidActivity.this, error.toString(), Toast.LENGTH_LONG).show();

              }
            }
    );




  }

}