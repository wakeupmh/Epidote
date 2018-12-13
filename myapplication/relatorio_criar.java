package com.example.nicolas.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.nicolas.myapplication.login.RALogged;

public class relatorio_criar extends FirebaseActivity {

    EditText edtNome;
    EditText edtResumo;
    EditText edtRA;
    EditText edtGrupo;

//    public FirebaseDatabase firebaseDatabase;
//    public DatabaseReference databaseReference;
    public List<relatorioCampos> listRelatorio = new ArrayList<relatorioCampos>();
    public ArrayAdapter<relatorioCampos> arrayAdapterRelatorio;

    relatorioCampos relatorioSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_criar);
        inicializarFirebase();

    }


    public boolean onCreateOptionsMenu(Menu menu2) {
        getMenuInflater().inflate(R.menu.menu_relatorio_criar,menu2);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item2) {
        int id = item2.getItemId();
        edtNome = (EditText) findViewById(R.id.edtNome);
        //edtRA = (EditText) findViewById(R.id.edtRA);
        edtGrupo = (EditText) findViewById(R.id.edtGrupo);
        edtResumo = (EditText) findViewById(R.id.edtResumo);
        if(id == R.id.menu_add){
            if (!edtNome.getText().toString().isEmpty()&&
                    !edtGrupo.getText().toString().isEmpty()&&
                    !edtResumo.getText().toString().isEmpty()){
                relatorioCampos p = new relatorioCampos();
                p.setUid(UUID.randomUUID().toString());
                p.setNome(edtNome.getText().toString());
                p.setResumo(edtResumo.getText().toString());
                p.setGrupo(edtGrupo.getText().toString());
                p.setRa(RALogged);
                databaseReference.child("Relatorio").child(p.getUid()).setValue(p);
                limpar_campos();
                Toast.makeText(getApplicationContext(), "Relatorio Criado", Toast.LENGTH_SHORT).show();

            }else if (edtNome.getText().toString().isEmpty()||
                    edtGrupo.getText().toString().isEmpty()||
                    edtResumo.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "Preencher todos os campos", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(), "Erro para salvar", Toast.LENGTH_SHORT).show();
            }



        }if(id == R.id.menu_list){
            Intent telaEsqueceuSenha = new Intent (this,relatorio_listagem.class);
            startActivity(telaEsqueceuSenha);
        }if(id == R.id.menu_index){

            Intent telaEsqueceuSenha = new Intent (this,activity_index.class);
            finish();
            startActivity(telaEsqueceuSenha);
        }
        return true;

    }


//    public void inicializarFirebase(){
//        FirebaseApp.initializeApp(relatorio_criar.this);
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference();
//    }


    public void limpar_campos(){
        edtNome.setText("");
        edtResumo.setText("");

        edtGrupo.setText("");
    }


}
