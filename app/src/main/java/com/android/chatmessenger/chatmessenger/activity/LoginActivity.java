package com.android.chatmessenger.chatmessenger.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.chatmessenger.chatmessenger.R;
import com.android.chatmessenger.chatmessenger.helper.Preferencias;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;
import java.util.Random;
import java.util.StringTokenizer;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextTelefone;
    private EditText editTextDDI;
    private EditText editTextDDD;
    private Button buttonCadastrar;
    private EditText editTextNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextTelefone = (EditText) findViewById(R.id.editTextTel_Id);
        editTextDDI = (EditText) findViewById(R.id.editTextDDI_Id);
        editTextDDD = (EditText) findViewById(R.id.editTextDDD_Id);
        editTextNome = (EditText) findViewById(R.id.editTextNome_Id);

        buttonCadastrar = (Button) findViewById(R.id.buttonCadastrar_Id);

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


        //Evento do butao cadastrar
        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeUsuario = editTextNome.getText().toString();
                String telefoneCompleto =
                                editTextDDI.getText().toString()+
                                editTextDDD.getText().toString()+
                                editTextTelefone.getText().toString();


                String telefoneSemFormatacao;
                telefoneCompleto = telefoneCompleto.replace("+","");
                telefoneSemFormatacao = telefoneCompleto.replace("-", "");

                //Gerar Token
                Random randomico = new Random();
                int numeroRandomico = randomico.nextInt(9999-1000)+1000; //forçado a geração de 4 digitos;

                String token = String.valueOf(numeroRandomico);
                String mensagemEnvio = "Chat Messenger Código de confirmação: " + token;

                //envio do sms

                enviaSMS("+" + telefoneSemFormatacao, mensagemEnvio);


                //salvando dados para validação
                Preferencias preferencias = new Preferencias(getApplicationContext());

                preferencias.salvarUsuarioPreferencias(nomeUsuario, telefoneSemFormatacao, token);

                HashMap<String, String> usuario = preferencias.getDadosUsuarios(); //Salvando hashmap de preferencias com os dados do usuario no hassmap usuarios.

                Log.i("TOKEN", "T:" + usuario.get("nome")+ usuario.get("telefone"));
            }
        });

    }

    //Metodo envio SMS
    private boolean enviaSMS(String telefone, String mensagem){
        try {

            SmsManager smsManager = SmsManager.getDefault(); //recuperando a instacia da classe
            smsManager.sendTextMessage(telefone,null, mensagem, null, null );

            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
