package org.isep;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Controlador {


    public Controlador(String file) throws IOException {
        getInfo(file);
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
                    map.get(startTime).add(new Parcela(parts[0], duracao, getRecorrencia(parts[2])));
                }
            }
        }
        return map;
    }

    public boolean regaAtiva(Map<LocalTime, List<Parcela>> mapaRega) { //parametro (String horaPretendida)
        LocalTime horaAtual = LocalTime.now(); //LocalTime.parse(horaPretendida);//
        Boolean bool = false;
        for (LocalTime horaKey : mapaRega.keySet()
        ) {

            for (Parcela p : mapaRega.get(horaKey)
            ) {
                if (horaKey.isBefore(horaAtual) && p.getDuracao().isAfter(horaAtual)) {
                    System.out.println(p.getSetor());
                    bool = true;
                }
            }
        }
        return bool;
    }

    private Regularidade getRecorrencia(String valor) {
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
