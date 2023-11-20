package org.isep.Repositories;
import org.isep.Domain.Parcela;
import org.isep.Domain.Regularidade;
import org.isep.Domain.Task;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TaskRepository implements Serializable {
    private static final int DIAS = 30;
    private TreeMap<LocalDate, List<Task>> taskMap = new TreeMap<>();
        private static final String MAP_FILE_PATH = "taskMapRep.ser";
    private LocalDate startDate = null;

    public TaskRepository(String filePath, LocalDate startDate) {
        if (verificarTaskMapEmpty()) {
            this.startDate = startDate;
            obterTasks(filePath);
            arquivarTaskMapInfo();
        } else {
            taskMap = recuperarTaskMapInfo();
        }
    }

    private boolean verificarTaskMapEmpty() {
        return taskMap.isEmpty();
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public TreeMap<LocalDate, List<Task>> getTaskMap() {
        return taskMap;
    }

    private void obterTasks(String filePath) {
        try {
            List<String> lines;
            lines = Files.readAllLines(Path.of(filePath));
            if (!lines.isEmpty()) {
                String[] horarios = lines.get(0).split(",");
                for (String horario : horarios) {
                    LocalTime horaInicio = LocalTime.parse(horario, DateTimeFormatter.ofPattern("H:mm"));
                    for (int i = 1; i < lines.size(); i++) {
                        String[] parts = lines.get(i).split(",");
                        int duracao = Integer.parseInt(parts[1]);
                        LocalTime horaFim = horaInicio.plusMinutes(duracao);
                        LocalDate nextDay = startDate;
                        Regularidade regularidade = getRegularidade(parts[2]);
                        Task newTask;
                        if (regularidade.equals(Regularidade.TODOS)) {
                            for (int j = 0; j < DIAS; j++) {
                                newTask = new Task(nextDay, horaInicio, horaFim, (new Parcela(parts[0], duracao, regularidade)));
                                adicionarTarefa(taskMap, nextDay, newTask);
                                nextDay = nextDay.plusDays(1);
                            }
                        } else if (regularidade.equals(Regularidade.PARES)) {
                            for (int j = 0; j < DIAS / 2; j++) {
                                if (nextDay.getDayOfMonth() % 2 == 0) {
                                    newTask = new Task(nextDay, horaInicio, horaFim, (new Parcela(parts[0], duracao, regularidade)));
                                    adicionarTarefa(taskMap, nextDay, newTask);
                                    nextDay = nextDay.plusDays(2);
                                } else {
                                    nextDay = nextDay.plusDays(1);
                                    newTask = new Task(nextDay, horaInicio, horaFim, (new Parcela(parts[0], duracao, regularidade)));
                                    adicionarTarefa(taskMap, nextDay, newTask);
                                    nextDay = nextDay.plusDays(2);
                                }
                            }
                        } else if (regularidade.equals(Regularidade.IMPARES)) {
                            for (int j = 0; j < DIAS / 2; j++) {
                                if (nextDay.getDayOfMonth() % 2 != 0) {
                                    newTask = new Task(nextDay, horaInicio, horaFim, (new Parcela(parts[0], duracao, regularidade)));
                                    adicionarTarefa(taskMap, nextDay, newTask);
                                    nextDay = nextDay.plusDays(2);
                                } else {
                                    nextDay = nextDay.plusDays(1);
                                    newTask = new Task(nextDay, horaInicio, horaFim, (new Parcela(parts[0], duracao, regularidade)));
                                    adicionarTarefa(taskMap, nextDay, newTask);
                                    nextDay = nextDay.plusDays(2);
                                }
                            }
                        } else if (regularidade.equals(Regularidade.CADA_3_DIAS)) {
                            for (int j = 0; j < DIAS / 3; j++) {
                                newTask = new Task(nextDay, horaInicio, horaFim, (new Parcela(parts[0], duracao, regularidade)));
                                adicionarTarefa(taskMap, nextDay, newTask);
                                nextDay = nextDay.plusDays(3);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("File is empty or does not exist.");
        }
    }

    private static void adicionarTarefa(TreeMap<LocalDate, List<Task>> result, LocalDate nextDay, Task newTask) {
        if (!result.containsKey(nextDay)) {
            result.put(nextDay, new ArrayList<>(List.of(newTask)));
        } else {
            if (!result.get(nextDay).contains(newTask)) {
                result.get(nextDay).add(newTask);
            }
        }
    }

    private Regularidade getRegularidade(String valor) {
        switch (valor) {
            case "T":
                return Regularidade.TODOS;
            case "I":
                return Regularidade.IMPARES;
            case "P":
                return Regularidade.PARES;
            case "3":
                return Regularidade.CADA_3_DIAS;
            default:
                return null;
        }
    }


    private void arquivarTaskMapInfo() {
        try {
            FileOutputStream fileOut = new FileOutputStream(MAP_FILE_PATH);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOut);
            outputStream.writeObject(taskMap);
            outputStream.close();
            fileOut.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TreeMap<LocalDate, List<Task>> recuperarTaskMapInfo() {
        TreeMap<LocalDate, List<Task>> getTaskMap = new TreeMap<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(MAP_FILE_PATH);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            getTaskMap = (TreeMap<LocalDate, List<Task>>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return getTaskMap;
    }


    public List<Task> tasksARegar(LocalDateTime dataHoraPretendida) {
        List<Task> result = new ArrayList<>();
        for (Task t : taskMap.get(dataHoraPretendida.toLocalDate())) {
            if (!dataHoraPretendida.toLocalTime().isBefore(t.getHoraInicioRega()) && !dataHoraPretendida.toLocalTime().isAfter(t.getHoraFimRega())) {
                result.add(t);
            }
        }
        return result;
    }
}
