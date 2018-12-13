package com.example.nicolas.myapplication;
import android.animation.ArgbEvaluator;
import android.content.Intent;
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

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
public class swiper extends AppCompatActivity{
    ViewPager viewPager;
    List<model> models;
    adapter adapter;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swiper);
        models = new ArrayList<>();
        models.add(new model(R.drawable.brochure, "Mídias", "Encontre um banner que trate sobre mídias em geral, desde as redes sociais até jornais.", model.class));
        models.add(new model(R.drawable.sticker, "Alimentos", "Encontre um banner que informe sobre alimentação ou algum tema referente ao setor agrícola.", model.class));
        models.add(new model(R.drawable.poster, "Marketing", "Encontre um banner que aborde temas relacionados ao marketing e a geração de conteúdo.", model.class));
        models.add(new model(R.drawable.namecard, "Empresa ou Consultoria", "Encontre um banner que solucione um problema de uma empresa ou atenda a alguma demanda de consultoria.", model.class));
        adapter = new adapter(models, this);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130, 0, 130, 0);

        Integer[] colors_temp = {
                ContextCompat.getColor(this, R.color.color1),
                ContextCompat.getColor(this,R.color.color2),
                ContextCompat.getColor(this,R.color.color3),
                ContextCompat.getColor(this,R.color.color4)
        };
        colors = colors_temp;
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position<(adapter.getCount() - 1) && position < (colors.length - 1)){
                    viewPager.setBackgroundColor(
                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                }else{
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
