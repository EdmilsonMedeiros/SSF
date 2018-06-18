package br.com.geekstorm.sussemfila;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {
    //Widgets do Layout
    private EditText user_cpf, user_senha;
    private Button botao_entrar;
    private TextView botao_cadastrar;
    private String nomedocara = "";
    private String cpfdocara = "";
    private int iddocara;
    //Widget Dialog progress
    ProgressDialog progress;

    //Objetos de Requisição do servidor
    private RequestQueue requestQueue;
    private static final String URL = "http://sussemfila.000webhostapp.com/acesso.php ";
    private StringRequest request;

    //Sistema de Sessão
    private UsuarioSessao sessao;

    //Variaveis do usuario
    private String usuariocpf;
    private String usuariosenha;

    private AlertDialog alerta;
    private static final String TAG = "LoginActivity";

    //Iniciando Intent para proxima tela
    Intent main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Efetuando ligação dos objetos do layout com objetos da classe
        this.user_cpf = findViewById(R.id.Login_cpf);
        this.user_senha = findViewById(R.id.Login_pass);
        this.botao_entrar = (Button) findViewById(R.id.Login_btacessar);
        this.botao_cadastrar = (TextView) findViewById(R.id.Login_btCadastrar);

        //Efetuando ligação do Objeto Sessão ao Contexto
        this.sessao = new UsuarioSessao(getApplicationContext());
        this.main  = new Intent(LoginActivity.this, MainActivity.class);

        //Mascarando CPF
        user_cpf.addTextChangedListener(MaskUtil.insert(user_cpf,MaskUtil.MaskType.CPF));

        //Ligando requisição do Sistema pelo metodo Volley
        requestQueue = Volley.newRequestQueue(this);

        //Botao cadastrar
        botao_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent telacadastro = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(telacadastro);
            }
        });

        //Botao entrar
        botao_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });

        //Checando se o usuario possui sessão. Se sim, abrir app
        if(this.sessao.isUserLoggedIn()){
            this.abrirApp();
        }

    }

    //Função para enviar requisição para o servidor com os dados e retornar informações do usuario
    private void Login(){
        //Tratamento para campo vazio
        if(user_cpf.getText().toString().isEmpty() || user_senha.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Você possui campos vazios!", Toast.LENGTH_LONG).show();
        }else{
            if(user_cpf.length() < 14){
                Toast.makeText(getApplicationContext(),"CPF invalido!", Toast.LENGTH_LONG).show();
            }else {
                //Inicializando Dialog de progresso
                progress = new ProgressDialog(LoginActivity.this);
                progress.setTitle("Aguarde...");
                progress.setMessage("Estamos verificando seus dados :p");
                progress.show();
                progress.setCancelable(false);
                //Efetuando a requisao para o sistema
                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    //Metodo de resposta da requisição
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.names().get(0).equals("confirmado")) {
                                String suporte = jsonObject.getString("suporte");
                                if(!suporte.equals("1.5")){
                                    suportedesativado();
                                }else {
                                    //Pegando dados do Objeto Json
                                    nomedocara = jsonObject.getString("nome");
                                    cpfdocara = jsonObject.getString("cpf");
                                    iddocara = jsonObject.getInt("id");

                                    //Criando objeto para enviar o nome e cpf do cara para outra intent
                                    Bundle bundle = new Bundle();
                                    main.putExtra("nome", nomedocara);
                                    main.putExtra("cpf", cpfdocara);
                                    //Finalizando dialog
                                    progress.cancel();
                                    //Criando uma sessão para o usuario
                                    sessao.createUserLoginSession(cpfdocara, nomedocara, iddocara);
                                    //Abrindo app
                                    abrirApp();
                                }
                            } else {
                                progress.cancel();
                                Toast.makeText(getApplicationContext(), "Error: " + jsonObject.getString("error"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    //Usando MAP e enviando informaçao para o servidor
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("cpf", user_cpf.getText().toString());
                        hashMap.put("senha", user_senha.getText().toString());

                        return hashMap;
                    }
                };
                requestQueue.add(request);
            }
        }
    }


    //Funcao para abrir a tela main
    private void abrirApp(){
        startActivity(main);
    }

    private void suportedesativado() {
        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.dialog, null);
        //definimos para o botão do layout um clickListener

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.dialog);
        builder.setTitle("Comunicado!");
        builder.setIcon(R.drawable.logomain);
        builder.setMessage("Está versão do SUS SEM FILA está desatualizada. Por motivos de politica direcionadas ao projeto, a equipe não oferece mais suporte a esta versão.");
        builder.setView(view);
        alerta = builder.create();
        alerta.show();
        alerta.setCancelable(false);
    }

}
