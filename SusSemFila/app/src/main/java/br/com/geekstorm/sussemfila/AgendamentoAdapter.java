package br.com.geekstorm.sussemfila;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AgendamentoAdapter extends ArrayAdapter<Agendamento> {
    private final Context context;
    private final ArrayList<Agendamento> elementos;
    private final boolean button;

    private Request request;
    private RequestQueue requestQueue;
    private boolean msg;

    public AgendamentoAdapter(Context context, ArrayList<Agendamento> elementos, boolean b){

        super(context , R.layout.layout_listview, elementos);
        this.context = context;
        this.elementos = elementos;
        this.button = b;
    }

    @Override
    public int getCount() {
        return elementos.size();
    }

    @Override
    public Agendamento getItem(int position) {
        return elementos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.layout_listview, parent, false);

        if(button == false){
            Button button = (Button) rowView.findViewById(R.id.Agendadas_ButtonCancelar);
            button.setVisibility(View.GONE);
        }

        Button button = (Button) rowView.findViewById(R.id.Agendadas_ButtonCancelar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Agendamento agendamento = elementos.get(position);
                Deletar(""+elementos.get(position).id);
                Log.i("Elemenot: ", elementos.get(position).toString());
                Log.i("Elemento Id: ", ""+elementos.get(position).id);

                 Toast.makeText(context, "Consulta cancelada com sucesso!", Toast.LENGTH_LONG).show();
                }
        });



        TextView especialidade = (TextView) rowView.findViewById(R.id.tvEspecialidade);
        TextView status = (TextView) rowView.findViewById(R.id.tv);
        TextView data = (TextView) rowView.findViewById(R.id.tvData);
        TextView hospital = (TextView) rowView.findViewById(R.id.Agendadas_hospital);
        TextView horario = (TextView) rowView.findViewById(R.id.Agendadas_horario);

        especialidade.setText(elementos.get(position).especialidade);
        status.setText(elementos.get(position).status);
        data.setText(elementos.get(position).datamarcada);
        horario.setText(elementos.get(position).horariomarcado);
        hospital.setText(elementos.get(position).hospital);

        return rowView;
    }

    private void Deletar(final String idp){
        requestQueue = Volley.newRequestQueue(context);
        request = new StringRequest(Request.Method.POST, "https://sussemfila.000webhostapp.com/DeleteAgendamento.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                     JSONArray jsonArray = new JSONArray(response);
                        JSONObject objeto = jsonArray.getJSONObject(0);
                        if (objeto.names().get(0).equals("confirmado")){

                }

            }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("id",idp);
                return hashMap;
            }
        };
        requestQueue.add(request);
    }}


