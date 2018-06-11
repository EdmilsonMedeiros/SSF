package br.com.geekstorm.sussemfila;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;

public class ConfigActivity extends AppCompatActivity {
    TextView tvNomeDoCara;
    TextView tvCpfDoCara;
    UsuarioSessao sessao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        tvCpfDoCara = (TextView) findViewById(R.id.tvCpfUsuario);
        tvNomeDoCara = (TextView) findViewById(R.id.tvNomeUsuario);

        this.sessao = new UsuarioSessao(getApplicationContext());
        HashMap<String, String> user = sessao.getUserDetails();
        String nome = user.get(UsuarioSessao.KEY_NOME);
        String cpf = user.get(UsuarioSessao.KEY_CPF);
        String idUsuario = user.get(UsuarioSessao.KEY_ID);
        Intent a = getIntent();
        String cpfdocara = a.getStringExtra("nomedocara");
        String nomedocara = a.getStringExtra("cpfdocara");
        if (this.sessao.isUserLoggedIn()) {
            tvNomeDoCara.setText(nome);
            tvCpfDoCara.setText("CPF: " + cpf);
        } else {
            tvNomeDoCara.setText(cpfdocara);
            tvCpfDoCara.setText("CPF: " + nomedocara);
        }

    }
}
