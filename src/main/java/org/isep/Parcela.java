package org.isep;

import java.time.LocalTime;

public class Parcela {
    private String setor;
    private LocalTime duracao;
    private Regularidade regularidade;
    public Parcela(String setor, LocalTime duracao, Regularidade recorrencia) {
        this.setor = setor;
        this.duracao = duracao;
        this.regularidade = recorrencia;
    }

    public String getSetor() {
        return setor;
    }

    public LocalTime getDuracao() {
        return duracao;
    }

    public Regularidade getRegularidade() {
        return regularidade;
    }

    @Override
    public String toString() {
        return "Parcela{" +
                "setor='" + setor + '\'' +
                ", duracao=" + duracao +
                ", regularidade=" + regularidade +
                '}';
    }
}
