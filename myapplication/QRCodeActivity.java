package com.example.nicolas.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;



public class QRCodeActivity extends AppCompatActivity {

    IntentIntegrator intentIntegrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        startQRScanner();
    }

    private void startQRScanner() {
        new IntentIntegrator(this).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);


        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                vibrate();
                openQuiz(String.valueOf(result.getContents()));

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    protected void openQuiz(String scanCode){
        Intent evento = new Intent(this,EventoActivity.class);
        evento.putExtra("eventUUID",scanCode);
        startActivity(evento);
    }

    protected  void vibrate(){
        Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(150);
    }

}