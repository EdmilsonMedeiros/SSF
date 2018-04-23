package estacio.educ.br.sussemfila;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView nomeusuario;
    TextView cpfusuario;

    String nomeusario1, cpfusuario1;


    TextView sair;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nomeusuario = findViewById(R.id.TextViewnomeusuario);
        cpfusuario =  (TextView)  findViewById(R.id.TextViewcpfusuario);

        nomeusario1 = getIntent().getExtras().getString("Nome");
        cpfusuario1 = getIntent().getExtras().getString("CPF");

        nomeusuario.setText(nomeusario1);
        cpfusuario.setText(cpfusuario1);

        sair = findViewById(R.id.botaosairmain);

        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sair_main = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(sair_main);
            }
        });



    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
