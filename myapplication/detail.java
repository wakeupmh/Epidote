package com.example.nicolas.myapplication;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jgabrielfreitas.core.BlurImageView;

public class detail extends AppCompatActivity{
    BlurImageView mBlurImage;
    RelativeLayout qrCode,openGMaps;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mBlurImage = (BlurImageView) findViewById(R.id.imgDetail);
        mBlurImage.setBlur(3);
        qrCode = findViewById(R.id.qrCode);
        txt = (TextView) findViewById(R.id.tit) ;
        Intent intent = getIntent();
        if(intent.hasExtra("byteArray")) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(
                    intent.getByteArrayExtra("byteArray"), 0, intent.getByteArrayExtra("byteArray").length);
            mBlurImage.setImageBitmap(bitmap);
        }
        if(intent.hasExtra("tit")){
            txt.setText(intent.getStringExtra("tit"));
        }
        qrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent qr = new Intent(detail.this, QRCodeActivity.class);
                startActivity(qr);
            }
        });
        openGMaps = findViewById(R.id.gMaps);
        openGMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gm = new Intent(Intent.ACTION_VIEW);
                gm.setData(Uri.parse("geo:-23.1990083,-45.9073377"));
                startActivity(gm);
            }
        });
    }
}
