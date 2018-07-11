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

    public FormularioHelper(FormularioActivity activity) {

        editTextNome = activity.findViewById(R.id.formulario_nome);
        editTextEndereco = activity.findViewById(R.id.formulario_endereco);
        editTextTelefone = activity.findViewById(R.id.formulario_telefone);
        editTextSite = activity.findViewById(R.id.formulario_site);
        ratingNota = activity.findViewById(R.id.formulario_nota);

    }

    public Aluno getAluno() {
        return new Aluno(
                editTextNome.getText().toString(),
                editTextEndereco.getText().toString(),
                editTextTelefone.getText().toString(),
                editTextSite.getText().toString(),
                (double) ratingNota.getRating() );
    }
}
