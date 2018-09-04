package br.com.alura.agenda.converters;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

import br.com.alura.agenda.models.Aluno;

public class AlunoConverter {

    public String converteParaJSON(List<Aluno> alunos) {

        /* Classe que ajuda a criar o JSON de envio */
        // Inicialmente acho o trabalho muito massante
        JSONStringer js = new JSONStringer();

        try {

            /* Representação do JSON por meio do métodos */
            /* { "list":[ { "aluno" : [ */
            js.object().key("list").array().object().key("aluno").array();

            for (Aluno aluno : alunos) {
                js.object()
                        .key("id").value(aluno.getId())
                        .key("nome").value(aluno.getNome())
                        .key("endereco").value(aluno.getEndereco())
                        .key("telefone").value(aluno.getTelefone())
                        .key("site").value(aluno.getSite())
                        .key("nota").value(aluno.getNota())
                        .endObject();
            }

            js.endArray().endObject().endArray().endObject();
            /*] } ] }*/
            /* Fechamento das respectivas tags */

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Erro de Conversão", e.toString());
        }

        Log.i("Conversão", js.toString());

        return js.toString();
    }

    public String converteParaJSON(Aluno aluno) {

        /* Classe que ajuda a criar o JSON de envio */
        // Inicialmente acho o trabalho muito massante
        JSONStringer js = new JSONStringer();

        try {

            js.object()
                    .key("id").value(aluno.getId())
                    .key("nome").value(aluno.getNome())
                    .key("endereco").value(aluno.getEndereco())
                    .key("telefone").value(aluno.getTelefone())
                    .key("site").value(aluno.getSite())
                    .key("nota").value(aluno.getNota())
                    .endObject();

        } catch (JSONException e1) {
            e1.printStackTrace();
        }


        Log.i("Conversão", js.toString());

        return js.toString();
    }
}
