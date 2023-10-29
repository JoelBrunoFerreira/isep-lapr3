package org.isep;

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
                System.out.println("Dia e hora pretendida em formato: \"dd-mm-yyyy hh:mm\"");
                LocalDateTime dataHora = LocalDateTime.parse(read.nextLine(), DateTimeFormatter.ofPattern("d-MM-yyyy H:mm"));
                Controlador c = new Controlador(dataHora);
                c.mostrarTodasAsTasks();
                System.out.println();
                c.mostrarParcelasARegar();
                flag = true;
            } catch (DateTimeParseException e) {
                System.out.println("Formato errado!");
            }
        }
    }
}