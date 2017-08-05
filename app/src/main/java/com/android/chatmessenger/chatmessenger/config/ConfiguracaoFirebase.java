package com.android.chatmessenger.chatmessenger.config;


import android.provider.ContactsContract;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public final class ConfiguracaoFirebase {

    private static DatabaseReference databaseReferenceFirebase;

    public static DatabaseReference getFirebase(){


        //caso seja nula ela pega a referencia
        if (databaseReferenceFirebase == null){

            databaseReferenceFirebase = FirebaseDatabase.getInstance().getReference();
        }

        return databaseReferenceFirebase;
    }
}
