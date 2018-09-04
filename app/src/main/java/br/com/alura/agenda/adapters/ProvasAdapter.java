package br.com.alura.agenda.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import br.com.alura.agenda.R;
import br.com.alura.agenda.models.Prova;

public class ProvasAdapter extends BaseAdapter {

    private final List<Prova> provas;
    private final Context contexto;

    public ProvasAdapter(Context contexto, List<Prova> provas) {
        this.contexto = contexto;
        this.provas = provas;
    }

    @Override
    public int getCount() {
        return provas.size();
    }

    @Override
    public Prova getItem(int posicao) {
        return provas.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return provas.get(posicao).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Prova prova = provas.get(position);

        LayoutInflater inflater = LayoutInflater.from(contexto);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_prova_item, parent, false);
        }

        TextView campoMateria = convertView.findViewById(R.id.list_topico_item_materia);
        EditText campoData = convertView.findViewById(R.id.list_topico_item_data);

        campoMateria.setText(prova.getMateria());
        campoData.setText(prova.getData());


        return convertView;
    }
}
