package com.android.chatmessenger.chatmessenger.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.chatmessenger.chatmessenger.R;
import com.android.chatmessenger.chatmessenger.helper.Preferencias;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;

public class ValidadorActivity extends AppCompatActivity {

    private EditText editTextValidador;
    private Button buttonValidador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validador);

        editTextValidador = (EditText) findViewById(R.id.editTextCodValidacao_Id);
        buttonValidador = (Button) findViewById(R.id.buttonValidar_Id);
        //validar = (Button) findViewById(R.id.buttonCadastrar_Id);

        //Criando mascara para aceitar apenas 4 digitos
        SimpleMaskFormatter simpleMaskCodigoValidacao = new SimpleMaskFormatter("NNNN");
        MaskTextWatcher maskTextCodigoValidacao = new MaskTextWatcher(editTextValidador, simpleMaskCodigoValidacao);
        editTextValidador.addTextChangedListener(maskTextCodigoValidacao);

        //Evento ao click no botao

        buttonValidador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //recuperar dados das preferencias do usuario.
                Preferencias preferencias = new Preferencias(getApplicationContext());

                HashMap<String, String> usuario = preferencias.getDadosUsuarios();

                String tokenGerado = usuario.get("token"); //recuperando apenas o token gerado.

                String tokenDigitado = editTextValidador.getText().toString();

                //Comparado token digitado com o token gerado
                if (tokenDigitado.equals(tokenGerado)){
                    Toast.makeText(getApplicationContext(), "token VALIDADO", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "token NAO VALIDADO", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
