package com.android.chatmessenger.chatmessenger.helper;


import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class Preferencias {

    private Context contexto;
    private SharedPreferences preferences;
    private static final String NOME_ARQIVO = "chatmessenger.preferencias";
    private  static  final int MODE = 0;
    private static final String CHAVE_NOME = "nome";
    private  static  final String CHAVE_TELEFONE = "telefone";
    private  static  final String CHAVE_TOKEN = "token";

    private SharedPreferences.Editor editor;



    public Preferencias(Context contextParametro){

        contexto = contextParametro;
        preferences = contexto.getSharedPreferences(NOME_ARQIVO, MODE);
        editor = preferences.edit(); // editor para inserir ou remover itens de preferencias

    }

    //salvando os dados do usuario no arquivo de preferencia.
    public void salvarUsuarioPreferencias( String nome, String telefone, String token){
        editor.putString(CHAVE_NOME, nome);
        editor.putString(CHAVE_TELEFONE, telefone);
        editor.putString(CHAVE_TOKEN, token);
        editor.commit();
    }

    //metodo para retornar as preferencias
    public HashMap<String, String> getDadosUsuarios(){
        HashMap<String, String> dadosUsuario = new HashMap<>(); //Lista com os dados em String

        //defino a chave e com getString do preference chamo o valor prenchido no preference
        dadosUsuario.put(CHAVE_NOME, preferences.getString(CHAVE_NOME, null));
        dadosUsuario.put(CHAVE_TELEFONE, preferences.getString(CHAVE_TELEFONE, null));
        dadosUsuario.put(CHAVE_TOKEN, preferences.getString(CHAVE_TOKEN, null));

        return dadosUsuario;
    }
}
