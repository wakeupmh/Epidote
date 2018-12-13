package com.example.nicolas.myapplication;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class login extends FirebaseActivity{


    TextView textView3, pager, ocr;
    EditText edtEmailLog,edtSenhaLog;
    Button btnLogin;
//    private DatabaseReference reference;
//    private FirebaseUser user;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    public static String RALogged;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textView3 = findViewById(R.id.textView3);

        inicializaComponentes();
        eventoClicks();


        auth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d("AUTH", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d("AUTH", "onAuthStateChanged:signed_out");
                }
            }
        };

    }

    private void inicializaComponentes() {
        btnLogin = (Button) findViewById(R.id.btnLogin);
        edtEmailLog = (EditText) findViewById(R.id.edtEmailLog);
        edtSenhaLog = (EditText) findViewById(R.id.edtSenhaLog);

    }

    private void eventoClicks() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailLog = edtEmailLog.getText().toString().trim();
                String senhalog = edtSenhaLog.getText().toString().trim();
                if(edtSenhaLog.getText().toString().equals(null)) {
                    Toast.makeText(getApplicationContext(),"Senha não conferem", Toast.LENGTH_SHORT).show();
                }else {
                    eventoLogin(emailLog,senhalog);
                }
            }
        });
        /*pager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(login.this, swiper.class);
                startActivity(c);

            }
        });
        ocr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent oc = new Intent(login.this, ocr.class);
                startActivity(oc);

            }
        });*/
    }

    private void eventoLogin(final String emaillog, String senhalog) {
        auth.signInWithEmailAndPassword(emaillog, senhalog).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"Falha no Login", Toast.LENGTH_SHORT).show();
                }else{
                    PerfilAux(emaillog);

                }
            }
        });
    }

    public void PerfilAux(String emailLog){
        final String emaillogaux = emailLog;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Usuario");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String userName;
                String userAux = null;



                for (DataSnapshot chidSnap : dataSnapshot.getChildren()) {

                    String emailTxt = String.valueOf(chidSnap.child("email").getValue());
                    userName = String.valueOf(chidSnap.child("nome").getValue());
                    RALogged = String.valueOf(chidSnap.child("ra").getValue());

                    if (emailTxt.equals(emaillogaux)) {
                        userAux = userName;
                        break;
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Error login", Toast.LENGTH_SHORT).show();
                    }
                }
                Intent i = new Intent(login.this, activity_index.class);
                i.putExtra("emaillogaux", emaillogaux);
                i.putExtra("NomeNav", userAux);
                startActivity(i);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        limparcampos();

    }

    public void limparcampos(){
        edtEmailLog.setText("");
        edtSenhaLog.setText("");

    }

    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            auth.removeAuthStateListener(authStateListener);
        }
    }

    public void chamarEsqueceuSenha(View v){

        Intent telaEsqueceuSenha = new Intent (this,esqueceusenha.class);
        startActivity(telaEsqueceuSenha);

    }



}
