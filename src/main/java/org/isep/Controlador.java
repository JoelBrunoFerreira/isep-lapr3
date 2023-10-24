package org.isep;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Controlador {
    private static TaskRepository taskRep;
    private static LocalDateTime dataHoraAtual;

    public Controlador() {
        taskRep = new TaskRepository();
        dataHoraAtual = LocalDateTime.now();
    }

    public void mostrarTodasAsTasks() {
        System.out.println("LISTA DE TAREFAS");
        for (Task task : taskRep.getTaskList()
        ) {
            System.out.println(task);
        }
    }

    public void mostrarParcelasARegar() {
        List<Task> tasks = taskRep.parcelasComRegaAtiva(dataHoraAtual);
        if (!tasks.isEmpty()) {
            System.out.println("Setor a regar: ");
            for (Task t : tasks
            ) {
                System.out.println(t.getParcela().getSetor());
                System.out.printf("Faltam %02d minutos para fim.\n", minutosQueFaltam(t.getHoraFimRega()));
            }
        }
    }

    private long minutosQueFaltam(LocalTime tempoFinal) {
        Duration duration = Duration.between(dataHoraAtual.toLocalTime(), tempoFinal);
        return duration.toMinutes();
    }
}
