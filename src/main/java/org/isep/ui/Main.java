package org.isep.ui;


import org.isep.Controllers.Controller;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    private static Scanner read = new Scanner(System.in);
    public static Controller controller;
    private static LocalDate startDate = LocalDate.now();
    private static String originalFilePath = "plano.csv";
    private static String filePath;

    public static void main(String[] args) {
        boolean flag = true;
        while (flag) {
            getPlanoRega();
            controller = new Controller(filePath, startDate);
            getTasks();
            System.out.println("Continuar? S/N");
            String answer = read.nextLine();
            if (answer.equalsIgnoreCase("n")) {
                flag = false;
            }
        }
    }

    private static void getPlanoRega() {
        boolean flag = false;
        while (!flag) {
            try {
                System.out.println("Utilizar novo plano de rega? S/N");
                String fileInputAnswer = read.nextLine();
                if (fileInputAnswer.equalsIgnoreCase("s")) {
                    System.out.println("Nome do ficheiro:");
                    filePath = read.nextLine();
                    if (checkIfFileExists(filePath)) {
                        flag = true;
                        System.out.println("Data para início do plano: dd-mm-yyyy");
                        startDate = LocalDate.parse(read.nextLine(), DateTimeFormatter.ofPattern("d-MM-yyyy"));
                    } else {
                        System.out.println("Ficheiro não encontrado.");
                    }
                } else if (fileInputAnswer.equalsIgnoreCase("n")) {
                    filePath = originalFilePath;
                    flag = true;
                } else {
                    System.out.println("Resposta inválida.");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Formato errado!");
                flag = false;
            }
        }
    }

    private static void getTasks() {
        boolean flag = false;
        while (!flag) {
            try {
                System.out.println("Dia e hora pretendida para verificação em formato: dd-MM-yyyy HH:mm");
                LocalDateTime dataHoraPretendida = LocalDateTime.parse(read.nextLine(), DateTimeFormatter.ofPattern("d-MM-yyyy H:mm"));

                if (controller.checkDate(dataHoraPretendida)) {
                    System.out.println("Fora do plano de rega!");
                } else {
                    controller.mostrarTodasAsTasks();
                    System.out.println();
                    controller.mostrarParcelasARegar(dataHoraPretendida);
                }
                flag = true;
            } catch (DateTimeParseException e) {
                System.out.println("Formato errado!");
            }
        }
    }

    private static boolean checkIfFileExists(String filePath) {
        File check = new File(filePath);
        return check.exists() && !check.isDirectory();
    }
}