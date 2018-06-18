package br.com.geekstorm.sussemfila;

public class Agendamento {
    long id;
    String datamarcada;
    String horariomarcado;
    String especialidade;
    String status;
    String hospital;


    public Agendamento(){}
    public Agendamento(long id, String data,String horario, String especialidade, String status, String hospital){
        this.id = id;
        this.datamarcada = data;
        this.horariomarcado = horario;
        this.especialidade = especialidade;
        this.status = status;
        this.hospital = hospital;
    }

    @Override
    public String toString() {
        return "Data: "+datamarcada+" Especialidade: "+especialidade+" Status: "+status;
    }
}
