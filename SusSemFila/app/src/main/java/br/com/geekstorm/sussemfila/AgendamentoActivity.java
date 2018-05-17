package br.com.geekstorm.sussemfila;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AgendamentoActivity extends AppCompatActivity {

    private Spinner especialidade, medico;
    ArrayList<Medico> arrayMedico = new ArrayList<>();
    ArrayList<Especialidade> arrayEspecialidade;

    private RequestQueue requestQueue;
    private static final String URLmedico = "http://sussemfila.000webhostapp.com/especialidades.php ";
    private StringRequest request;

    Especialidade esp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendamento);

        especialidade = (Spinner) findViewById(R.id.Agendamento_especialidade);
        medico = (Spinner) findViewById(R.id.Agendamento_medico);

        Intent a = getIntent();
        Bundle bundle = a.getBundleExtra("bundle");
        arrayEspecialidade = (ArrayList<Especialidade>) bundle.getSerializable("especialidade");



        ArrayAdapter<Especialidade> adapter = new ArrayAdapter<Especialidade>(this,android.R.layout.simple_spinner_dropdown_item, arrayEspecialidade);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        especialidade.setAdapter(adapter);

        especialidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                    showMedico();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });
    }

    public void ShowItem(){

        ArrayAdapter<Medico> adapter = new ArrayAdapter<Medico>(this,android.R.layout.simple_spinner_dropdown_item, arrayMedico);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        medico.setAdapter(adapter);





        String nome = especialidade.getSelectedItem().toString();
        long id = especialidade.getSelectedItemId();

    }

   public void showMedico() {
       /*request = new StringRequest(Request.Method.POST, URLmedico, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               try {
                   JSONArray jsonArray = new JSONArray(response);
                   for (int i = 0; i < jsonArray.length(); i++) {
                       JSONObject objeto = jsonArray.getJSONObject(i);
                       long especialidademedico = objeto.getInt("idEspecialidade");
                       for (Especialidade e : arrayEspecialidade) {
                           if (especialidademedico == e.getId()) {
                               esp = e;
                           }
                       }

                       Medico medico = new Medico(objeto.getString("nome"), objeto.getString("cpf"), esp);
                       arrayMedico.add(medico);
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
*/}
}
