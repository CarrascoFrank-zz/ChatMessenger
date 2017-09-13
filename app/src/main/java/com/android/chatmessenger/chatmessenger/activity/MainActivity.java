package com.android.chatmessenger.chatmessenger.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.chatmessenger.chatmessenger.Adapter.TabAdapter;
import com.android.chatmessenger.chatmessenger.R;
import com.android.chatmessenger.chatmessenger.config.ConfiguracaoFirebase;
import com.android.chatmessenger.chatmessenger.helper.Base64Custom;
import com.android.chatmessenger.chatmessenger.helper.Preferencias;
import com.android.chatmessenger.chatmessenger.helper.SlidingTabLayout;
import com.android.chatmessenger.chatmessenger.model.Contato;
import com.android.chatmessenger.chatmessenger.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    private Toolbar toolbarMainActivity;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private String identificadorContato;
    private DatabaseReference databaseReferenceFirebase;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = ConfiguracaoFirebase.getFirebaseAuth();

        toolbarMainActivity = (Toolbar) findViewById(R.id.toolbar_principal_id);
        toolbarMainActivity.setTitle("ChatMessesnger");
        setSupportActionBar(toolbarMainActivity);

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.stl_tabs_id);
        viewPager = (ViewPager) findViewById(R.id.vp_pagina_id);

        //configuração slidingTabs
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this,R.color.colorAccent));

        //configurar o adapter
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);

        slidingTabLayout.setViewPager( viewPager );

    }

    private void abrirLoginActivity() {

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater(); //este metodo getMenuInflater cria o objeto inflater ja com o contexto da nossa aplicação.
        //inflater serve para mostrar o menu
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_sair_id:
                deslogarUsuario();
                return true;
            case R.id.actions_settings_id:
                return true;
            case R.id.item_adicionar_id:
                abrirCadastroContato();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void abrirCadastroContato(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

        //configurando o Dialog

        alertDialog.setTitle("Novo Contato");
        alertDialog.setMessage("E-mail do usuário");
        alertDialog.setCancelable(false);

        final EditText editTextDialog = new EditText(MainActivity.this);

        alertDialog.setView( editTextDialog );

        //configurações dos botoes do Dialog

        alertDialog.setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //evento ao adicionar

                String emailContato = editTextDialog.getText().toString();

                if ( emailContato.isEmpty() ){
                    Toast.makeText(getApplicationContext(),"Campo E-mail vazio", Toast.LENGTH_LONG).show();
                }else{
                    //verificar se o usuario consultado esta cadastrado.
                    identificadorContato = Base64Custom.codificarBase64(emailContato);

                    //recuperar a instacia do banco
                    databaseReferenceFirebase = ConfiguracaoFirebase.getFirebase();
                    databaseReferenceFirebase = databaseReferenceFirebase.child("usuarios").child(identificadorContato);

                    databaseReferenceFirebase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if ( dataSnapshot.getValue() != null){

                                //recuperand dados do contato a ser adicionado
                               Usuario usuarioContato =  dataSnapshot.getValue(Usuario.class);


                                //recuperando identificacao do usuario logado
                                Preferencias preferencias = new Preferencias(getApplicationContext());
                                String identificadorUsuarioLogado = preferencias.getIdentificador();


//                                firebaseAuth.getCurrentUser().getEmail();
                                databaseReferenceFirebase = ConfiguracaoFirebase.getFirebase();
                                databaseReferenceFirebase = databaseReferenceFirebase
                                        .child("contatos")
                                        .child(identificadorUsuarioLogado)
                                        .child(identificadorContato);


                                Contato contato = new Contato();
                                contato.setIdentificadorUsuario(identificadorContato);
                                contato.setEmail(usuarioContato.getEmail());
                                contato.setNome(usuarioContato.getNome());

                                databaseReferenceFirebase.setValue(contato);
                            }else{
                                Toast.makeText(getApplicationContext(),"Contato não encontrado", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

            }
        });


        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //evento ao cancelar

            }
        });

        alertDialog.create();
        alertDialog.show();
    }


    private void deslogarUsuario(){

        firebaseAuth.signOut();
        abrirLoginActivity();

    }

}
