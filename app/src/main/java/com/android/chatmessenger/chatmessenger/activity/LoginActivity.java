package com.android.chatmessenger.chatmessenger.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.android.chatmessenger.chatmessenger.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextTelefone;
    private EditText editTextDDI;
    private EditText editTextDDD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextTelefone = (EditText) findViewById(R.id.editTextTel_Id);
        editTextDDI = (EditText) findViewById(R.id.editTextDDI_Id);
        editTextDDD = (EditText) findViewById(R.id.editTextDDD_Id);

        //mascara telefone
        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("NNNNN-NNNN");
        MaskTextWatcher maskTextTelefone = new MaskTextWatcher(editTextTelefone, simpleMaskTelefone);
        editTextTelefone.addTextChangedListener(maskTextTelefone);

        //Mascara DDI
        SimpleMaskFormatter simpleMaskDDI = new SimpleMaskFormatter("+NN");
        MaskTextWatcher maskTextDDI = new MaskTextWatcher(editTextDDI, simpleMaskDDI);
        editTextDDI.addTextChangedListener(maskTextDDI);

        //Mascara DDD
        SimpleMaskFormatter simpleMaskDDD = new SimpleMaskFormatter("NN");
        MaskTextWatcher maskTextDDD = new MaskTextWatcher(editTextDDD, simpleMaskDDD);
        editTextDDD.addTextChangedListener(maskTextDDD);


    }
}
