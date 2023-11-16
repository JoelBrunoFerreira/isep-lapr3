package org.isep.Controllers;

import org.isep.Domain.Task;
import org.isep.Repositories.TaskRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Controlador {
    private static TaskRepository taskRep;
    private static LocalDateTime dataHoraAtual;

    public Controlador(LocalDateTime dataHoraAtual) {
        taskRep = new TaskRepository();
        this.dataHoraAtual = dataHoraAtual;
    }
    public boolean checkDate(){
        return dataHoraAtual.toLocalDate().isBefore(taskRep.getTaskList().get(0).getDiaInicio()) || dataHoraAtual.toLocalDate().isAfter(taskRep.getTaskList().get(0).getDiaFim());
    }
    public void mostrarTodasAsTasks() {
        System.out.println("PLANO DE REGA");
        for (int i = 0; i < taskRep.getTaskList().size(); i++) {
            System.out.println((i+1) + " - " + taskRep.getTaskList().get(i));
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
        } else {
            System.out.println("Nada a regar!");
        }
    }

    private long minutosQueFaltam(LocalTime tempoFinal) {
        Duration duration = Duration.between(dataHoraAtual.toLocalTime(), tempoFinal);
        return duration.toMinutes();
    }
}
