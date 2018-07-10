package br.com.alura.agenda.models;

public class Aluno {

    private String nome;
    private String endereco;
    private String site;
    private Float nota;

    public Aluno(String nome, String endereco, String site, Float nota) {
        this.nome = nome;
        this.endereco = endereco;
        this.site = site;
        this.nota = nota;
    }

    public Float getNota() {
        return nota;
    }

    public void setNota(Float nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "nome='" + nome + '\'' +
                ", endereco='" + endereco + '\'' +
                ", site='" + site + '\'' +
                ", nota=" + nota +
                '}';
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
}
