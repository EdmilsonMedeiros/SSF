package br.com.geekstorm.sussemfila;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText user_cpf, user_senha;
    private Button botao_entrar;
    private TextView botao_cadastrar;
    private String nomedocara = "";
    private String cpfdocara = "";

    Paciente paciente;

    ProgressDialog progress;
    Bundle bundle;
    private RequestQueue requestQueue;
    private static final String URL = "http://sussemfila.000webhostapp.com/acesso.php ";
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.user_cpf = findViewById(R.id.Login_cpf);
        this.user_senha = findViewById(R.id.Login_pass);
        this.botao_entrar = (Button) findViewById(R.id.Login_btacessar);
        this.botao_cadastrar = (TextView) findViewById(R.id.Login_btCadastrar);

        requestQueue = Volley.newRequestQueue(this);

        botao_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent telacadastro = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(telacadastro);
            }
        });

        botao_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(user_cpf.getText().toString().isEmpty() || user_senha.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Você não pode acessar sem todas as informações", Toast.LENGTH_LONG).show();
                }else{
                    progress = new ProgressDialog(LoginActivity.this);
                    progress.setTitle("Aguarde...");
                    progress.show();

                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.names().get(0).equals("confirmado")) {
                                Intent main = new Intent(LoginActivity.this, MainActivity.class);
                                nomedocara = jsonObject.getString("nome");
                                cpfdocara = jsonObject.getString("cpf");

                                //Criando método para passar a Classe pessoa
                                Bundle bundle = new Bundle();
                                paciente = new Paciente(nomedocara,cpfdocara);

                                bundle.putSerializable("paciente", paciente);
                                main.putExtra("a1",bundle);
                                //Fim do metodo

                                main.putExtra("nome", nomedocara);
                                progress.cancel();
                                startActivity(main);
                            }else {
                                progress.cancel();
                                Toast.makeText(getApplicationContext(), "Error: "+jsonObject.getString("error"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("cpf", user_cpf.getText().toString());
                        hashMap.put("senha", user_senha.getText().toString());

                        return hashMap;
                    }
                };
                requestQueue.add(request);
            }}
        });


    }
}
