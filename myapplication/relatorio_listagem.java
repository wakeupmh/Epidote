package com.example.nicolas.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.BreakIterator;

import static com.example.nicolas.myapplication.activity_index.EmailLogged;
import static com.example.nicolas.myapplication.activity_index.NomeLogged;
import static com.example.nicolas.myapplication.activity_index.RALogged;



public class relatorio_listagem extends relatorio_criar {


    public ListView listaRelatorioEsp;
    public TextView textViewEmail,textViewNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_listagem);

        //Toast.makeText(getApplicationContext(), EmailLogged +" "+ NomeLogged, Toast.LENGTH_SHORT).show();
        

        edtNome = (EditText) findViewById(R.id.edtNome);
        edtRA = (EditText) findViewById(R.id.edtRA);
        edtGrupo = (EditText) findViewById(R.id.edtGrupo);
        edtResumo = (EditText) findViewById(R.id.edtResumo);
        listaRelatorioEsp = (ListView)findViewById(R.id.listaRelatorioEsp);

        inicializarFirebase();

        eventoDatabaseRelList();

        listaRelatorioEsp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                relatorioSelecionado = (relatorioCampos) parent.getItemAtPosition(position);

                String UUid = relatorioSelecionado.getUid();
                String RelNome = relatorioSelecionado.getNome();
                String RelRA = relatorioSelecionado.getRa();
                String RelResumo = relatorioSelecionado.getResumo();
                String RelGrupo = relatorioSelecionado.getGrupo();



                Intent segundaActivity = new Intent(relatorio_listagem.this, relatorio_especifico.class);
                segundaActivity.putExtra("RelNome",RelNome);
                segundaActivity.putExtra("RelRA",RelRA);
                segundaActivity.putExtra("RelResumo",RelResumo);
                segundaActivity.putExtra("RelGrupo",RelGrupo);
                segundaActivity.putExtra("UUid",UUid);

                startActivity(segundaActivity);

            }
        });
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_relatorio_list,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_novo){

            Intent telaEsqueceuSenha = new Intent (this,relatorio_criar.class);
            startActivity(telaEsqueceuSenha);
        }if(id == R.id.menu_indexRet){

            Intent telaInicial = new Intent (this,activity_index.class);
            finish();
            startActivity(telaInicial);

        }

        return true;
    }
    public void eventoDatabaseRelList(){
        databaseReference.child("Relatorio").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listRelatorio.clear();
                String RAUX = null;

                for(DataSnapshot objSnapshot:dataSnapshot.getChildren()){

                    RAUX = String.valueOf(objSnapshot.child("ra").getValue());
                    relatorioCampos p = objSnapshot.getValue(relatorioCampos.class);

                    if(RALogged!=null && RALogged.equals(RAUX)){

                        listRelatorio.add(p);

                    }
                    else{

                    }

                }
                arrayAdapterRelatorio = new ArrayAdapter<relatorioCampos>(relatorio_listagem.this,
                        android.R.layout.simple_list_item_1,listRelatorio){
                  @Override
                  public View getView(int position, View convertView, ViewGroup parent) {


                      TextView item = (TextView) super.getView(position, convertView, parent);
                      //TextView textView=(TextView) view.findViewById(android.R.id.t);

                      /*YOUR CHOICE OF COLOR*/
                      //textView.setTextColor(Color.BLUE);
                       item.setTextColor(Color.parseColor("#FFFFFF"));
                       item.setTypeface(item.getTypeface(), Typeface.BOLD);
                      return item;
                  }
                };


                listaRelatorioEsp.setAdapter(arrayAdapterRelatorio);
                
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
