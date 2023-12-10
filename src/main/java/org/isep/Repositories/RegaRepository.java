package org.isep.Repositories;

import org.isep.Domain.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class RegaRepository implements Serializable {
    private static final int DIAS = 30;
    private TreeMap<LocalDate, List<Rega>> taskMap = new TreeMap<>();
    private static final String MAP_FILE_PATH = "taskMapRep.ser";
    private LocalDate startDate = null;

    public RegaRepository(String filePath, LocalDate startDate) {
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

    public TreeMap<LocalDate, List<Rega>> getTaskMap() {
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
                        LocalDate dia = startDate;
                        Regularidade regularidade = getRegularidade(parts[2]);
                        Fertilizante fertilizante = null;
                        if (parts.length > 3) {
                            FertirregaMix mix = getFertirregaMix(parts[3]);
                            int recorrenciaMix = Integer.parseInt(parts[4]);
                            if (recorrenciaMix < 0 || mix == null) {
                                fertilizante = null;
                            }else {
                                fertilizante = new Fertilizante(mix, recorrenciaMix);
                            }
                        }
                        Parcela newParcela = new Parcela(parts[0], duracao, regularidade);
                        Rega newRega;
                        int counter = (fertilizante == null) ? 0 : fertilizante.getRecorrencia();
                        if (regularidade.equals(Regularidade.TODOS)) {
                            for (int j = 0; j < DIAS; j++) {
                                if (fertilizante != null && counter == fertilizante.getRecorrencia()) {
                                    newRega = new Rega(dia, horaInicio, horaFim, newParcela, fertilizante);
                                    adicionarTarefa(taskMap, dia, newRega);
                                    dia = dia.plusDays(1);
                                    counter = 1;
                                } else {
                                    newRega = new Rega(dia, horaInicio, horaFim, newParcela);
                                    adicionarTarefa(taskMap, dia, newRega);
                                    dia = dia.plusDays(1);
                                    counter++;
                                }
                            }
                        } else if (regularidade.equals(Regularidade.PARES)) {
                            for (int j = 0; j < DIAS / 2; j++) {
                                if (fertilizante != null && counter == fertilizante.getRecorrencia()) {
                                    if (dia.getDayOfMonth() % 2 != 0) {
                                        dia = dia.plusDays(1);
                                    }
                                    newRega = new Rega(dia, horaInicio, horaFim, newParcela, fertilizante);
                                    adicionarTarefa(taskMap, dia, newRega);
                                    dia = dia.plusDays(2);
                                    counter = 1;
                                } else {
                                    if (dia.getDayOfMonth() % 2 != 0) {
                                        dia = dia.plusDays(1);
                                    }
                                    newRega = new Rega(dia, horaInicio, horaFim, newParcela);
                                    adicionarTarefa(taskMap, dia, newRega);
                                    dia = dia.plusDays(2);
                                    counter++;
                                }
                            }
                        } else if (regularidade.equals(Regularidade.IMPARES)) {
                            for (int j = 0; j < DIAS / 2; j++) {
                                if (fertilizante != null && counter == fertilizante.getRecorrencia()) {
                                    if (dia.getDayOfMonth() == 31) {
                                        newRega = new Rega(dia, horaInicio, horaFim, newParcela, fertilizante);
                                        adicionarTarefa(taskMap, dia, newRega);
                                        dia = dia.plusDays(1);
                                        counter = 1;
                                    } else if (dia.getDayOfMonth() % 2 != 0) {
                                        newRega = new Rega(dia, horaInicio, horaFim, newParcela, fertilizante);
                                        adicionarTarefa(taskMap, dia, newRega);
                                        dia = dia.plusDays(2);
                                        counter = 1;
                                    } else {
                                        dia = dia.plusDays(1);
                                        newRega = new Rega(dia, horaInicio, horaFim, newParcela, fertilizante);
                                        adicionarTarefa(taskMap, dia, newRega);
                                        dia = dia.plusDays(2);
                                        counter = 1;
                                    }
                                } else {
                                    if (dia.getDayOfMonth() == 31) {
                                        newRega = new Rega(dia, horaInicio, horaFim, newParcela);
                                        adicionarTarefa(taskMap, dia, newRega);
                                        dia = dia.plusDays(1);
                                        counter++;
                                    } else if (dia.getDayOfMonth() % 2 != 0) {
                                        newRega = new Rega(dia, horaInicio, horaFim, newParcela);
                                        adicionarTarefa(taskMap, dia, newRega);
                                        dia = dia.plusDays(2);
                                        counter++;
                                    } else {
                                        dia = dia.plusDays(1);
                                        newRega = new Rega(dia, horaInicio, horaFim, newParcela);
                                        adicionarTarefa(taskMap, dia, newRega);
                                        dia = dia.plusDays(2);
                                        counter++;
                                    }
                                }
                            }
                        } else if (regularidade.equals(Regularidade.CADA_3_DIAS)) {
                            for (int j = 0; j < DIAS / 3; j++) {
                                if (fertilizante != null && counter == fertilizante.getRecorrencia()) {
                                    newRega = new Rega(dia, horaInicio, horaFim, newParcela, fertilizante);
                                    adicionarTarefa(taskMap, dia, newRega);
                                    dia = dia.plusDays(3);
                                    counter = 1;
                                } else {
                                    newRega = new Rega(dia, horaInicio, horaFim, newParcela);
                                    adicionarTarefa(taskMap, dia, newRega);
                                    dia = dia.plusDays(3);
                                    counter++;
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("File is empty or does not exist.");
        }
    }

    private static void adicionarTarefa(TreeMap<LocalDate, List<Rega>> result, LocalDate nextDay, Rega newRega) {
        if (!result.containsKey(nextDay)) {
            result.put(nextDay, new ArrayList<>(List.of(newRega)));
        } else {
            if (!result.get(nextDay).contains(newRega)) {
                result.get(nextDay).add(newRega);
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

    private FertirregaMix getFertirregaMix(String mix) {
        switch (mix) {
            case "mix1":
                return FertirregaMix.MIX1;
            case "mix2":
                return FertirregaMix.MIX2;
            case "mix3":
                return FertirregaMix.MIX3;
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

    private TreeMap<LocalDate, List<Rega>> recuperarTaskMapInfo() {
        TreeMap<LocalDate, List<Rega>> getTaskMap = new TreeMap<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(MAP_FILE_PATH);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            getTaskMap = (TreeMap<LocalDate, List<Rega>>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return getTaskMap;
    }


    public List<Rega> tasksARegar(LocalDateTime dataHoraPretendida) {
        List<Rega> result = new ArrayList<>();
        for (Rega t : taskMap.get(dataHoraPretendida.toLocalDate())) {
            if (!dataHoraPretendida.toLocalTime().isBefore(t.getHoraInicioRega()) && !dataHoraPretendida.toLocalTime().isAfter(t.getHoraFimRega())) {
                result.add(t);
            }
        }
        return result;
    }
}
