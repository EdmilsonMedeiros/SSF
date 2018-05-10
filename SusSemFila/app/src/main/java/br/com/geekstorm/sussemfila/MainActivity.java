package br.com.geekstorm.sussemfila;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button consulta;
    TextView nomeusuario, cpfusuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        consulta = (Button) findViewById(R.id.Main_ConsultasAgendadas);
        nomeusuario = (TextView) findViewById(R.id.Main_nomeusuario);
        cpfusuario = (TextView) findViewById(R.id.Main_cpfusuario);

        Intent a = getIntent();
        String nome = a.getExtras().getString("nome");
        nomeusuario.setText(nome);

        Bundle bundle = a.getBundleExtra("a1");
        Paciente usuario = (Paciente) bundle.getSerializable("paciente");


        cpfusuario.setText(usuario.getCpf());

        consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

    }
}
