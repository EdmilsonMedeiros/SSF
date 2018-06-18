package br.com.geekstorm.sussemfila;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class AgendamentoAdapter extends ArrayAdapter<Agendamento> {
    private final Context context;
    private final ArrayList<Agendamento> elementos;
    private final boolean button;

    public AgendamentoAdapter(Context context, ArrayList<Agendamento> elementos, boolean b){

        super(context , R.layout.layout_listview, elementos);
        this.context = context;
        this.elementos = elementos;
        this.button = b;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.layout_listview, parent, false);

        if(button == false){
            Button button = (Button) rowView.findViewById(R.id.Agendadas_ButtonCancelar);
            button.setVisibility(View.GONE);
        }

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
}
