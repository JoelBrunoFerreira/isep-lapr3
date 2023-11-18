package org.isep.Domain;

import java.io.Serializable;

public class Parcela implements Serializable {
    private String setor;
    private int duracao;
    private Regularidade regularidade;

    public Parcela(String setor, int duracao, Regularidade regularidade) {
        this.setor = setor;
        this.duracao = duracao;
        this.regularidade = regularidade;
    }

    public String getSetor() {
        return setor;
    }

    public int getDuracao() {
        return duracao;
    }

    public Regularidade getRegularidade() {
        return regularidade;
    }

    @Override
    public String toString() {

        return String.format("Setor: %s | Duração: %2d(min) | Regularidade: %s",setor,duracao,regularidade);
        //return "Setor: " + setor + " | Duração(min): " + duracao + " | Regularidade: " + regularidade;
    }
}
