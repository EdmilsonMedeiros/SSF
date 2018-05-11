package br.com.geekstorm.sussemfila;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class AgendamentoActivity extends AppCompatActivity {

    private Spinner especialidade;
    private Especialidade especialidades;
    private List<Especialidade> arrayEspecialidade = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendamento);

        especialidade = (Spinner) findViewById(R.id.Agendamento_especialidade);


    }
}
