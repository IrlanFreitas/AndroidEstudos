package br.com.alura.agenda.activities;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import br.com.alura.agenda.R;
import br.com.alura.agenda.fragments.DetalheProvaFragment;
import br.com.alura.agenda.fragments.InserirProvaFragment;
import br.com.alura.agenda.fragments.ListaProvasFragment;
import br.com.alura.agenda.models.Prova;


public class ProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        Button btnAdicionar = findViewById(R.id.fragment_lista_provas_btn_adicionar);

        /* O código abaixo indica utilização de fragment */
        /* Manipulador de Fragments */
        final FragmentManager fragmentManager = getSupportFragmentManager();

        /* Necessário gerenciar transações para manipular o fragment */
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        /* If's sabem se o elemento está na tela, o que indica se estão
         * na orientação certa para cada layout */

        /* Substituindo o frame principal pelo fragment */
        if ( !estaNoModoPaisagem() ) {
            transaction.replace(R.id.provas_frame_principal, new ListaProvasFragment());
        }

        /* Usando os resources como controladores */

        /* if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
        *  O código acima não funciona, esse é o erro:
        *  java.lang.NullPointerException: Attempt to invoke virtual method 'void com.android.server.wm.DisplayContent.getLogicalDisplayRect(android.graphics.Rect)' on a null object reference*/

         if (estaNoModoPaisagem()) {

        /* if ( findViewById(R.id.provas_frame_lista) != null) {
        * Outra forma de fazer.*/

            transaction.replace(R.id.provas_frame_lista, new ListaProvasFragment());
            transaction.replace(R.id.provas_frame_detalhe, new DetalheProvaFragment());
        }

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.provas_frame_principal, new InserirProvaFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        /* Necessário commit para funcione */
        transaction.commit();




    }

    private boolean estaNoModoPaisagem() {
        return getResources().getBoolean(R.bool.modoPaisagem);
    }

    /* Colocando a responsábilidade no lugar certo */
    // Nesse momento a activity fica responsável
    // por modificar os fragments (a depender do layout)
    // e o fragment não precisa conhecer o elemento
    // que ele irá modificar

    public void selecionaProva(Prova prova) {

        FragmentManager manager = getSupportFragmentManager();

        if (!estaNoModoPaisagem()) {

            FragmentTransaction transaction = manager.beginTransaction();

            DetalheProvaFragment detalheFragment = new DetalheProvaFragment();

            /* Uma das formas de passagem de valor */
            Bundle parametros = new Bundle();
            parametros.putSerializable("prova", prova);

            detalheFragment.setArguments(parametros);

            transaction.replace(R.id.provas_frame_principal, detalheFragment);

            /* Criando um ponto na pilha de activities para voltar corretamente */
            transaction.addToBackStack(null);

            transaction.commit();

        } else {

            DetalheProvaFragment  detalhesFragment = (DetalheProvaFragment) manager.findFragmentById(R.id.provas_frame_detalhe);

            detalhesFragment.preencher(prova);

        }

    }

    public void adicionar(View view) {

        Toast.makeText(this, "Teste!", Toast.LENGTH_SHORT).show();

    }
}
