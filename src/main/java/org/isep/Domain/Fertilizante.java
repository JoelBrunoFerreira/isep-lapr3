package org.isep.Domain;

import java.io.Serializable;

public class Fertilizante implements Serializable {
    private FertirregaMix mix;
    private int recorrencia;

    public Fertilizante(FertirregaMix mix, int recorrencia) {
        this.mix = mix;
        this.recorrencia = recorrencia;
    }

    public FertirregaMix getMix() {
        return mix;
    }

    public int getRecorrencia() {
        return recorrencia;
    }
}
