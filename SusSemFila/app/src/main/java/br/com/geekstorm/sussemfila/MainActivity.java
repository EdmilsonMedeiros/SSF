package br.com.geekstorm.sussemfila;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ImageView agendamento;
    TextView nomeusuario, cpfusuario, btsair;

    private RequestQueue requestQueue;
    private static final String URLespecialidade = "http://sussemfila.000webhostapp.com/especialidades.php ";
    private StringRequest request;

    ArrayList<Especialidade> especialidadesArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nomeusuario = (TextView) findViewById(R.id.Main_nomeuser);
        cpfusuario = (TextView) findViewById(R.id.Main_cpfuser);
        btsair = (TextView) findViewById(R.id.Main_sair);
        agendamento = findViewById(R.id.Main_btagendamento);

        requestQueue = Volley.newRequestQueue(this);

        Intent a = getIntent();

        Bundle bundle = a.getBundleExtra("a1");
        Paciente usuario = (Paciente) bundle.getSerializable("paciente");

        nomeusuario.setText(usuario.getNome());
        cpfusuario.setText("CPF: " + usuario.getCpf());

        btsair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sair = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(sair);
            }
        });

        agendamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetornEspecialidade();
                Intent agendamento = new Intent(MainActivity.this, AgendamentoActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("especialidade", especialidadesArray);
                agendamento.putExtra("bundle", bundle);
                //Fim do metodo
                startActivity(agendamento);

            }
        });
    }


    private void RetornEspecialidade(){
        request = new StringRequest(Request.Method.POST, URLespecialidade, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject objeto = jsonArray.getJSONObject(i);
                        int id = objeto.getInt("id");
                        String descricao = objeto.getString("descricao");
                        Especialidade especialidade = new Especialidade(id,descricao);
                        especialidadesArray.add(especialidade);
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
                return null;
            }
        };
        requestQueue.add(request);
    }}



