package br.com.alura.agenda.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import br.com.alura.agenda.R;
import br.com.alura.agenda.fragments.MapaFragment;

public class MapaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction tx = manager.beginTransaction();

        //Adicionando o Fragment de mapa já criado pelo Google Maps
        tx.replace(R.id.mapa_frame_principal, new MapaFragment());

        tx.commit();

    }

}
