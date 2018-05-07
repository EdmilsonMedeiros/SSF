package br.com.geekstorm.sussemfila;

import android.app.DownloadManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class CadastroActivity extends AppCompatActivity {

    private AutoCompleteTextView nome,endereco, cpf, sus, email, pass;
    private Button botaocadastrar, botaocancelar;

    RequestQueue requestQueue;
    String insertUrl = "http://sussemfila.000webhostapp.com/Cadastro.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        this.nome = findViewById(R.id.Cadastro_nome);
        this.endereco = findViewById(R.id.Cadastro_endereço);
        this.cpf = findViewById(R.id.Cadastro_cpf);
        this.sus = findViewById(R.id.Cadastro_sus);
        this.email = findViewById(R.id.Cadastro_email);
        this.pass = findViewById(R.id.Cadastro_pass);
        this.botaocadastrar = (Button) findViewById(R.id.Cadastro_cadastrar);
        this.botaocancelar = (Button) findViewById(R.id.Cadastro_cancelar);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        botaocadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                        return parameters;
                    }
                };
                requestQueue.add(request);

            }
        });

    }
}
