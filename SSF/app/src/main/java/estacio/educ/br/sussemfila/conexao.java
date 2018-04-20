package estacio.educ.br.sussemfila;

import android.renderscript.ScriptGroup;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class conexao {
    public static String postDados(String urlUsuario, String paramentrosUsuario){
        URL url;
        HttpURLConnection connection = null;

        try {
            url = new URL(urlUsuario);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");

            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Lenght", "" + Integer.toString(paramentrosUsuario.getBytes().length));
            connection.setRequestProperty("Content-Language", "pt-BR");

            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            outputStreamWriter.write(paramentrosUsuario);
            outputStreamWriter.flush();

            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
            String linha;
            StringBuffer resposta = new StringBuffer();

            while ((linha = bufferedReader.readLine()) != null){
                resposta.append(linha);
                resposta.append('\r');
            }
            bufferedReader.close();
            return resposta.toString();
        }catch (Exception erro) {
            return null;
        }finally {
            if(connection != null){
                connection.disconnect();
            }
        }
    }

}
