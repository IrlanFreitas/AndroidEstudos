package br.com.alura.agenda.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import br.com.alura.agenda.R;
import br.com.alura.agenda.models.Prova;


public class DetalheProvaFragment extends Fragment {

    private TextView campoMateria;
    private TextView campoData;
    private ListView listaTopicos;

    /* Forma que eu pensei e funcionou
    * passar os valores pelo contrustor
    public DetalheProvaFragment(Prova prova) {
        this.prova = prova;
    } */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detalhe_prova, container, false);

        /* Recebendo valores de outra activity/fragment */
        Bundle parametros = getArguments();
        Prova prova = null;

        campoMateria = view.findViewById(R.id.detalhes_prova_materia);
        campoData = view.findViewById(R.id.detalhes_prova_data);
        listaTopicos = view.findViewById(R.id.detalhes_prova_lista_topicos);

        if (parametros != null) {
            prova = (Prova) parametros.getSerializable("prova");
            preencher(prova);
        }

        return view;
    }


    public void preencher(Prova prova) {
        Log.i("Prova", prova.getMateria() + " " + prova.getData() + " " + prova.getTopicos());

        campoMateria.setText(prova.getMateria());
        campoData.setText(prova.getData());

        ArrayAdapter adapter =
                new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, prova.getTopicos());

        listaTopicos.setAdapter(adapter);
    }

}
