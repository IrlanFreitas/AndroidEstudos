package br.com.alura.agenda.helpers;

import android.widget.EditText;
import android.widget.TextView;

import br.com.alura.agenda.R;
import br.com.alura.agenda.activities.InformacoesActivity;
import br.com.alura.agenda.models.Aluno;

public class InformacoesHelper {

    private TextView campoId;
    private TextView campoNome;
    private TextView campoEndereco;
    private TextView campoTelefone;
    private TextView campoSite;
    private TextView campoNota;
    private TextView campoCaminhoFoto;


    public InformacoesHelper(InformacoesActivity activity) {
        campoId = activity.findViewById(R.id.informacoes_id);
        campoNome = activity.findViewById(R.id.informacoes_nome);
        campoEndereco = activity.findViewById(R.id.informacoes_endereco);
        campoTelefone = activity.findViewById(R.id.informacoes_telefone);
        campoSite = activity.findViewById(R.id.informacoes_site);
        campoNota = activity.findViewById(R.id.informacoes_nota);
        campoCaminhoFoto = activity.findViewById(R.id.informacoes_caminhoFoto);
    }

    public void preencherFormulario(Aluno aluno){
        campoId.setText(aluno.getId().toString());
        campoNome.setText(aluno.getNome());
        campoEndereco.setText(aluno.getEndereco());
        campoTelefone.setText(aluno.getTelefone());
        campoSite.setText(aluno.getSite());
        campoNota.setText(aluno.getNota().toString());
        campoCaminhoFoto.setText(aluno.getCaminhoFoto());
    }

}
