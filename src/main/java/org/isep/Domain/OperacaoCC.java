package org.isep.Domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class OperacaoCC implements Serializable {
    private Rega rega;
    private LocalDate data;

    public OperacaoCC(Rega rega, LocalDate data) {
        this.rega = rega;
        this.data = data;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof OperacaoCC that)) return false;
        return Objects.equals(rega, that.rega) && Objects.equals(data, that.data);
    }

    @Override
    public String toString() {
        if (this.rega.getFertilizante() == null) {
            return String.format("Operação de rega: Dia %s, setor %s, hora de início %s, duração %d, hora de fim %s.", data.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), rega.getParcela().getSetor(), rega.getHoraInicioRega(), rega.getParcela().getDuracao(), rega.getHoraFimRega());
        } else {
            return String.format("Operação de rega: Dia %s, setor %s, hora de início %s, duração %d, hora de fim %s, mistura: %s.", data.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), rega.getParcela().getSetor(), rega.getHoraInicioRega(), rega.getParcela().getDuracao(), rega.getHoraFimRega(), rega.getFertilizante().getMix());
        }
    }
}
