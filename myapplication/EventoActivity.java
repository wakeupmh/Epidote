package com.example.nicolas.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class EventoActivity extends relatorio_criar{

    TextView txtNomeEvento;
    TextView txtEndereco;
    TextView txtDataInicio;
    TextView txtDataFim;
    Button btnAbrirQuiz;
    boolean ok = false;

    //public FirebaseDatabase firebaseDatabase;
    //public DatabaseReference databaseReference;
    EventoAC evento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        txtNomeEvento = (TextView) findViewById(R.id.txtNomeEvento);
        txtEndereco = (TextView) findViewById(R.id.txtEndereco);
        txtDataFim = (TextView) findViewById(R.id.txtDataFim);
        txtDataInicio = (TextView) findViewById(R.id.txtDataInicio);
        btnAbrirQuiz = (Button) findViewById(R.id.btnAbrirQuiz);
        final String idEvento = getIntent().getExtras().getString("eventUUID");


        btnAbrirQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idEvento != null) {

                    Intent i = new Intent(EventoActivity.this, QuizActivity.class);
                    startActivity(i);
                }
                else{
                    Intent i = new Intent(EventoActivity.this, QRCodeActivity.class);
                    startActivity(i);
                }

            }
        });

        //inicializarFirebase();

        carregarEvento(idEvento);
    }

//    public void inicializarFirebase(){
//       // FirebaseApp.initializeApp(EventoActivity.this);
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference();
//    }


    public void carregarEvento(final String uuidEvento){


        databaseReference.child("Evento")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String id = null;

                for(DataSnapshot object : dataSnapshot.getChildren()){
                    id = String.valueOf(object.child("uuid").getValue());


                    if(id.equals(uuidEvento)){

                        evento = object.getValue(EventoAC.class);
                        txtNomeEvento.setText(evento.getNome());
                        txtDataFim.setText(evento.getDataFim());
                        txtDataInicio.setText(evento.getDataInicio());
                        txtEndereco.setText(evento.getEndereco());

                        break;
                    }
                }

                ok = true;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }




}
