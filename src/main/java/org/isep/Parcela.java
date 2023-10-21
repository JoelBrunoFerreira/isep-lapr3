package org.isep;

public class Parcela {
    private String setor;
    private int duracao;
    private Regularidade regularidade;
    public Parcela(String setor, int duracao, Regularidade recorrencia) {
        this.setor = setor;
        this.duracao = duracao;
        this.regularidade = recorrencia;
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

}
