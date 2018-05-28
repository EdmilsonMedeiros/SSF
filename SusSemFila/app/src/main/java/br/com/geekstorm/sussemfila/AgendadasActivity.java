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
    //1
    String urlAdress = "https://sussemfila.000webhostapp.com/listViewShow.php";
    String[] dataConsulta;
    String[] especialidade;
    String[] foto;
    ListView listView;
    //5
    String line="null";
    String result="null";
    //4
    BufferedInputStream is;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendadas);
        //2
        listView = (ListView) findViewById(R.id.lView);

        //3---------
        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        collectData();
        CustomListView customListView = new CustomListView(this, dataConsulta, especialidade, foto);
        listView.setAdapter(customListView);

    }
    private void collectData(){//connection-------4
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
            foto = new String[ja.length()];

            for (int i=0; i<=ja.length(); i++){
                jo = ja.getJSONObject(i);
                dataConsulta[i] = jo.getString("dt_consulta");
                especialidade[i] = jo.getString("descricao");
                foto[i] = jo.getString("link_profile");
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }//fim collectData;
}
