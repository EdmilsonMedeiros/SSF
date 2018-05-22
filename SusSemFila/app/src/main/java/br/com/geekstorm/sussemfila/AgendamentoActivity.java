package br.com.geekstorm.sussemfila;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AgendamentoActivity extends AppCompatActivity {

    //Widgets do Layout
    private Spinner especialidade, medico;
    Button sair;
    //Objetos de Requisição do servidor
    private RequestQueue requestQueue;
    private StringRequest request;
    //Url para onde a requisição será enviada
    private static final String URLespecialidade = "http://sussemfila.000webhostapp.com/especialidades.php ";
    //Array e objeto de Especialidade
    ArrayList<Especialidade> especialidadesArray = new ArrayList<>();
    Especialidade especialyt;
    long idMedico;
    //Sistema de Sessão
    private UsuarioSessao sessao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendamento);

        //Efetuando ligação do Objeto Sessão ao Contexto
        this.sessao = new UsuarioSessao(getApplicationContext());
        //Checando se o usuario esta logado
        if(sessao.checkLogin())
            finish();

        //Ligando requisição do Sistema pelo metodo Volley
        requestQueue = Volley.newRequestQueue(this);
        //Executando função para popular Spinner Especialidade
        this.SpinnerEspecialidade();

        //Efetuando ligação dos objetos do layout com objetos da classe
        especialidade = (Spinner) findViewById(R.id.Agendamento_especialidade);
        sair = (Button) findViewById(R.id.Agendamento_sair);
        medico = (Spinner) findViewById(R.id.Agendamento_medico);


        //Spinner de Especialidade
        ArrayAdapter<Especialidade> adapter = new ArrayAdapter<Especialidade>(this,android.R.layout.simple_spinner_dropdown_item, especialidadesArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        especialidade.setAdapter(adapter);
        //Onclick do Spinner
        especialidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                idMedico = especialidade.getSelectedItemId();
                /*
                POPULAR OUTRO SPINNER APARTIR DO RESULTADO
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>([SUA ACTIVITY AQUI].this,android.R.layout.simple_spinner_item, minhalista);
                */

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });

        //Botão Sair
        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              finish();
            }
        });


    }
    //Função para popular Spinner de Especialidade
    private void SpinnerEspecialidade(){
        request = new StringRequest(Request.Method.POST, URLespecialidade, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject objeto = jsonArray.getJSONObject(i);
                        especialyt = new Especialidade(objeto.getInt("id"),objeto.getString("descricao"));
                        especialidadesArray.add(especialyt);
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
    }

    //Spinner medico
    private void SpinnerMedico(){
        request = new StringRequest(Request.Method.POST, URLespecialidade, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject objeto = jsonArray.getJSONObject(i);
                        especialyt = new Especialidade(objeto.getInt("id"),objeto.getString("descricao"));
                        especialidadesArray.add(especialyt);
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
                /*hashMap.put("idmedico",idMedico);
                return hashMap;*/
                return null;
            }
        };
        requestQueue.add(request);
    }
}
