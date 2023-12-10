package org.isep.Domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Rega implements Comparable<Rega>, Serializable {
    //private final int DIAS_PLANO = 30;
    private LocalDate diaInicio;
    private LocalDate diaFim;
    private LocalTime horaInicioRega;
    private LocalTime horaFimRega;
    private Parcela parcela;
    private Fertilizante fertilizante;

    public LocalTime getHoraInicioRega() {
        return horaInicioRega;
    }

    public LocalDate getDiaInicio() {
        return diaInicio;
    }

    public LocalDate getDiaFim() {
        return diaFim;
    }

    public Rega(LocalDate diaInicio, LocalTime horaInicioRega, LocalTime horaFimRega, Parcela parcela) {
        this.diaInicio = diaInicio;
        this.diaFim = diaInicio;//diaInicio.plusDays(1);
        this.horaInicioRega = horaInicioRega;
        this.horaFimRega = horaFimRega;
        this.parcela = parcela;
        this.fertilizante = null;
    }

    public Rega(LocalDate diaInicio, LocalTime horaInicioRega, LocalTime horaFimRega, Parcela parcela, Fertilizante fertilizante) {
        this.diaInicio = diaInicio;
        this.horaInicioRega = horaInicioRega;
        this.horaFimRega = horaFimRega;
        this.parcela = parcela;
        this.fertilizante = fertilizante;
    }

    public Fertilizante getFertilizante() {
        return fertilizante;
    }

    public LocalTime getHoraFimRega() {
        return horaFimRega;
    }

    public Parcela getParcela() {
        return parcela;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Rega rega)) return false;
        return Objects.equals(diaInicio, rega.diaInicio) && Objects.equals(diaFim, rega.diaFim) && Objects.equals(horaInicioRega, rega.horaInicioRega) && Objects.equals(horaFimRega, rega.horaFimRega) && Objects.equals(parcela, rega.parcela);
    }

    @Override
    public int hashCode() {
        return Objects.hash(diaInicio, diaFim, horaInicioRega, horaFimRega, parcela);
    }

    @Override
    public String toString() {
            return String.format("%s, hora de início %s, hora de fim %s.", parcela, horaInicioRega, horaFimRega);

    }

    @Override
    public int compareTo(Rega o) {
        return this.parcela.getSetor().compareTo(o.getParcela().getSetor());
    }
}
