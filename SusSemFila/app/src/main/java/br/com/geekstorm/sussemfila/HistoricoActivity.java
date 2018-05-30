package br.com.geekstorm.sussemfila;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class HistoricoActivity extends AppCompatActivity {
    TextView tvNomeDoCara;
    TextView tvCpfDoCara;
    UsuarioSessao sessao;
    //1
    String urlAdress = "https://sussemfila.000webhostapp.com/listViewHistorico.php";
    String[] dataConsulta;
    String[] especialidade;
    String[] foto;
    ListView listViewHistorico;
    String[] statusC;
    //5
    String line="null";
    String result="null";
    //4
    BufferedInputStream is;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        //pega nome e cpf do usuario logado
        tvNomeDoCara=(TextView) findViewById(R.id.tvNomeUsuario);
        tvCpfDoCara=(TextView) findViewById(R.id.tvCpfUsuario);
        this.sessao = new UsuarioSessao(getApplicationContext());
        HashMap<String, String> user = sessao.getUserDetails();
        String nome = user.get(UsuarioSessao.KEY_NOME);
        String cpf = user.get(UsuarioSessao.KEY_CPF);
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


        //2
        listViewHistorico = (ListView) findViewById(R.id.listViewHistorico);
        //tvStatusC = findViewById(R.id.tv);


        //3---------
        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        collectData2();
        CustomListView customListView = new CustomListView(this, dataConsulta, especialidade, foto, statusC);
        listViewHistorico.setAdapter(customListView);
    }
    private void collectData2(){//connection-------4
        try{
            URL url = new URL(urlAdress);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            is = new BufferedInputStream(con.getInputStream());
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        //content
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            while ((line=br.readLine()) != null){
                sb.append(line+"\n");
            }
            is.close();
            result = sb.toString();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        //JSON
        try{
            JSONArray ja = new JSONArray(result);
            JSONObject jo = null;
            dataConsulta = new String[ja.length()];
            especialidade = new String[ja.length()];
            statusC = new String[ja.length()];
            foto = new String[ja.length()];

            for (int i=0; i<=ja.length(); i++){
                jo = ja.getJSONObject(i);
                dataConsulta[i] = jo.getString("dt_consulta");
                especialidade[i] = jo.getString("descricao");
                statusC[i] = jo.getString("status");
                foto[i] = jo.getString("link_profile");
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }//fim collectData;
    private void logoff(){
        finish();
        this.sessao.logoutUser();
    }
}
