package estacio.educ.br.sussemfila;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import org.json.JSONException;
import android.util.Log;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class CadastroActivity extends AppCompatActivity {

    AutoCompleteTextView nome;
    private AutoCompleteTextView endereco;
    private AutoCompleteTextView email;
    private AutoCompleteTextView numerocpf;
    private AutoCompleteTextView numerosus;
    private AutoCompleteTextView pass1;
    private AutoCompleteTextView pass2;
    private Button botaocadastrar;
    private Button botaocancelar;
    private String pass;
    private boolean pass_confirm;

    String url= "";
    String parametros = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        nome = findViewById(R.id.editTextNome);
        endereco = findViewById(R.id.editTextEndereço);
        email = findViewById(R.id.editTextEmail);
        numerocpf = findViewById(R.id.editTextCpf);
        numerosus = findViewById(R.id.editTextSus);
        pass1 = findViewById(R.id.editTextPass1);
        pass2 = findViewById(R.id.editTextPass2);
        Button botaocadastrar = (Button) findViewById(R.id.buttonCadastrar);
        Button botaocancelar = (Button) findViewById(R.id.buttonCancelar);


        botaocancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        botaocadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                    String pass11 = pass1.getText().toString();
                    String pass22 = pass2.getText().toString();
                    if (pass11.equals(pass22)) {
                        pass = pass22;
                    } else {
                        Toast.makeText(getApplicationContext(), "Senha diferentes!", Toast.LENGTH_LONG).show();
                    }
                    String nome1 = nome.getText().toString();
                    String endereco1 = endereco.getText().toString();
                    String email1 = email.getText().toString();
                    String numerocpf1 = numerocpf.getText().toString();
                    String numerosus1 = numerosus.getText().toString();

                    if (nome1.isEmpty() || endereco1.isEmpty() || email1.isEmpty() || numerocpf1.isEmpty() || numerosus1.isEmpty() || pass11.isEmpty() || pass22.isEmpty() ) {
                        Toast.makeText(getApplicationContext(), "Os campos não podem está vazios!", Toast.LENGTH_LONG).show();
                    } else {
                        url = "https://sussemfila.000webhostapp.com/cadastro.php";
                        parametros = "nome=" + nome1 + "&endereco=" + endereco1 + "&numerocpf=" + numerocpf1 + "&numerosus=" + numerosus1 + "&email=" + email1 + "&senha=" + pass;

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

            if(result.contains("registro_ok")){
                Toast.makeText(getApplicationContext(), "Registro efetuado com Sucesso!", Toast.LENGTH_LONG).show();
                Intent voltarlogin = new Intent(CadastroActivity.this, LoginActivity.class);
                startActivity(voltarlogin);
            }else{
                Toast.makeText(getApplicationContext(), "Registro não efetuado!", Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
