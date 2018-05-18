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
    Button sair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendamento);

        especialidade = (Spinner) findViewById(R.id.Agendamento_especialidade);
        sair = (Button) findViewById(R.id.Agendamento_sair);
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

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });

        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              finish();
            }
        });




    }

}
