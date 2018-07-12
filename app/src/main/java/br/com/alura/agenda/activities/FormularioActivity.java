package br.com.alura.agenda.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.alura.agenda.R;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.helpers.FormularioHelper;
import br.com.alura.agenda.models.Aluno;

public class FormularioActivity extends AppCompatActivity {

    private FormularioHelper helper;

    private AlunoDAO alunoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        helper = new FormularioHelper(this);

        alunoDAO = new AlunoDAO(this);

        //getIntent() devolve a intent que foi usada para abrir a activity
        Aluno aluno = (Aluno)getIntent().getSerializableExtra("aluno");

        if (aluno != null) {
            Log.i("ALUNO", aluno.toString());
            helper.preencherFormulario(aluno);
        }

        //
        //Setando o comportamento de click do botão com o listener(escutador)
        // de evento de click, poderia ser outros, double click, long click*/
        // findViewById(R.id.formulario_btn_salvar).setOnClickListener(new View.OnClickListener() {
        //  @Override
        //   public void onClick(View view) { }
        //});

    }

    //Modificando a ActionBar assim que é criada
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Inflar é transformar o xml em view
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_formulario, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //Sobrecrevendo o método que é chamado quando um item de menu da Action Bar é clicado
    //Forma mais elegante de fazer, poderia ter pego o id do item de menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.formulario_menu_item_adicionar: {

                String acao = "";

                Aluno aluno = helper.getAluno();
                if (aluno.getId() == null) {
                    alunoDAO.inserir(aluno);
                    acao = " Cadastrado com sucesso. ";
                } else {
                    alunoDAO.atualizar(aluno);
                    acao = " Atualizado com sucesso. ";
                }

                //Mensagem que aparece abaixo como uma notificação
                //FormularioActivity.this = view.getContext()
                Toast.makeText(FormularioActivity.this, acao, Toast.LENGTH_SHORT).show();

                //Finalizando a activity com o conceito de pilha do Android
                finish();

                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }


}
