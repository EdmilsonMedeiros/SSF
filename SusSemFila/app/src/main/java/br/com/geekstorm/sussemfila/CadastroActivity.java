package br.com.geekstorm.sussemfila;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class CadastroActivity extends AppCompatActivity {

    //Widgets do Layout
    private EditText nome,endereco, cpf, sus, email, pass;
    private Button botaocadastrar, botaocancelar;

    //Objetos de Requisição do servidor
    RequestQueue requestQueue;
    String insertUrl = "http://sussemfila.000webhostapp.com/Cadastro.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //Efetuando ligação dos objetos do layout com objetos da classe
        this.nome = findViewById(R.id.Cadastro_nome);
        this.endereco = findViewById(R.id.Cadastro_endereco);
        this.cpf = findViewById(R.id.Cadastro_cpf);
        this.sus = findViewById(R.id.Cadastro_sus);
        this.email = findViewById(R.id.Cadastro_email);
        this.pass = findViewById(R.id.Cadastro_pass);
        this.botaocadastrar = (Button) findViewById(R.id.Cadastro_btcadastrar);
        this.botaocancelar = (Button) findViewById(R.id.Cadastro_btcancelar);

        final String tokenUser = FirebaseInstanceId.getInstance().getToken();

        //Mascarando CPF
        cpf.addTextChangedListener(MaskUtil.insert(cpf,MaskUtil.MaskType.CPF));
        //Realizando requisição
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        //Botao cadastrar
        botaocadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(nome.getText().toString().isEmpty() || endereco.getText().toString().isEmpty() || cpf.getText().toString().isEmpty() || sus.getText().toString().isEmpty() || email.getText().toString().isEmpty() || pass.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Você possui campos ainda vazios!", Toast.LENGTH_LONG).show();
                }else{
                    if(cpf.length() < 14){
                        Toast.makeText(getApplicationContext(),"CPF invalido!", Toast.LENGTH_LONG).show();
                    }else{
                    StringRequest request =new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("nome",nome.getText().toString());
                        parameters.put("endereco",endereco.getText().toString());
                        parameters.put("numerocpf",cpf.getText().toString());
                        parameters.put("numerosus",sus.getText().toString());
                        parameters.put("email",email.getText().toString());
                        parameters.put("senha",pass.getText().toString());
                        parameters.put("token", tokenUser);

                        return parameters;
                    }
                };
                requestQueue.add(request);
                finish();
            }}
            }
        });

        botaocancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
