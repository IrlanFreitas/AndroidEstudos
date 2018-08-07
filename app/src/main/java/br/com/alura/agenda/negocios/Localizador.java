package br.com.alura.agenda.negocios;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import br.com.alura.agenda.fragments.MapaFragment;

public class Localizador extends LocationCallback implements GoogleApiClient.ConnectionCallbacks, LocationListener {

    private final GoogleApiClient client;
    private final MapaFragment mapaFragment;
    private final Context contexto;

    public Localizador(Context contexto, MapaFragment mapaFragment) {


        /* GoogleApiClient - Utilizando serviços */
        // Classe utilizada quando se quer utilizar API/Serviços
        // já existentes no celular do usuário como: GPS...

        //Adicionando a classe que irá tratar o recebimento do serviço
        client = new GoogleApiClient.Builder(contexto)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this) //Adicionando a classe que irá tratar o recebimento do serviço
                .build();

        // Conectando com a Serviços/API soliticatado, nesse cado GPS
        // porem, é assíncrono, é necessário que alguem trate
        // o resultado disso
        client.connect();

        this.mapaFragment = mapaFragment;
        this.contexto = contexto;

    }

    /* Recebendo o valor do método connect() quando conectar */
    // depois da classe implementar o ConnectionCallbacks
    // ela pode tratar o resultado o connect e
    // com isso utilizar o serviço de gps do usuário

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // Descrevendo quais dados se quer receber do gps
        // Configuração de GPS
        LocationRequest request = new LocationRequest();

        //Atualizar de um em um segundo
        request.setInterval(1000);
        //Atualizar a cada 50 metros, com o usuário para não gasta bateria
        request.setSmallestDisplacement(50);
        //Priorizando a precisão do gps e não báteria
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        FusedLocationProviderClient locationClient = LocationServices.getFusedLocationProviderClient(contexto);

        if (ActivityCompat.checkSelfPermission(contexto, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(contexto, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            String[] permissoes = {
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            };

            ActivityCompat.requestPermissions(
                    mapaFragment.getActivity(), permissoes, 1231);

            return;
        }

        locationClient.requestLocationUpdates(request, this, null);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    /* Obtendo os dados enquando o usuário atualiza */
    // se movendo

    @Override
    public void onLocationChanged(Location location) {

        LatLng coordenada = new LatLng(location.getLatitude(), location.getLongitude());

        mapaFragment.centralizaEm(coordenada);

    }

}
