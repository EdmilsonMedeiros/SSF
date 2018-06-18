package br.com.geekstorm.sussemfila;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.List;
import java.util.Map;

public class HistoricoActivity extends AppCompatActivity {

    private ListView listview;
    private Agendamento agendamento;
    private ArrayList<Agendamento> list = new ArrayList<Agendamento>();;
    TextView msg;

    private String Url = "https://sussemfila.000webhostapp.com/getHistorico.php";
    private Request request;
    private RequestQueue requestQueue;

    //Sistema de Sessão
    private UsuarioSessao sessao;
    private String idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        requestQueue = Volley.newRequestQueue(this);
        listview = (ListView) findViewById(R.id.historico_listview);
        msg = (TextView) findViewById(R.id.historico_msg);
        msg.setVisibility(View.GONE);

        this.sessao = new UsuarioSessao(getApplicationContext());
        HashMap<String, String> user = sessao.getUserDetails();
        idUsuario = user.get(UsuarioSessao.KEY_ID);

        RefreshList();
    }

    private void RefreshList(){

        request = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if(jsonArray.length() <= 0){
                        msg.setVisibility(View.VISIBLE);
                    }else {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject objeto = jsonArray.getJSONObject(i);
                            agendamento = new Agendamento(objeto.getInt("id"), objeto.getString("data"), objeto.getString("horario"), objeto.getString("descricao"), objeto.getString("status"), objeto.getString("hospital"));
                            list.add(agendamento);
                        }
                        Log.i("List", list.toString());
                        ArrayAdapter<Agendamento> adapter = new AgendamentoAdapter(getApplicationContext(), list,false);
                        listview.setAdapter(adapter);
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
                hashMap.put("id",idUsuario);
                return hashMap;
            }
        };
        requestQueue.add(request);
    }
}