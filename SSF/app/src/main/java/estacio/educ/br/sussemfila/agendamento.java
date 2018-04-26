package estacio.educ.br.sussemfila;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.ArrayList;

public class agendamento extends AppCompatActivity {

    Spinner Especialidade;
    Button entrar;

    String url= "";
    String parametros = "";

    ArrayList especialy;
    TextView titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendamento);

        TextView titulo = (TextView) findViewById(R.id.textagendamento);

        url = "https://sussemfila.000webhostapp.com/agendamento.php";
        new SolicitaDados().execute(url);

        Especialidade = (Spinner) findViewById(R.id.especialidades);


        entrar = (Button) findViewById(R.id.btnenviar);
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                String item = Especialidade.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), "Item Escolhido: "+item, Toast.LENGTH_LONG).show();
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

                String[] dados = result.split(",");
                String a = dados[1];
                titulo.setText(a);



    }}


 /*   private class BackTask extends AsyncTask<Void, Void, Void>{
        ArrayList<String> list;

        @Override
        protected Void doInBackground(Void... voids) {
            InputStream is=null;
            String result="";
            return null;

        }

        protected  void onPreExecute(){
            super.onPreExecute();
            list=new ArrayList<>();
        }
    }
    */

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
