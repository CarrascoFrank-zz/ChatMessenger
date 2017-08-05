package com.android.chatmessenger.chatmessenger.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import com.android.chatmessenger.chatmessenger.helper.Permissao;
import com.android.chatmessenger.chatmessenger.helper.Preferencias;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Random;
import java.util.StringTokenizer;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference databaseReferenceFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseReferenceFirebase = ConfiguracaoFirebase.getFirebase();
        databaseReferenceFirebase.child("pontos").setValue("800");


        }


        public void abrirCadastroUsuario(View view){

            Intent intent = new Intent(getApplicationContext(), CadastroUsuarioActivity.class);
            startActivity(intent);

        }


}
