package org.isep.Controllers;


import org.isep.Domain.Task;
import org.isep.Repositories.TaskRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Controller {
    private static TaskRepository taskRep;

    public Controller(String filePath, LocalDate startDate) {
        taskRep = new TaskRepository(filePath, startDate);
    }

    public boolean checkDate(LocalDateTime dataHoraAtual) {
        return dataHoraAtual.toLocalDate().isBefore(taskRep.getTaskMap().firstKey()) || dataHoraAtual.toLocalDate().isAfter(taskRep.getTaskMap().lastKey());
    }

    public void mostrarTodasAsTasks() {
        System.out.println("PLANO DE REGA");

        for (LocalDate date : taskRep.getTaskMap().keySet()
        ) {
            System.out.printf("Dia: %s\n", date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            for (Task t : taskRep.getTaskMap().get(date)
            ) {
                System.out.println(t);
            }
            System.out.println();
        }
    }

    public void mostrarParcelasARegar(LocalDateTime dataHoraPretendida) {
        List<Task> tasks = taskRep.tasksARegar(dataHoraPretendida);
        if (!tasks.isEmpty()) {
            System.out.println("Setor a regar: ");
            for (Task t : tasks
            ) {
                System.out.println(t.getParcela().getSetor());
                System.out.printf("Faltam %02d minutos para fim.\n", minutosQueFaltam(t.getHoraFimRega(), dataHoraPretendida));
            }
        } else {
            System.out.println("Nada a regar!");
        }
    }

    private long minutosQueFaltam(LocalTime tempoFinal, LocalDateTime dataHoraAtual) {
        Duration duration = Duration.between(dataHoraAtual.toLocalTime(), tempoFinal);
        return duration.toMinutes();
    }
}
