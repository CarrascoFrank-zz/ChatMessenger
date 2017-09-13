package com.android.chatmessenger.chatmessenger.fragment;


import android.content.pm.ProviderInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.chatmessenger.chatmessenger.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContatosFragment extends Fragment {


    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<String> contatos;


    public ContatosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Instanciando contatos
        contatos = new ArrayList<>();
        contatos.add("Frank Carrasco");
        contatos.add("Julia Costa");
        contatos.add("Alejandra Carrasco");


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contatos, container, false);

        listView = (ListView)  view.findViewById(R.id.lv_contatos_id);
        adapter = new ArrayAdapter(
                getActivity(), //pegando a activity
                R.layout.lista_contato, //definindo Layout
                contatos //defindo o conteudo
        );

        listView.setAdapter(adapter);
        return view;
    }

}
