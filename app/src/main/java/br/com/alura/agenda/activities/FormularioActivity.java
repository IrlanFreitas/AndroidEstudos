package br.com.alura.agenda.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        aluno = obterDados();

        Button btnSalvar = findViewById(R.id.formulario_btn_salvar);

        /*Setando o comportamento de click do botão com o listener(escutador)
        * de evento de click, poderia ser outros, double click, long click*/
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aluno = obterDados();
                //Mensagem que aparece abaixo como uma notificação
                //FormularioActivity.this = view.getContext()
                Toast.makeText(FormularioActivity.this, aluno.toString(), Toast.LENGTH_SHORT).show();
            }
        });

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
