package br.com.alura.agenda.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.alura.agenda.converters.AlunoConverter;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.models.Aluno;
import br.com.alura.agenda.web.WebClient;

/* Entenendo o que pode ou não ser feito na Thread principal */
//Necessário outra classe que faça operações mais
//custosas para que não trave a thread principal

/* Modificando os parametros da classe AsyncTask */

// AsyncTask < 1, 2, 3 >

// 1 - Parametros de entrada do doInBackground recebido pelo execute()
// 2 - Usado para fazer atualizações no meio da execução da tarefa
// 3 - Parametro de retorno dos métodos

// Caso não for utilizar, o recomendado é utilizar a
// classe Void para ficar claro que não está sendo utilizado

public class EnviaAlunosTask extends AsyncTask < Void, Void, String > {

    private final Context context;
    private ProgressDialog dialog = null;

    public EnviaAlunosTask(Context context) {
        this.context = context;
    }

    /* Antes de executar a tarefa, será usado para barra de progresso */
    /* Também executado na thread principal */
    @Override
    protected void onPreExecute() {

       dialog = ProgressDialog.show(context, "Aguarde", "Enviando alunos...", true, true);

    }

    /* Tudo aqui dentro do método será feito
     * pela Thread Secundária */
    @Override
    protected String doInBackground(Void... objects) {

        AlunoDAO alunoDAO = new AlunoDAO(context);

        List<Aluno> alunos = alunoDAO.getAlunos();

        AlunoConverter conversor = new AlunoConverter();

        String json = conversor.converteParaJSON(alunos);

        WebClient client = new WebClient();

        String resposta = client.post(json);


        return resposta;
    }


    /* Será executado depois do método doInBackground */
    /* E recebe como paramêtro o retorno do doInBackground */
    /* E é executado na Thread principal diferente do doInBackground */
    @Override
    protected void onPostExecute(String resposta) {

        //Finalizar o dialog
        dialog.dismiss();

        String aux;

        if ( resposta != null) {
            aux = resposta;
        } else {
            aux = "Erro ao enviar os dados.";
        }

        Toast.makeText(context, aux, Toast.LENGTH_SHORT).show();


    }
}
