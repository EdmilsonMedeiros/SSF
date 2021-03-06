package br.com.geekstorm.sussemfila;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "";
    //Widgets do Layout
    ImageView agendamento, consAgendadas, mainBthistorico, Main_btconfiguracao, main_perfil;
    TextView nomeusuario, cpfusuario, btsair;
    //Sistema de Sessão
    UsuarioSessao sessao;
    String EXTRA_MESSAGE;
    String EXTRA_MESSAGE2;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Efetuando ligação dos objetos do layout com objetos da classe
        nomeusuario = (TextView) findViewById(R.id.Main_nomeuser);
        cpfusuario = (TextView) findViewById(R.id.Main_cpfuser);
        btsair = (TextView) findViewById(R.id.Main_sair);
        agendamento = findViewById(R.id.Main_btagendamento);
        consAgendadas = findViewById(R.id.Main_btconsultaagendamento);
        mainBthistorico = findViewById(R.id.mainBthistorico);
        Main_btconfiguracao = findViewById(R.id.Main_btconfiguracao);


        main_perfil = (ImageView) findViewById(R.id.main_perfil);
        main_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        this.sessao = new UsuarioSessao(getApplicationContext());

        //Abrindo consultas agendadas
        consAgendadas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(sessao.isUserLoggedIn()) {
                    Intent it = new Intent(MainActivity.this, AgendamentoActivity.class);
                    String nomedocara = it.getStringExtra("nomedocara");
                    String cpfdocara = it.getStringExtra("cpfdocara");
                    it.putExtra(EXTRA_MESSAGE, nomedocara);
                    it.putExtra(EXTRA_MESSAGE2, cpfdocara);
                    startActivity(it);
                }
            }
        });
        //Abrindo histórico

        mainBthistorico.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(sessao.isUserLoggedIn()) {
                    Intent it = new Intent(MainActivity.this, HistoricoActivity.class);
                    String nomedocara = it.getStringExtra("nomedocara");
                    String cpfdocara = it.getStringExtra("cpfdocara");
                    it.putExtra(EXTRA_MESSAGE, nomedocara);
                    it.putExtra(EXTRA_MESSAGE2, cpfdocara);
                    startActivity(it);
                }
            }
        });
        //Configurações
        Main_btconfiguracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent it = new Intent(MainActivity.this, ConfigActivity.class);
                    String nomedocara = it.getStringExtra("nomedocara");
                    String cpfdocara = it.getStringExtra("cpfdocara");
                    it.putExtra(EXTRA_MESSAGE, nomedocara);
                    it.putExtra(EXTRA_MESSAGE2, cpfdocara);
                    startActivity(it);
            }
        });


        //Restaurando Intent e seus dados
        Intent a = getIntent();
        String cpfdocara = a.getStringExtra("nomedocara");
        String nomedocara = a.getStringExtra("cpfdocara");

        //Checando se o usuario esta logado
        if(sessao.checkLogin())
            finish();

        //Restaura dados do Usuario, caso esteja logado
        HashMap<String, String> user = sessao.getUserDetails();
        String nome = user.get(UsuarioSessao.KEY_NOME);
        String cpf = user.get(UsuarioSessao.KEY_CPF);

        //Se o usuario estiver logado pegar seus dados; se não, pegar dados da intent login

            if (this.sessao.isUserLoggedIn()) {
                nomeusuario.setText(nome);
                cpfusuario.setText("CPF: " + cpf);
            } else {
                nomeusuario.setText(cpfdocara);
                cpfusuario.setText("CPF: " + nomedocara);
            }

        //Botao sair
        btsair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               logoff();
            }
        });

        //Botao Agendamento
        agendamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,AgendarActivity.class);
                startActivity(i);
          }
        });


    }
    //------------------Intent do histórico
/*
        public void mainBthistorico (View view){
        Intent intent = new Intent(this, HistoricoActivity.class);
        Intent intent2 = new Intent(this, HistoricoActivity.class);
        String nomedocara = intent.getStringExtra("nomedocara");
        String cpfdocara = intent2.getStringExtra("cpfdocara");
        intent.putExtra(EXTRA_MESSAGE, nomedocara);
        intent2.putExtra(EXTRA_MESSAGE2, cpfdocara);
        startActivity(intent);
        startActivity(intent2);
    }
    */


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            main_perfil.setImageBitmap(imageBitmap);
        }
    }
    //Função sair e deletar sessão
    private void logoff(){
        finish();
        this.sessao.logoutUser();
    }

    }



