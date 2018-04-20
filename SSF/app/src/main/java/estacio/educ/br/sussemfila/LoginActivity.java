package estacio.educ.br.sussemfila;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    //Atri
    protected EditText editTextCpf;
    protected EditText editTextPass;
    protected Button btnEntrar;
    protected TextView cadastrese;
    protected TextView titulo;

    String url= "";
    String parametros = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Inst
        editTextCpf = findViewById(R.id.editTextCpf);
        editTextPass = findViewById(R.id.editTextPass);
        btnEntrar = findViewById(R.id.btnEntrar);
        cadastrese = findViewById(R.id.cadastrese);
        titulo = findViewById(R.id.textViewLogin);

        cadastrese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abrecadastro = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(abrecadastro);
            }
        });

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                    String cpf = editTextCpf.getText().toString();
                    String senha = editTextPass.getText().toString();
                      if(cpf.isEmpty() || senha.isEmpty()){
                          Toast.makeText(getApplicationContext(), "CAMPOS VAZIOS!", Toast.LENGTH_LONG).show();
                      }else {
                          url = "https://sussemfila.000webhostapp.com/login.php";
                          parametros = "cpf="+cpf+"&senha="+senha;
                          new SolicitaDados().execute(url);
                      }
                } else {
                    Toast.makeText(getApplicationContext(), "Sem Conexão com a Internet", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private class SolicitaDados extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return conexao.postDados(urls[0], parametros);
        }

        @Override
        protected void onPostExecute(String result) {
            titulo.setText(result);
        }
    }
}