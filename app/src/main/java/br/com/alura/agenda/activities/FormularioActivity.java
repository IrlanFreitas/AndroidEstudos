package br.com.alura.agenda.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import br.com.alura.agenda.R;
import br.com.alura.agenda.models.Aluno;

public class FormularioActivity extends AppCompatActivity {

    private Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        /*Setando o comportamento de click do botão com o listener(escutador)
        * de evento de click, poderia ser outros, double click, long click*/
        /* findViewById(R.id.formulario_btn_salvar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

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

                aluno = obterDados();

                //Mensagem que aparece abaixo como uma notificação
                //FormularioActivity.this = view.getContext()
                Toast.makeText(FormularioActivity.this, aluno.toString(), Toast.LENGTH_SHORT).show();

                //Finalizando a activity com o conceito de pilha do Android
                finish();

                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private Aluno obterDados() {
        EditText editTextNome = findViewById(R.id.formulario_nome);
        String nome = editTextNome.getText().toString();

        EditText editTextEndereco = findViewById(R.id.formulario_endereco);
        String endereco = editTextEndereco.getText().toString();

        EditText editTextSite = findViewById(R.id.formulario_site);
        String site = editTextSite.getText().toString();

        RatingBar ratingNota = findViewById(R.id.formulario_nota);
        Float nota = ratingNota.getRating();

        return new Aluno(nome, endereco, site, nota);
    }
}
