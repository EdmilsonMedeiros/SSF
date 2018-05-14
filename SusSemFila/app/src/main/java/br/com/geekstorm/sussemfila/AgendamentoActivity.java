package br.com.geekstorm.sussemfila;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class AgendamentoActivity extends AppCompatActivity {

    private Spinner especialidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendamento);

        Intent a = getIntent();

        Bundle bundle = a.getBundleExtra("a2");
        Especialidade esp = (Especialidade) bundle.getSerializable("especialidade");


        ArrayAdapter<Especialidade> adapter = new ArrayAdapter<Especialidade>(this,android.R.layout.simple_spinner_dropdown_item, esp.especialidadesArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        especialidade = (Spinner) findViewById(R.id.Agendamento_especialidade);
        especialidade.setAdapter(adapter);

    }
}
