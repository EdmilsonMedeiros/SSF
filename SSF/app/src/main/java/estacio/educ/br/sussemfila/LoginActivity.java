package estacio.educ.br.sussemfila;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    //Atri
    protected EditText editTextCpfSus;
    protected EditText editTextPass;
    protected Button btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Inst
        editTextCpfSus = findViewById(R.id.editTextCpfSus);
        editTextPass = findViewById(R.id.editTextPass);
        btnEntrar = findViewById(R.id.btnEntrar);

    }
    //Met
    public void logar(View v){
        setContentView(R.layout.activity_main);
    }
    public void cadastrar(View v){
        setContentView(R.layout.activity_cadastro);
    }
}
