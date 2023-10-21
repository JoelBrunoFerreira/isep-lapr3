package org.isep;

import java.io.IOException;
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

public class Controlador {

    private static LocalDate startDate = null;

    public Controlador(String file) throws IOException {
        getInfo(file);
        startDate = LocalDate.now();
    }


    public Map<LocalTime, List<Parcela>> getInfo(String file) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(file));

        Map<LocalTime, List<Parcela>> map = new TreeMap<>();

        if (!lines.isEmpty()) {
            String[] horarios = lines.get(0).split(",");

            for (String horario : horarios) {
                LocalTime localTime = LocalTime.parse(horario, DateTimeFormatter.ofPattern("H:mm"));
                map.put(localTime, new ArrayList<>());
            }

            for (int i = 1; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(",");
                for (int j = 0; j < horarios.length; j++) {
                    LocalTime startTime = LocalTime.parse(horarios[j], DateTimeFormatter.ofPattern("H:mm"));
                    LocalTime duracao = startTime.plusMinutes(Integer.parseInt(parts[1]));
                    map.get(startTime).add(new Parcela(parts[0], duracao, getRegularidade(parts[2])));
                }
            }
        }
        return map;
    }

    public boolean regaAtiva(Map<LocalTime, List<Parcela>> mapaRega) { //parametro (String horaPretendida)
        LocalDateTime dataHoraAtual = LocalDateTime.now(); //LocalTime.parse(horaPretendida);//
        boolean bool = false;
        for (LocalTime horaKey : mapaRega.keySet()
        ) {

            for (Parcela p : mapaRega.get(horaKey)
            ) {
                if (horaKey.isBefore(dataHoraAtual.toLocalTime()) && p.getDuracao().isAfter(dataHoraAtual.toLocalTime()) && verificarRegularidade(p.getRegularidade(), dataHoraAtual.toLocalDate())) {
                    System.out.println(p.getSetor());
                    bool = true;
                }
            }
        }
        return bool;
    }

    private boolean verificarRegularidade(Regularidade regularidade, LocalDate diaAtual) {
        int dia = diaAtual.getDayOfMonth();
        int daysSinceStart = (int) startDate.until(diaAtual).getDays();
        boolean result = false;
        switch (regularidade) {
            case TODOS:
                result = true;
                break;
            case PARES:
                if (dia % 2 == 0) {
                    result = true;
                }
                break;
            case IMPARES:
                if (dia % 2 != 0) {
                    result = true;
                }
                break;
            case CADA_3_DIAS:
                result = daysSinceStart >= 0 && (daysSinceStart - dia) % 3 == 0;
                break;
        }
        return result;
    }

    private Regularidade getRegularidade(String valor) {
        Regularidade result = null;
        switch (valor) {
            case "T":
                result = Regularidade.TODOS;
                break;
            case "I":
                result = Regularidade.IMPARES;
                break;
            case "P":
                result = Regularidade.PARES;
                break;
            case "3":
                result = Regularidade.CADA_3_DIAS;
                break;
        }
        return result;
    }
}
