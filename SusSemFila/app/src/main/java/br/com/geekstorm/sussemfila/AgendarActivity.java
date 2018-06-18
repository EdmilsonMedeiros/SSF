package br.com.geekstorm.sussemfila;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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

public class AgendarActivity extends AppCompatActivity {
    UsuarioSessao sessao2;
    //Widgets do Layout
    private Spinner especialidade, medico, hospital, atendimento;
    Button cadastrar, sair;
    //Objetos de Requisição do servidor
    private RequestQueue requestQueue;
    private StringRequest request;
    //Url para onde a requisição será enviada
    private static final String URLespecialidade = "http://sussemfila.000webhostapp.com/especialidades.php ";
    //Array e objeto de Especialidade
    ArrayList<Especialidade> especialidadesArray = new ArrayList<>();
    Especialidade especialyt;

    //Objetos medico
    private static final String URLMedicos = "http://sussemfila.000webhostapp.com/medicos.php ";
    long idMedico;
    ArrayList<Medico> medicoArray = new ArrayList<>();
    Medico medicolyt;

    //Objetos Hospitais
    private static final String URLHospital = "http://sussemfila.000webhostapp.com/hospitais.php ";
    Hospital hospitaly;
    ArrayList<Hospital> hospitalArray = new ArrayList<>();

    //Objeto Horarios
    private static final String URLHorarios = "http://sussemfila.000webhostapp.com/atendimentos.php ";
    Atendimento atendimentoly;
    ArrayList<Atendimento> atendimentoArray = new ArrayList<>();

    //Sistema de Sessão
    private UsuarioSessao sessao;
    //user dates
    String idUsuario;
    private static final String URLEnviar = "https://sussemfila.000webhostapp.com/setAgendamento.php";

