package br.com.geekstorm.sussemfila;

/**
 * Created by Sus sem Fila on 10/05/2018.
 */

public class Especialidade {
    private String id;
    private String descricao;

    public Especialidade(){}

    public Especialidade(String id, String descricao){
        this.id = id;
        this.descricao = descricao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
