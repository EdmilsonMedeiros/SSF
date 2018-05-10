package br.com.geekstorm.sussemfila;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class ConsultaActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private static final String URL = "http://sussemfila.000webhostapp.com/Consulta.php ";
    private StringRequest request;
    private String cpf;

    private String nomepaciente= "";
    private TextView titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        titulo = (TextView) findViewById(R.id.Consulta_titulo);

        requestQueue = Volley.newRequestQueue(this);
        Intent a = getIntent();
        cpf = a.getExtras().getString("cpf");

        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.names().get(0).equals("confirmado")) {
                        nomepaciente = jsonObject.getString("Paciente");
                        titulo.setText(nomepaciente);
                    } else {
                        Toast.makeText(getApplicationContext(), "Error: " + jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                        Intent voltar = new Intent(ConsultaActivity.this, MainActivity.class);
                        startActivity(voltar);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("cpf", cpf);
                return hashMap;
            }
        };
        requestQueue.add(request);

    }


}