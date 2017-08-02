package com.android.chatmessenger.chatmessenger.helper;


import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissao {

    public static boolean validaPermissoes(int requestCode, Activity activity, String[] permissoes){

        //verificando a versao API do android
        if(Build.VERSION.SDK_INT > 18){

            List<String> listaPermissoes = new ArrayList<String>();

            /*Vai percorrer as permissoes passadas, verificando uma a uma se ja tem a permissao liberada*/

            for(String permissao: permissoes){

                //caso tenha a permissao retorna TRUE
                Boolean validaPermissao = ContextCompat.checkSelfPermission(activity, permissao) == PackageManager.PERMISSION_GRANTED; //verifica para a activity passada se tem a permissao liberada.
                // Se  nao tiver ele vai guarda numa lista para slicitar pemissao
                if(!validaPermissao) {
                    listaPermissoes.add(permissao);
                }
            }
            //Caso a lista esteja vazia, nao pedira permissao
            if(listaPermissoes.isEmpty()){
                return true;
            }

            //convertendo a lista em array de string
            String[] novaspermissoes =  new String[listaPermissoes.size()];
            listaPermissoes.toArray(novaspermissoes);

            //Solicitando as permissoes
            ActivityCompat.requestPermissions(activity, novaspermissoes, requestCode);

        }
            return true;

    }
}
