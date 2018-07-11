package br.com.alura.agenda.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.alura.agenda.R;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.models.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {

    private AlunoDAO alunoDAO;

    private ListView listaAlunos;

    //Método sobrescrito baseado no ciclo de vida da Atividade (Activity)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Chamado caso tenha algum comportamento acontecendo anteiormente
        super.onCreate(savedInstanceState);

        //Ligação da tela(xml) com o comportamento(activity)
        setContentView(R.layout.activity_lista_alunos);

        /*Em vez de usar o TextView para cada um dos elementos
         * melhor usar um ListView para uma lista de elementos como
         * TextView. */
        listaAlunos = (ListView) findViewById(R.id.lista_alunos);

        alunoDAO = new AlunoDAO(this);

        Button btnAdicionar = findViewById(R.id.lista_alunos_btn_adicionar);

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Declarando a intenção para o Android gerenciar.
                Intent intentIrParaFormulario = new Intent( ListaAlunosActivity.this, FormularioActivity.class );
                startActivity(intentIrParaFormulario);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarLista();
    }

    private void carregarLista() {
        /*Sobre o ArrayAdapter
         * necessário passar no construtor, o contexto - que é a atual Activity
         * depois o layout da lista, e como não temos passarei um layout pronto do próprio android
         * e depois a fonte de dados */
        ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunoDAO.getAlunos());

        //Adicionando o Adapter a ListView
        listaAlunos.setAdapter(adapter);
    }
}
