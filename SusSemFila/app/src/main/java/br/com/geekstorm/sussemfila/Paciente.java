package br.com.geekstorm.sussemfila;

import java.io.Serializable;

public class Paciente implements Serializable {
    private String nome;
    private String datanascimento;
    private String cpf;
    private String endereco;
    private String cidade;

    private String img;

    public Paciente(){

    }

    public Paciente(String nome, String cpf){
        this.nome = nome;
        this.cpf = cpf;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDatanascimento() {
        return datanascimento;
    }

    public void setDatanascimento(String datanascimento) {
        this.datanascimento = datanascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

}
