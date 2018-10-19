package br.com.alura.agenda.dto;

import java.util.Date;
import java.util.List;

import br.com.alura.agenda.models.Aluno;

public class AlunoSync {

    private List<Aluno> alunos;
    private String momentoDaUltimaModificacao;

    public String getMomentoDaUltimaModificacao() {
        return momentoDaUltimaModificacao;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

}
