package br.com.geekstorm.sussemfila;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    AutoCompleteTextView user_cpf, user_senha;
    Button botao_entrar;
    TextView botao_cadastrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AutoCompleteTextView user_cpf = findViewById(R.id.Login_cpf);
        AutoCompleteTextView user_senha = findViewById(R.id.Login_pass);
        Button botao_entrar = (Button) findViewById(R.id.Login_botaoentrar);
        TextView botao_cadastrar = (TextView) findViewById(R.id.Login_botaocadastrar);

        botao_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent telacadastro = new Intent(LoginActivity.this,CadastroActivity.class);
                startActivity(telacadastro);
            }
        });

    }
}
