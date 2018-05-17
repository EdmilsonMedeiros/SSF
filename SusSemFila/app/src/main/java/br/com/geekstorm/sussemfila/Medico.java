package br.com.geekstorm.sussemfila;

public class Medico {
    private String nome;
    private String cpf;
    private Especialidade especialidade;

    public Medico(String nome, String cpf, Especialidade especialidade){
        this.nome = nome;
        this.cpf = cpf;
        this.especialidade = especialidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }
}
