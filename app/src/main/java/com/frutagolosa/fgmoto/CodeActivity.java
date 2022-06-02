package com.frutagolosa.fgmoto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.frutagolosa.fgmoto.R;
import com.frutagolosa.fgmoto.api.ApiInterfaceCertificado;
import com.frutagolosa.fgmoto.api.ApiSMS;
import com.frutagolosa.fgmoto.api.ApiVerificateCode;
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
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CodeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        SharedPreferences preferences=getSharedPreferences("login", Context.MODE_PRIVATE);
        String t=preferences.getString("telefonous","No");

        EditText edtx= (EditText) findViewById(R.id.editTextNumber);
        EditText edtxCode= (EditText) findViewById(R.id.editTextNumber2);
        Button btn=(Button) findViewById(R.id.button2);
        edtx.setText(t);
        edtx.setEnabled(false);

        RestAdapter adapter2 = new RestAdapter.Builder()
                .setEndpoint("https://frutagolosa.com/FrutaGolosaApp")
                .build();

        ApiSMS api2 = adapter2.create(ApiSMS.class);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        api2.sendSms(
                t,



                new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {

                        BufferedReader reader = null;
                        String output = "";


                        try {
                            reader = new BufferedReader(new InputStreamReader(result.getBody().in()));


                            output = reader.readLine();
                            Toast.makeText(CodeActivity.this, output.toString(), Toast.LENGTH_SHORT).show();





                        } catch (IOException e) {
                            Toast.makeText(CodeActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {


                    }
                }

        );

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             String code=    edtxCode.getText().toString();
                RestAdapter adapter2 = new RestAdapter.Builder()
                        .setEndpoint("https://frutagolosa.com/FrutaGolosaApp")
                        .build();

                ApiVerificateCode api2 = adapter2.create(ApiVerificateCode.class);
                setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                api2.sendSms(
                        code,



                        new Callback<Response>() {
                            @Override
                            public void success(Response result, Response response) {

                                BufferedReader reader = null;
                                String output = "";


                                try {
                                    reader = new BufferedReader(new InputStreamReader(result.getBody().in()));


                                    output = reader.readLine();
                                    Toast.makeText(CodeActivity.this, output.toString(), Toast.LENGTH_SHORT).show();
                                    if(output.equals("Codigo Verificado")){
                                        SharedPreferences.Editor editor=preferences.edit();
                                        editor.putString("verify","Si");
                                        editor.commit();
                                        Intent f = new Intent(CodeActivity.this, MainActivity.class);
                                        startActivity(f);
                                        finish();

                                    }





                                } catch (IOException e) {
                                    Toast.makeText(CodeActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void failure(RetrofitError error) {


                            }
                        }

                );
            }
        });
    }

}