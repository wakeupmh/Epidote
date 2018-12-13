package com.example.nicolas.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class relatorio_especifico extends relatorio_listagem {


    TextView txtResumo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_especifico);


        Intent recebe = getIntent();

        String RelNome, RelRA, RelResumo, RelGrupo;

        RelNome = recebe.getStringExtra("RelNome");
        RelResumo = recebe.getStringExtra("RelResumo");
        RelGrupo = recebe.getStringExtra("RelGrupo");
        RelRA = recebe.getStringExtra("RelRA");


        inicializarFirebase();
        eventoDatabase();

        txtResumo = (TextView)findViewById(R.id.txtResumo);

        txtResumo.setText("Resumo feito por: "+ RelNome+" RA: "+RelRA+" após assistir a apresentação do grupo " +
                RelGrupo+" onde foi possivel concluir: " +RelResumo);


    }
    public void deleteReg(View v) {
        Intent recebe = getIntent();
        String UUid;
        UUid = recebe.getStringExtra("UUid");
        databaseReference.child("Relatorio").child(UUid).removeValue();
        finish();
        Intent telachamarRelatorio = new Intent(this, relatorio_listagem.class);
        startActivity(telachamarRelatorio);


    }
    public void eventoDatabase(){
        databaseReference.child("Relatorio").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listRelatorio.clear();
                for(DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    relatorioCampos p = objSnapshot.getValue(relatorioCampos.class);
                    listRelatorio.add(p);

                }
                arrayAdapterRelatorio = new ArrayAdapter<relatorioCampos>(relatorio_especifico.this,
                        android.R.layout.simple_list_item_1,listRelatorio);

                listaRelatorioEsp.setAdapter(arrayAdapterRelatorio);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
