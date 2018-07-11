package br.com.alura.agenda.models;

import java.io.Serializable;

public class Aluno implements Serializable {

    private Long id;
    private String nome;
    private String endereco;
    private String site;
    private String telefone;
    private Double nota;

    public Aluno(String nome, String endereco, String telefone, String site, Double nota) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.site = site;
        this.nota = nota;
    }

    public Aluno() {}

    @Override
    public String toString() {
        return "Aluno{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", endereco='" + endereco + '\'' +
                ", site='" + site + '\'' +
                ", telefone='" + telefone + '\'' +
                ", nota=" + nota +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
