package estacio.educ.br.sussemfila;

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
import android.widget.TextView;


public class CadastroActivity extends AppCompatActivity {

    private AutoCompleteTextView nome;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        AutoCompleteTextView nome = (AutoCompleteTextView) findViewById(R.id.editTextNome);
        AutoCompleteTextView endereco = (AutoCompleteTextView) findViewById(R.id.editTextEndereço);
        AutoCompleteTextView email = (AutoCompleteTextView) findViewById(R.id.editTextEmail);
        AutoCompleteTextView numerocpf = (AutoCompleteTextView) findViewById(R.id.editTextCpf);
        AutoCompleteTextView numerosus = (AutoCompleteTextView) findViewById(R.id.editTextSus);
        AutoCompleteTextView pass1 = (AutoCompleteTextView) findViewById(R.id.editTextPass1);
        AutoCompleteTextView pass2 = (AutoCompleteTextView) findViewById(R.id.editTextPass2);
        Button botaocadastrar = (Button) findViewById(R.id.buttonCadastrar);
        Button botaocancelar = (Button) findViewById(R.id.buttonCancelar);
    }

}
