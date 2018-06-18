package br.com.geekstorm.sussemfila;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
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

    private ListView listview;
    private Agendamento agendamento;
    private ArrayList<Agendamento> list = new ArrayList<Agendamento>();;
    TextView msg;

    private String Url = "https://sussemfila.000webhostapp.com/getAgendamento.php";
    private Request request;
    private RequestQueue requestQueue;

    //Sistema de Sessão
    private UsuarioSessao sessao;
    private String idUsuario;

    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendamento);
        requestQueue = Volley.newRequestQueue(this);
        listview = (ListView) findViewById(R.id.agendadas_listview);
        msg = (TextView) findViewById(R.id.historico_msg);
        msg.setVisibility(View.GONE);

        this.sessao = new UsuarioSessao(getApplicationContext());
        HashMap<String, String> user = sessao.getUserDetails();
        idUsuario = user.get(UsuarioSessao.KEY_ID);




        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute("");
    }

    private class AsyncTaskRunner extends AsyncTask<String,String,String> {

        private String resp;

        @Override
        protected String doInBackground(String... strings) {
            request = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        if (jsonArray.length() <= 0) {
                            msg.setVisibility(View.VISIBLE);
                        } else {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject objeto = jsonArray.getJSONObject(i);
                                agendamento = new Agendamento(objeto.getInt("id"), objeto.getString("data"), objeto.getString("horario"), objeto.getString("descricao"), objeto.getString("status"), objeto.getString("hospital"));
                                list.add(agendamento);

                            }

                            final ArrayAdapter<Agendamento> adapter = new AgendamentoAdapter(getApplicationContext(), list, true);
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
                    hashMap.put("id", idUsuario);
                    return hashMap;
                }
            };
            requestQueue.add(request);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

        }

        @Override
        protected void onPreExecute() {
            Toast.makeText(getApplicationContext(),"Carregando",Toast.LENGTH_LONG).show();

        }
    }

}

