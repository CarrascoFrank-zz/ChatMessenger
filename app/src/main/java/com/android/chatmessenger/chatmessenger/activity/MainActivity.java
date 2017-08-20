package com.android.chatmessenger.chatmessenger.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.android.chatmessenger.chatmessenger.Adapter.TabAdapter;
import com.android.chatmessenger.chatmessenger.R;
import com.android.chatmessenger.chatmessenger.config.ConfiguracaoFirebase;
import com.android.chatmessenger.chatmessenger.helper.SlidingTabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    private Toolbar toolbarMainActivity;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;





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
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void deslogarUsuario(){

        firebaseAuth.signOut();
        abrirLoginActivity();

    }

}
