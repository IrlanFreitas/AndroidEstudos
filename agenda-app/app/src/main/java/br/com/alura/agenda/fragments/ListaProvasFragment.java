package br.com.alura.agenda.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.alura.agenda.R;
import br.com.alura.agenda.activities.DetalhesProvaActivity;
import br.com.alura.agenda.activities.ProvasActivity;
import br.com.alura.agenda.adapters.ProvasAdapter;
import br.com.alura.agenda.models.Prova;

public class ListaProvasFragment extends Fragment {

    /* OnCreateView */
    // Chamado pelo android quando o fragament vai criar a tela
    // Tod.o o  fragament vai entrar na tela dentro da activity
    // para que posso aparecer é necessário construir a
    // view que o representa
    /* Parametros */
    // inflater - Controlador do layout que será inflado
    // container - Elemento pai do fragment, que pode ser qualquer activity
    // savedInstanceState - Algum comportamento que esteja acontecendo anteriormente

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Inflando o layout que foi renomeado, com o container que é recebido de parametro
        //false - para não ocorrer erro.
        View view = inflater.inflate(R.layout.fragment_lista_provas, container, false);

        /*Prova portugues = new Prova("Portugues", "24/08/2018", Arrays.asList("Analise Sintática", "Analise Morfológica"));
        Prova matematica = new Prova("Matematica", "26/08/2018", Arrays.asList("Logaritmo", "Seno", "Cosseno"));
        Prova historia = new Prova("Historia", "28/08/2018", Arrays.asList("Segunda Guerra Mundial", "Guerra Fria", "Colonizações"));
        Prova geografia = new Prova("Geografia", "30/08/2018", Arrays.asList("Morros", "Planices", "Terrenos"));*/

        List<Prova> provas = new ArrayList<>(); //Arrays.asList(portugues, matematica, historia, geografia);

        ListView listaProvas = view.findViewById(R.id.provas_lista);

        ProvasAdapter adapter = new ProvasAdapter(getContext(), provas);

        listaProvas.setAdapter(adapter);

        listaProvas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Prova prova = (Prova) lista.getItemAtPosition(position);

                /* Outra forma de fazer a substituíção
                 * de um framelayout para o fragment
                 * porem, nesse caso a responsabilidade
                 * fica com a activity e não com o desenvolvedor */

                ProvasActivity activity = (ProvasActivity) getActivity();
                activity.selecionaProva(prova);

            }
        });

        //Retorna a view que foi criada e populada
        return view;
    }

    private boolean estaNoModoPaisagem() {
        return getResources().getBoolean(R.bool.modoPaisagem);
    }
}
