package br.com.alura.agenda.helpers;

import android.widget.EditText;
import android.widget.RatingBar;

import br.com.alura.agenda.R;
import br.com.alura.agenda.activities.FormularioActivity;
import br.com.alura.agenda.models.Aluno;

public class FormularioHelper {

    //Ter os campos aqui é importante para obter os dados quando necessário
    //sem precisar instanciar novamente
    private EditText editTextNome;
    private EditText editTextEndereco;
    private EditText editTextTelefone;
    private EditText editTextSite;
    private RatingBar ratingNota;
    private Aluno aluno;

    public FormularioHelper(FormularioActivity activity) {

        editTextNome = activity.findViewById(R.id.formulario_nome);
        editTextEndereco = activity.findViewById(R.id.formulario_endereco);
        editTextTelefone = activity.findViewById(R.id.formulario_telefone);
        editTextSite = activity.findViewById(R.id.formulario_site);
        ratingNota = activity.findViewById(R.id.formulario_nota);
        aluno = new Aluno();

    }

    public Aluno getAluno() {
        aluno.setNome(editTextNome.getText().toString());
        aluno.setEndereco(editTextEndereco.getText().toString());
        aluno.setTelefone(editTextTelefone.getText().toString());
        aluno.setSite( editTextSite.getText().toString());
        aluno.setNota( (double) ratingNota.getRating());

        return aluno;
    }

    public void preencherFormulario(Aluno aluno) {
        this.aluno = aluno;
        editTextNome.setText(aluno.getNome());
        editTextEndereco.setText(aluno.getEndereco());
        editTextTelefone.setText(aluno.getTelefone());
        editTextSite.setText(aluno.getSite());
        ratingNota.setRating(Float.parseFloat(aluno.getNota().toString()));
    }
}
