package org.isep.Controllers;

import org.isep.Domain.OperacaoCC;
import org.isep.Domain.Task;
import org.isep.Repositories.TaskRepository;
import org.isep.Repositories.CadernoDeCampoRep;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Controller {
    private static TaskRepository taskRep;
    private static CadernoDeCampoRep cCampoRep;
    private LocalDateTime dataHoraPretendida;

    public Controller(String filePath, LocalDate startDate) {
        taskRep = new TaskRepository(filePath, startDate);
        cCampoRep = new CadernoDeCampoRep();
    }

    public void setDataHoraPretendida(LocalDateTime dataHoraPretendida) {
        this.dataHoraPretendida = dataHoraPretendida;
    }

    public boolean verificarData() {
        LocalDate primeiroDia = taskRep.getTaskMap().firstKey();
        LocalDate ultimoDia = taskRep.getTaskMap().lastKey();
        return dataHoraPretendida.toLocalDate().isBefore(primeiroDia) || dataHoraPretendida.toLocalDate().isAfter(ultimoDia);
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

    public void mostrarParcelasARegar() {
        List<Task> tasks = taskRep.tasksARegar(dataHoraPretendida);
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
        System.out.println();
    }

    private long minutosQueFaltam(LocalTime tempoFinal) {
        Duration duration = Duration.between(dataHoraPretendida.toLocalTime(), tempoFinal);
        return duration.toMinutes();
    }

    private void atualizarCadernoDeCampo() {
        if (dataHoraPretendida==null){
            dataHoraPretendida=LocalDateTime.now();
        }
        OperacaoCC op;
        for (LocalDate ld : taskRep.getTaskMap().keySet()) {
            if (!ld.isAfter(dataHoraPretendida.toLocalDate())) {
                for (Task t : taskRep.getTaskMap().get(ld)) {
                    if (!t.getHoraFimRega().isAfter(dataHoraPretendida.toLocalTime())) {
                        op = new OperacaoCC(t, ld);
                        cCampoRep.registarOperacao(op);
                    }
                }
            }
        }
    }

    public void mostrarCadernoDeCampo() {
        atualizarCadernoDeCampo();
        System.out.println("Caderno de Campo");
        if (!cCampoRep.getOperacoesCC().isEmpty()) {
            for (OperacaoCC op : cCampoRep.getOperacoesCC()) {
                System.out.println(op);
            }
        }else{
            System.out.println("Nenhuma operação registada.");
        }
        System.out.println();
    }
}
