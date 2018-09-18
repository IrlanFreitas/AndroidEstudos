package br.com.alura.agenda.service;

import br.com.alura.agenda.models.Aluno;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AlunoService {

    @POST(value = "aluno")
    Call<Void> insere(@Body Aluno aluno);

}
