package com.android.chatmessenger.chatmessenger.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.chatmessenger.chatmessenger.R;
import com.android.chatmessenger.chatmessenger.config.ConfiguracaoFirebase;
import com.android.chatmessenger.chatmessenger.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText editTextNome;
    private EditText editTextEmail;
    private EditText editTextSenha;
    private Button buttonCadastrar;
    private Usuario usuario;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        editTextNome = (EditText) findViewById(R.id.editText_cadastro_nome_id);
        editTextEmail = (EditText) findViewById(R.id.editText_cadastro_email_id);
        editTextSenha = (EditText) findViewById(R.id.editText_cadastro_senha_id);
        buttonCadastrar = (Button)  findViewById(R.id.bt_cadastrar_id);

        //Evento de click no buttonCadastrar

        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usuario = new Usuario();
                usuario.setNome(editTextNome.getText().toString());
                usuario.setEmail(editTextEmail.getText().toString());
                usuario.setSenha(editTextSenha.getText().toString());
                cadastrarUsuario();


            }
        });



    }

    //metodo cadastrar usuario
    private void cadastrarUsuario(){

        firebaseAuth = ConfiguracaoFirebase.getFirebaseAuth();
        firebaseAuth.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Sucesso ao cadastrar usuario",Toast.LENGTH_LONG).show();

                    FirebaseUser firebaseUser = task.getResult().getUser();
                    usuario.setId(firebaseUser.getUid());
                    usuario.salvar();

                    firebaseAuth.signOut(); //deslogando o usuario apos o cadastrato

                    finish();

                }else{

                    String erroExcecao;

                    try{

                        throw task.getException(); //recuperando a exceção

                    } catch (FirebaseAuthWeakPasswordException e) {
                        erroExcecao = "Digite uma senha mais forte, contendo mais caracteres (numeros e letras)";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        erroExcecao = "O e-mail informado é invalido, Digite novamente.";
                    }catch (FirebaseAuthUserCollisionException e){
                        erroExcecao = "O e-email informado ja foi cadastrado, Tente usar outro e-mail";
                    }catch (Exception e){
                        erroExcecao = "Erro ao efetuar o cadastro";
                        e.printStackTrace();
                    }

                    Toast.makeText(getApplicationContext(), "ERRO: " + erroExcecao, Toast.LENGTH_LONG).show();

                }
            }
        });

    }

}
