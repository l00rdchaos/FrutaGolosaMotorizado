package com.frutagolosa.frutgolosamotorizado;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class TheService extends Service {

  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public void onCreate() {

  }

  @Override
  public void onDestroy() {
    ;
  }

  @Override
  public void onStart(Intent intent, int startid) {

  }
}