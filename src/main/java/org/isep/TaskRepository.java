package org.isep;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository implements Serializable {
    public List<Task> getTaskList() {
        return taskList;
    }

    public static LocalDate getStartDate() {
        return startDate;
    }

    private List<Task> taskList = recuperarTaskListInfo();
    private static final String PLANO_DE_REGA_FILE = "rega.csv";
    private static final String FILE_PATH = "C:\\Users\\vns30\\Documents\\ISEP\\LEI_2\\LAPR3\\sem3pi2023_24_g311\\tasksRep.ser";

    private static LocalDate startDate = null;

    public TaskRepository() {
        if (verificarTaskListEmpty()) {
            startDate = LocalDate.now();
            obterTasks();
            arquivarTaskListInfo();
        }
    }

    private boolean verificarTaskListEmpty() {
        return taskList.isEmpty();
    }

    private void obterTasks() {
        try {
            List<String> lines = null;
            lines = Files.readAllLines(Path.of(PLANO_DE_REGA_FILE));
            if (!lines.isEmpty()) {
                String[] horarios = lines.get(0).split(",");
                for (String horario : horarios) {
                    LocalTime horaInicio = LocalTime.parse(horario, DateTimeFormatter.ofPattern("H:mm"));
                    for (int i = 1; i < lines.size(); i++) {
                        String[] parts = lines.get(i).split(",");
                        int duracao = Integer.parseInt(parts[1]);
                        LocalTime horaFim = horaInicio.plusMinutes(duracao);
                        taskList.add(new Task(startDate, horaInicio, horaFim, (new Parcela(parts[0], duracao, getRegularidade(parts[2])))));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    private void arquivarTaskListInfo() {
        try {
            FileOutputStream fileOut = new FileOutputStream(FILE_PATH);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOut);
            outputStream.writeObject(taskList);
            outputStream.close();
            fileOut.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Task> recuperarTaskListInfo() {
        List<Task> getListings = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(FILE_PATH);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            getListings = (List<Task>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return getListings;
    }

    /**
     * Mostra as parcelas que estão atualmente a ser regadas
     *
     * @param diaHoraAtual
     */
    public List<Task> parcelasComRegaAtiva(LocalDateTime diaHoraAtual) {
        List<Task> resultado = new ArrayList<>();
        for (Task task : taskList) {
            if (task.verificaRegaAtivaHoras(diaHoraAtual) && task.verificarRegularidadeDias(diaHoraAtual)) {
                resultado.add(task);
            }
        }
        return resultado;
    }
}
