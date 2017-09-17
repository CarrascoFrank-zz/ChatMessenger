package com.android.chatmessenger.chatmessenger.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.chatmessenger.chatmessenger.R;
import com.android.chatmessenger.chatmessenger.model.Contato;

import java.util.ArrayList;
import java.util.List;

public class ContatoAdapter extends ArrayAdapter<Contato>{

    private ArrayList<Contato> contatos;
    private Context context;

    public ContatoAdapter(@NonNull Context c, @NonNull ArrayList<Contato> objects) {
        super(c, 0, objects);
        this.contatos = objects;
        this.context = c;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;

        //verificando se a lista esta vazia
        if ( contatos != null){
            //inicializando objeto para montagem da view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);


            //Montando o view apartir do XML
            view = inflater.inflate(R.layout.lista_contato, parent, false);

            //Recuperando Elemento para exibição
            TextView nomeContato = (TextView) view.findViewById(R.id.TextViewNome_Id);
            TextView emailContato = (TextView) view.findViewById(R.id.textViewEmail_Id);

            Contato contato = contatos.get(position);
            nomeContato.setText(contato.getNome());
            emailContato.setText(contato.getEmail());
        }

        return view;

    }
}
