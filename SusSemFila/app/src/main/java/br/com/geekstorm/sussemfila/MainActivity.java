package br.com.geekstorm.sussemfila;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ImageView agendamento;
    TextView nomeusuario, cpfusuario, btsair;

    private RequestQueue requestQueue;
    private static final String URLespecialidade = "http://sussemfila.000webhostapp.com/especialidades.php ";
    private StringRequest request;

    private Especialidade especialidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nomeusuario = (TextView) findViewById(R.id.Main_nomeuser);
        cpfusuario = (TextView) findViewById(R.id.Main_cpfuser);
        btsair = (TextView)  findViewById(R.id.Main_sair);
        agendamento = findViewById(R.id.Main_btagendamento);

        requestQueue = Volley.newRequestQueue(this);

        Intent a = getIntent();

        Bundle bundle = a.getBundleExtra("a1");
        Paciente usuario = (Paciente) bundle.getSerializable("paciente");

        nomeusuario.setText(usuario.getNome());
        cpfusuario.setText("CPF: "+usuario.getCpf());

        btsair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sair = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(sair);
            }
        });

        agendamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request = new StringRequest(Request.Method.POST, URLespecialidade, new Response.Listener<String>() {

                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.names().get(0).equals("confirmado")) {
                            Intent agendamento = new Intent(MainActivity.this, AgendamentoActivity.class);

                            String id = jsonObject.getString("id");
                            String descricao = jsonObject.getString("cpf");

                            //Criando método para passar a Classe pessoa
                            Bundle bundle = new Bundle();
                            especialidade = new Especialidade(id, descricao);

                        }else {}
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

                        return hashMap;
                    }
                };
                requestQueue.add(request);
            }});


}
}
