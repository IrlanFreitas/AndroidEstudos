package br.com.alura.agenda.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.models.Aluno;
import br.com.alura.agenda.negocios.Localizador;

public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {


    private GoogleMap mapa;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        /* Método que prepara um instancia do google maps para ser utilizada
          * para ser customizada */
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.mapa = googleMap;

        LatLng posicao = obterEnderecoPeloNome("Salvador, Bahia");

        if (posicao != null) {
            centralizaEm(posicao);
        }

        adicionarMarcadores(googleMap);

        new Localizador(getContext(), this);

    }

    private void adicionarMarcadores(GoogleMap googleMap) {

        AlunoDAO alunoDAO = new AlunoDAO(getContext());
        List<Aluno> alunos = alunoDAO.getAlunos();

        for (Aluno aluno : alunos) {

            LatLng localizacao = obterEnderecoPeloNome(aluno.getEndereco());

            if (localizacao != null) {
                MarkerOptions marcador = new MarkerOptions();
                marcador.position(localizacao);
                marcador.title(aluno.getNome());
                marcador.snippet(aluno.getNota().toString());
                /*Adicionando marcadores no mapa*/
                googleMap.addMarker(marcador);

            }
        }

    }

    public LatLng obterEnderecoPeloNome(String endereco) {
        try {
            /* Geocoder */
            // Classe responsável por fazer chamadas a API de mapas
            // do google e obter os resultados do maps, onde isso é controlado
            // no getFromLocationName, no segundo parametro maxResults
            Geocoder geocoder = new Geocoder(getContext());
            List<Address> resultados = geocoder.getFromLocationName(endereco, 1);
            if (!resultados.isEmpty()) {
                LatLng posicaoInicial = new LatLng(resultados.get(0).getLatitude(), resultados.get(0).getLongitude());
                return posicaoInicial;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void centralizaEm(LatLng coordenada) {
        if (mapa != null) {
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(coordenada, 14);
            mapa.moveCamera(update);
        }
    }

    public GoogleMap getMapa() {
        return mapa;
    }

    public void setMapa(GoogleMap mapa) {
        this.mapa = mapa;
    }

}
