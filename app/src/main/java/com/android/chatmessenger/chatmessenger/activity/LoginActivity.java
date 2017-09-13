package com.android.chatmessenger.chatmessenger.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.chatmessenger.chatmessenger.R;
import com.android.chatmessenger.chatmessenger.config.ConfiguracaoFirebase;
import com.android.chatmessenger.chatmessenger.helper.Base64Custom;
import com.android.chatmessenger.chatmessenger.helper.Permissao;
import com.android.chatmessenger.chatmessenger.helper.Preferencias;
import com.android.chatmessenger.chatmessenger.model.Usuario;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Random;
import java.util.StringTokenizer;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference databaseReferenceFirebase;

    private EditText editTextEmail;
    private EditText editTextSenha;
    private Button buttonLogar;
    private Usuario usuario;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        verificarUsuarioLogado();

        databaseReferenceFirebase = ConfiguracaoFirebase.getFirebase();

        editTextEmail = (EditText) findViewById(R.id.editText_email_id);
        editTextSenha = (EditText) findViewById(R.id.editText_senha_id);
        buttonLogar = (Button) findViewById(R.id.bt_logar_id);

        //evento ao clicar em logar
            buttonLogar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    usuario = new Usuario();
                    usuario.setEmail(editTextEmail.getText().toString());
                    usuario.setSenha(editTextSenha.getText().toString());

                    if ( (usuario.getEmail().isEmpty()) || (usuario.getSenha().isEmpty()) ){

                        Toast.makeText(getApplicationContext(), "Digite um e-mail e uma senha", Toast.LENGTH_LONG).show();

                    }else {

                        validarLogin();
                    }

                }
            });


    }

        private void verificarUsuarioLogado(){
            firebaseAuth = ConfiguracaoFirebase.getFirebaseAuth(); //instanciando o banco

            //verificando se ele a esta logado.
            if (firebaseAuth.getCurrentUser() != null){
                abrirActivityPrincipal();
            }
        }

        public void abrirCadastroUsuario(View view){

            Intent intent = new Intent(getApplicationContext(), CadastroUsuarioActivity.class);
            startActivity(intent);
            finish();
        }

        private void abrirActivityPrincipal(){

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        private void validarLogin(){

            firebaseAuth = ConfiguracaoFirebase.getFirebaseAuth();

            firebaseAuth.signInWithEmailAndPassword(
                    usuario.getEmail(),
                    usuario.getSenha()
            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){

                        Preferencias preferencias = new Preferencias(LoginActivity.this);
                        String identificadorUsuarioLogado = Base64Custom.codificarBase64(usuario.getEmail());
                        preferencias.salvarDados(identificadorUsuarioLogado);

                        abrirActivityPrincipal();

                    }else{

                        String erroExcecao;

                        try {
                            throw task.getException();
                        }catch (FirebaseAuthInvalidUserException e){
                            erroExcecao = "E-mail invalidou ou nao existe";
                        }catch (FirebaseAuthInvalidCredentialsException e){
                            erroExcecao = "Senha Invalida, tente novamente";
                        }catch (Exception e){
                            erroExcecao = "Erro ao logar, tente novamente";
                            e.printStackTrace();
                        }

                        Toast.makeText(getApplicationContext(), "ERRO: " + erroExcecao, Toast.LENGTH_LONG).show();
                    }
                }
            });

        }




}
