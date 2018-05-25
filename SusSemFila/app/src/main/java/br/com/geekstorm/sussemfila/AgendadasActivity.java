package br.com.geekstorm.sussemfila;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AgendadasActivity extends AppCompatActivity {
    private static final String URLespecialidade = "http://sussemfila.000webhostapp.com/agendamentos.php ";
    ArrayAdapter<String> adapter;
    ListView listView;
    InputStream is=null;
    String line = null;
    String result = null;
    String[] dados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendadas);
        listView = (ListView) findViewById(R.id.listView);

        //permite network na tread principal
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        //-------------------
        try {
            buscaDados();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //adapter
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dados);
        listView.setAdapter(adapter);
        //---------------------------------------------------------------------//



    }
    private void buscaDados() throws JSONException {
        try {
            URL url = new URL(URLespecialidade);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            is = new BufferedInputStream(con.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //lendo o conteudo
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //json dados
        try {
            JSONArray ja = new JSONArray(result);
            JSONObject jo = null;
            dados = new String[ja.length()];


            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);
                dados[i] = jo.getString("dt_consulta");
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
