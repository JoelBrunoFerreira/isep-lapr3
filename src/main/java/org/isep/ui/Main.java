package org.isep.ui;

import org.isep.Controllers.Controlador;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner read = new Scanner(System.in);
        boolean flag = false;

        while (!flag) {
            try {
                System.out.println("Dia e hora pretendida para verificação em formato: \"dd-MM-yyyy HH:mm\"");
                LocalDateTime dataHora = LocalDateTime.parse(read.nextLine(), DateTimeFormatter.ofPattern("d-MM-yyyy H:mm"));
                Controlador c = new Controlador(dataHora);

                if (c.checkDate()) {
                    System.out.println("Fora do plano de rega!");
                } else {
                    c.mostrarTodasAsTasks();
                    System.out.println();
                    c.mostrarParcelasARegar();
                }
                flag = true;
            } catch (DateTimeParseException e) {
                System.out.println("Formato errado!");
            }
        }
    }
}