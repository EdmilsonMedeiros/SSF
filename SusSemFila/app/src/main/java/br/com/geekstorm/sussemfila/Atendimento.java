package br.com.geekstorm.sussemfila;

import android.text.format.Time;

import java.util.Date;

public class Atendimento {
    private int id;
    private String dt_atendimento;
    private String hr_atendimento;

    public Atendimento(){}
    public Atendimento(int id, String dt_atendimento, String hr_atendimento){
        this.id = id;
        this.dt_atendimento = dt_atendimento;
        this.hr_atendimento = hr_atendimento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDt_atendimento() {
        return dt_atendimento;
    }

    public void setDt_atendimento(String dt_atendimento) {
        this.dt_atendimento = dt_atendimento;
    }

    public String getHr_atendimento() {
        return hr_atendimento;
    }

    public void setHr_atendimento(String hr_atendimento) {
        this.hr_atendimento = hr_atendimento;
    }

    public String toString() {
        return this.dt_atendimento+" - "+this.hr_atendimento;
    }
}
