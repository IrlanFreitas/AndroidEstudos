package br.com.alura.agenda.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;

import br.com.alura.agenda.R;
import br.com.alura.agenda.helpers.InformacoesHelper;
import br.com.alura.agenda.models.Aluno;

public class InformacoesActivity extends AppCompatActivity {

    private InformacoesHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacoes);

        helper = new InformacoesHelper(this);

        Aluno aluno = (Aluno) getIntent().getSerializableExtra("aluno");
        if (aluno != null){
            helper.preencherFormulario(aluno);
        }

        Button btnVoltar = findViewById(R.id.informacoes_btn_voltar);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
