package org.isep;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Controlador {
    private List<Parcela> parcelas = new ArrayList<>();

    private List<LocalTime> horariosDeRega = new ArrayList<>();

    public Controlador(String file) throws IOException {
        getInfo(file);
    }

    private void getInfo(String file) throws IOException {
        List<String> lines = Files.lines(Paths.get(file)).collect(Collectors.toList());

        if (!lines.isEmpty()) {
            String[] aux = lines.get(0).split(",");
            for (int i = 0; i < aux.length; i++) {
                LocalTime localTime = LocalTime.parse(aux[i], DateTimeFormatter.ofPattern("H:mm"));
                horariosDeRega.add(localTime);
            }
            for (LocalTime lc: horariosDeRega
                 ) {
                for (int i = 1; i < lines.size(); i++) {
                    aux = lines.get(i).split(",");
                    LocalTime duracao = lc.plusMinutes(Integer.parseInt(aux[1]));
                    parcelas.add(new Parcela(aux[0],duracao , getRecorrencia(aux[2])));
                }
            }

        }
    }
    public void printer() {
        for (LocalTime time : horariosDeRega
        ) {
            System.out.println(time);
        }
        for (Parcela parcela : parcelas
        ) {
            System.out.println(parcela);
        }
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
