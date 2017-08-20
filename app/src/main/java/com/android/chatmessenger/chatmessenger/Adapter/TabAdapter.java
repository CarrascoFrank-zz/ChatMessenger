package com.android.chatmessenger.chatmessenger.Adapter;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.Switch;

import com.android.chatmessenger.chatmessenger.fragment.ContatosFragment;
import com.android.chatmessenger.chatmessenger.fragment.ConversasFragment;

public class TabAdapter extends FragmentStatePagerAdapter{

    private  String[] tituloAbas ={"CONVERSAS", "CONTATOS"}; //Titulo que ficaram nas abas



    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    //metodo para retornar qual fragment vai ser carregado
    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;

        switch (position){
            case 0 :
                fragment = new ConversasFragment();
                break;
            case 1 :
                fragment = new ContatosFragment();
                break;

        }

        return fragment;
    }

    //Quantidade de abas
    @Override
    public int getCount() {
        return tituloAbas.length;
    }

    //Recupera os titulos de cada uma das abas
    @Override
    public CharSequence getPageTitle(int position) {
       return tituloAbas[ position  ];
    }
}
