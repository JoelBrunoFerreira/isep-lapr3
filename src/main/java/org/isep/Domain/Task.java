package org.isep.Domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Task implements Serializable {
    //private final int DIAS_PLANO = 30;
    private LocalDate diaInicio;
    private LocalDate diaFim;
    private LocalTime horaInicioRega;
    private LocalTime horaFimRega;
    private Parcela parcela;

    public LocalTime getHoraInicioRega() {
        return horaInicioRega;
    }

    public LocalDate getDiaInicio() {
        return diaInicio;
    }

    public LocalDate getDiaFim() {
        return diaFim;
    }

    public Task(LocalDate diaInicio, LocalTime horaInicioRega, LocalTime horaFimRega, Parcela parcela) {
        this.diaInicio = diaInicio;
        this.diaFim = diaInicio;//diaInicio.plusDays(1);
        this.horaInicioRega = horaInicioRega;
        this.horaFimRega = horaFimRega;
        this.parcela = parcela;
    }

    public LocalTime getHoraFimRega() {
        return horaFimRega;
    }

    public Parcela getParcela() {
        return parcela;
    }

    public boolean verificaRegaAtivaHoras(LocalDateTime dataHoraAtual) {
        LocalTime horaAtual = dataHoraAtual.toLocalTime();
        return !horaAtual.isBefore(horaInicioRega) && !horaAtual.isAfter(horaFimRega);
    }

    public boolean verificarRegularidadeDias(LocalDateTime dataHoraAtual) {
        int hoje = dataHoraAtual.getDayOfMonth();
        switch (parcela.getRegularidade()) {
            case TODOS:
                return true;
            case PARES:
                return hoje % 2 == 0;
            case IMPARES:
                return hoje % 2 != 0;
            case CADA_3_DIAS:
                return diaInicio.getDayOfMonth() >= 0 && (diaInicio.getDayOfMonth() - hoje) % 3 == 0;
            default:
                return false;
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Task task)) return false;
        return Objects.equals(diaInicio, task.diaInicio) && Objects.equals(diaFim, task.diaFim) && Objects.equals(horaInicioRega, task.horaInicioRega) && Objects.equals(horaFimRega, task.horaFimRega) && Objects.equals(parcela, task.parcela);
    }

    @Override
    public int hashCode() {
        return Objects.hash(diaInicio, diaFim, horaInicioRega, horaFimRega, parcela);
    }

    @Override
    public String toString() {
       // return String.format("")
        return parcela + " | Hora de Início de Rega: " + horaInicioRega +
                " | Hora de Fim de Rega: " + horaFimRega;
    }
}
