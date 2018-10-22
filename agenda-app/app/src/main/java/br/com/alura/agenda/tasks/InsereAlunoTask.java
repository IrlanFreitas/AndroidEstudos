package br.com.alura.agenda.tasks;

import android.content.Context;
import android.os.AsyncTask;

import br.com.alura.agenda.activities.FormularioActivity;
import br.com.alura.agenda.converters.AlunoConverter;
import br.com.alura.agenda.models.Aluno;
import br.com.alura.agenda.web.WebClient;

@Deprecated
public class InsereAlunoTask extends AsyncTask <Void, Void, Void> {

    private Context contexto;
    private Aluno aluno;

    public InsereAlunoTask(Context contexto, Aluno aluno) {
        this.contexto = contexto;
        this.aluno = aluno;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        AlunoConverter conversor = new AlunoConverter();

        String json = conversor.converteParaJSON(aluno);

        WebClient client = new WebClient();

        client.insere(json);

        return null;
    }
}