    String gEspecialidade;
    String gHospital;
    String gMedico;
    String gAtendimento;
    private AlertDialog alerta;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar);
        //----------------------

        this.sessao = new UsuarioSessao(getApplicationContext());
        HashMap<String, String> user = sessao.getUserDetails();
        String nome = user.get(UsuarioSessao.KEY_NOME);
        String cpf = user.get(UsuarioSessao.KEY_CPF);
        idUsuario = user.get(UsuarioSessao.KEY_ID);

        Intent a = getIntent();
        String cpfdocara = a.getStringExtra("nomedocara");
        String nomedocara = a.getStringExtra("cpfdocara");

        //----------------------
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
        cadastrar = (Button) findViewById(R.id.Agendamento_enviar);
        //Spinner de Medico
        medico = (Spinner) findViewById(R.id.Agendamento_medico);
        //Spinner do Hospital
        hospital = (Spinner) findViewById(R.id.Agendamento_hospital);
        //Spinner Atendimento
        atendimento = (Spinner) findViewById(R.id.Agendamento_date);

        //Botão Sair
        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              finish();
            }
        });
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarAgendamento();
            }
        });

    }

    //Função para popular Spinner de Especialidade
    private void SpinnerEspecialidade(){

        Especialidade e1 = new Especialidade(0,"Especialidade");
        especialidadesArray.add(e1);
        request = new StringRequest(Request.Method.POST, URLespecialidade, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject objeto = jsonArray.getJSONObject(i);
                        especialyt = new Especialidade(objeto.getInt("id"), objeto.getString("descricao"));
                        especialidadesArray.add(especialyt);
                    }

                    //Spinner de Especialidade
                    ArrayAdapter<Especialidade> adapter = new ArrayAdapter<Especialidade>(getApplicationContext(),R.layout.spinner_item, especialidadesArray);
                    adapter.setDropDownViewResource(R.layout.spinner_item);
                    especialidade.setAdapter(adapter);

                    //Onclick do Spinner
                    especialidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                            Especialidade a = (Especialidade) especialidade.getSelectedItem();
                            hospitalArray.clear();
                            medicoArray.clear();
                            gEspecialidade = a.getId()+"";
                            SpinnerHospital(a.getId()+"");

                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapter) {  }
                    });
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

    private void SpinnerHospital(final String v){

        Hospital h1 = new Hospital(0,"Hospital");
        hospitalArray.add(h1);
        request = new StringRequest(Request.Method.POST, URLHospital, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject objeto = jsonArray.getJSONObject(i);
                        hospitaly = new Hospital(objeto.getInt("id"), objeto.getString("descricao"));
                        hospitalArray.add(hospitaly);
                    }

                    //Spinner de Especialidade
                    ArrayAdapter<Hospital> adapter = new ArrayAdapter<Hospital>(getApplicationContext(),R.layout.spinner_item, hospitalArray);
                    adapter.setDropDownViewResource(R.layout.spinner_item);
                    hospital.setAdapter(adapter);

                    //Onclick do Spinner
                    hospital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                            Hospital h = (Hospital) hospital.getSelectedItem();
                            medicoArray.clear();
                            gHospital = h.getId()+"";
                            SpinnerMedico(h.getId()+"");

                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapter) {  }
                    });
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
                hashMap.put("especialidade", v);
                return hashMap;
            }
        };
        requestQueue.add(request);
    }

    //Spinner medico
    private void SpinnerMedico(final String v){
        Medico m1 = new Medico(0,"Medico");
        medicoArray.add(m1);
        request = new StringRequest(Request.Method.POST, URLMedicos, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if(jsonArray.length() > 0){
                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject objeto = jsonArray.getJSONObject(i);
                            medicolyt = new Medico(objeto.getInt("id"),objeto.getString("nome"));
                            medicoArray.add(medicolyt);
                        }
                    }else{}

                    ArrayAdapter<Medico> adapter2 = new ArrayAdapter<Medico>(AgendarActivity.this,R.layout.spinner_item, medicoArray);
                    adapter2.setDropDownViewResource(R.layout.spinner_item);
                    medico.setAdapter(adapter2);

                    //Onclick do Spinner
                    medico.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                            Medico m = (Medico) medico.getSelectedItem();
                            atendimentoArray.clear();
                            gMedico = m.getId()+"";
                            SpinnerAtendimento(m.getId()+"");
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapter) {  }
                    });
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
                hashMap.put("hospital", v);
                return hashMap;
            }
        };
        requestQueue.add(request);
    }

    private void SpinnerAtendimento(final String v){
        request = new StringRequest(Request.Method.POST, URLHorarios, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Atendimento ", response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject objeto = jsonArray.getJSONObject(i);
                        atendimentoly = new Atendimento(objeto.getInt("id"), objeto.getString("Data"),objeto.getString("Time"));
                        atendimentoArray.add(atendimentoly);
                    }

                    //Spinner de Especialidade
                    ArrayAdapter<Atendimento> adapter = new ArrayAdapter<Atendimento>(getApplicationContext(),R.layout.spinner_item, atendimentoArray);
                    adapter.setDropDownViewResource(R.layout.spinner_item);
                    atendimento.setAdapter(adapter);

                    //Onclick do Spinner
                    atendimento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                            Atendimento a = (Atendimento) atendimento.getSelectedItem();
                            gAtendimento = a.getId()+"";
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapter) {  }
                    });
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
                hashMap.put("medico", v);
                return hashMap;
            }
        };
        requestQueue.add(request);

    }

    private void cadastrarAgendamento(){
        Log.i("Especialidade", gEspecialidade);
        Log.i("Hospital", gHospital);
        Log.i("Medico", gMedico);
        Log.i("Atendimento", gAtendimento);

        progress = new ProgressDialog(AgendarActivity.this);
        progress.setTitle("Aguarde...");
        progress.setMessage("Estamos cadastrando a sua consulta :p");
        progress.show();
        progress.setCancelable(false);

        request = new StringRequest(Request.Method.POST, URLEnviar, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.names().get(0).equals("confirmado")) {
                        progress.dismiss();
                        AlertTrue();
                    } else {
                        Toast.makeText(getApplicationContext(),"Erro ao Cadastrar a Consulta!", Toast.LENGTH_LONG).show();
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
                hashMap.put("especialidade", gEspecialidade);
                hashMap.put("hospital", gHospital);
                hashMap.put("medico", gMedico);
                hashMap.put("atendimento", gAtendimento);
                hashMap.put("paciente",idUsuario);
                Log.i("HASHMAP", hashMap.toString());
                return hashMap;
            }
        };
        requestQueue.add(request);
    }

    private void AlertTrue() {
        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.layout_dialog, null);
        //definimos para o botão do layout um clickListener
        TextView msg = (TextView) view.findViewById(R.id.dialog_msg);
        msg.setText("Agendamento Realizado com Sucesso!");

        view.findViewById(R.id.dialog_agendar_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.layout_dialog);
        builder.setView(view);
        alerta = builder.create();
        alerta.show();


    }



}
