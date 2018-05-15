package br.com.geekstorm.sussemfila;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sus sem Fila on 10/05/2018.
 */

public class Especialidade implements Serializable {
    private int id;
    private String descricao;

    public Especialidade(){}

    public Especialidade(int id, String descricao){
        this.id = id;
        this.descricao = descricao;
    }

    public String toString() {
        return this.descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
