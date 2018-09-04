package br.com.alura.agenda.models;

import java.io.Serializable;
import java.util.List;

public class Prova implements Serializable {

    private Long id;
    private String materia;
    private String data;
    private List<Topico> topicos;

    public Prova(String materia, String data, List<Topico> topicos) {
        this.materia = materia;
        this.data = data;
        this.topicos = topicos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<Topico> getTopicos() {
        return topicos;
    }

    public void setTopicos(List<Topico> topicos) {
        this.topicos = topicos;
    }

    @Override
    public String toString() {
        return materia;
    }
}
