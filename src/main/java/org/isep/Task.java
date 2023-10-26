package org.isep;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Task implements Serializable {
    private final int DIAS_PLANO = 30;
    private LocalDate diaInicio;
    private LocalDate diaFim;
    private LocalTime horaInicioRega;
    private LocalTime horaFimRega;
    private Parcela parcela;

    public Task(LocalDate diaInicio, LocalTime horaInicioRega, LocalTime horaFimRega, Parcela parcela) {
        this.diaInicio = diaInicio;
        this.diaFim = diaInicio.plusDays(DIAS_PLANO);
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

    public boolean verificaRegaAtivaDia(LocalDateTime dataHoraAtual) {
        LocalDate diaAtual = dataHoraAtual.toLocalDate();
        return !diaAtual.isBefore(diaInicio) && !diaAtual.isAfter(diaFim);
    }

    @Override
    public String toString() {
        return parcela + "\nDia de Início: " + diaInicio +
                " | Dia Fim: " + diaFim +
                " | Hora de Início de Rega: " + horaInicioRega +
                " | Hora de Fim de Rega :" + horaFimRega;
    }
}
