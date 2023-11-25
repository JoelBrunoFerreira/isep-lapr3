package org.isep.Domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class OperacaoCC {
    private Task task;
    private LocalDate data;

    public OperacaoCC(Task task, LocalDate data) {
        this.task = task;
        this.data = data;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof OperacaoCC that)) return false;
        return Objects.equals(task, that.task) && Objects.equals(data, that.data);
    }

    @Override
    public String toString() {
        return String.format("Operação de rega: Dia %s, setor %s, hora de início %s, duração %d, hora de fim %s.", data.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), task.getParcela().getSetor(), task.getHoraInicioRega(), task.getParcela().getDuracao(), task.getHoraFimRega());
    }
}
