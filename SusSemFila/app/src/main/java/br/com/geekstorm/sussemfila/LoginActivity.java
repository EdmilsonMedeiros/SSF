package br.com.geekstorm.sussemfila;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView user_cpf, user_senha;
    private Button botao_entrar;
    private TextView botao_cadastrar;
    private String nomedocara = "";
    private String cpfdocara = "";

    private RequestQueue requestQueue;
    private static final String URL = "http://sussemfila.000webhostapp.com/acesso.php ";
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.user_cpf = findViewById(R.id.Login_cpf);
        this.user_senha = findViewById(R.id.Login_pass);
        this.botao_entrar = (Button) findViewById(R.id.Login_botaoentrar);
        this.botao_cadastrar = (TextView) findViewById(R.id.Login_botaocadastrar);

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

                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.names().get(0).equals("confirmado")) {
                                Intent main = new Intent(LoginActivity.this, MainActivity.class);
                                nomedocara = jsonObject.getString("nome");
                                cpfdocara = jsonObject.getString("cpf");
                                main.putExtra("cpf", cpfdocara);
                                main.putExtra("nome", nomedocara);
                                startActivity(main);
                            }else {
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
            }
        });


    }
}
