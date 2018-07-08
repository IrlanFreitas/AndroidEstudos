package br.com.alura.agenda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ListaAlunosActivity extends AppCompatActivity {

    //Método sobrescrito baseado no ciclo de vida da Atividade (Activity)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Chamado caso tenha algum comportamento acontecendo anteiormente
        super.onCreate(savedInstanceState);
        //Ligação da tela(xml) com o comportamento(activity)
        setContentView(R.layout.activity_lista_alunos);
    }
}
