package br.com.geekstorm.sussemfila;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button consulta;
    TextView nomeusuario, cpfusuario, btsair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nomeusuario = (TextView) findViewById(R.id.Main_nomeuser);
        cpfusuario = (TextView) findViewById(R.id.Main_cpfuser);
        btsair = (TextView)  findViewById(R.id.Main_sair);

        Intent a = getIntent();

        Bundle bundle = a.getBundleExtra("a1");
        Paciente usuario = (Paciente) bundle.getSerializable("paciente");

        nomeusuario.setText(usuario.getNome());
        cpfusuario.setText("CPF: "+usuario.getCpf());

        btsair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sair = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(sair);
            }
        });
    }

}
