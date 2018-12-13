package com.example.nicolas.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.AppCompatEditText;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.UUID;

public class SignUp extends FirebaseActivity {

    EditText edtNome;
    EditText edtEmail;
    EditText edtRA;
    EditText edtSenha;
    EditText edtConfSenha;
    Button btnSalvar;

    private FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        inicializaComponentes();

        eventoClicks();

    }

    private void eventoClicks() {
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString().trim();
                String senha = edtSenha.getText().toString().trim();
                if(!edtSenha.getText().toString().equals(edtConfSenha.getText().toString())) {
                    Toast.makeText(getApplicationContext(),"Senhas não estão iguais", Toast.LENGTH_SHORT).show();

                }else if(!edtSenha.getText().toString().isEmpty()&&
                        edtSenha.getText().length()<=5||
                        !edtConfSenha.getText().toString().isEmpty()&&
                        edtConfSenha.getText().length()<=5) {
                    Toast.makeText(getApplicationContext(),"Senha com no minimo 6 digitos", Toast.LENGTH_SHORT).show();

                }
                else if(!edtNome.getText().toString().isEmpty()&&
                        !edtEmail.getText().toString().isEmpty()&&
                        !edtRA.getText().toString().isEmpty()&&
                        !edtSenha.getText().toString().isEmpty()&&
                        !edtConfSenha.getText().toString().isEmpty()&&
                        edtSenha.getText().length()>=6 &&
                        edtConfSenha.getText().length()>=6) {
                    //Toast.makeText(getApplicationContext(),"Salvar", Toast.LENGTH_SHORT).show();
                    CriarUser(email,senha);
                }else if(edtNome.getText().toString().isEmpty()||
                        edtEmail.getText().toString().isEmpty()||
                        edtRA.getText().toString().isEmpty()||
                        edtSenha.getText().toString().isEmpty()||
                        edtConfSenha.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Preencher todos os campos", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Erro para criar usuario", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void CriarUser(String email, String senha) {
        auth.createUserWithEmailAndPassword(email,senha).addOnCompleteListener(SignUp.this,
                new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    UserCampos u = new UserCampos();
                    u.setUid(UUID.randomUUID().toString());
                    u.setNome(edtNome.getText().toString());
                    u.setEmail(edtEmail.getText().toString());
                    u.setSenha(edtSenha.getText().toString());
                    u.setRa(edtRA.getText().toString());
                    databaseReference.child("Usuario").child(u.getUid()).setValue(u);
                    alert("Usuario Cadastrado com sucesso!");
                    ClearFields();
                    Intent i = new Intent(SignUp.this,login.class);
                    startActivity(i);

                }else{
                    alert("Erro no cadastro");
                }
            }
        });
    }

    private void alert(String msg){
        Toast.makeText(SignUp.this,msg,Toast.LENGTH_SHORT).show();
    }

    private void inicializaComponentes(){
        edtNome = (EditText) findViewById(R.id.edtNome);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtRA = (EditText) findViewById(R.id.edtRA);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        edtConfSenha = (EditText) findViewById(R.id.edtConfSenha);
        btnSalvar = (Button) findViewById(R.id.btnSalvarCad);


    }

    public void ClearFields(){
        edtNome.setText("");
        edtEmail.setText("");
        edtRA.setText("");
        edtSenha.setText("");
        edtConfSenha.setText("");
    }

    protected void onStart(){
        super.onStart();
        auth = ConexaoLogin.getFirebaseAuth();
    }

}
