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
        private static String filePath = "plano.csv";

        public static void main(String[] args) {
            boolean flag = true;
            controller = new Controller(filePath, startDate);
            while (flag) {
                System.out.println("1 - Carregar novo plano de rega");
                System.out.println("2 - Setores a regar");
                System.out.println("3 - Verificar Caderno de Campo");
                System.out.println("4 - Sair");
                String resposta = read.nextLine();

                switch (resposta) {
                    case "1":
                        getPlanoRega();
                        controller = new Controller(filePath, startDate);
                        break;
                    case "2":
                        getTasks();
                        break;
                    case "3":
                        controller.mostrarCadernoDeCampo();
                        break;
                    case "4":
                        flag = false;
                        break;
                    default:
                        System.out.println("Opção inválida.\n");
                }
            }
        }

        private static void getPlanoRega() {
            boolean flag = false;
            while (!flag) {
                try {
                    System.out.println("Nome do ficheiro:");
                    filePath = read.nextLine();
                    if (verificaSeFicheiroExiste(filePath)) {
                        flag = true;
                        System.out.println("Data para início do plano: (formato: dd-mm-yyyy)");
                        startDate = LocalDate.parse(read.nextLine(), DateTimeFormatter.ofPattern("d-MM-yyyy"));
                    } else {
                        System.out.println("Ficheiro não encontrado.\n");
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("Formato errado!\n");
                    flag = false;
                }
            }
        }

        private static void getTasks() {
            boolean flag = false;
            while (!flag) {
                try {
                    System.out.println("Dia e hora pretendida para verificação? (formato: dd-MM-yyyy HH:mm)");
                    LocalDateTime dataHoraPretendida = LocalDateTime.parse(read.nextLine(), DateTimeFormatter.ofPattern("d-MM-yyyy H:mm"));
                    controller.setDataHoraPretendida(dataHoraPretendida);
                    if (controller.verificarData()) {
                        System.out.println("Fora do plano de rega!\n");
                    } else {
                        controller.mostrarTodasAsTasks();
                        System.out.println();
                        controller.mostrarParcelasARegar();
                    }
                    flag = true;
                } catch (DateTimeParseException e) {
                    System.out.println("Formato errado!\n");
                }
            }
        }

        private static boolean verificaSeFicheiroExiste(String filePath) {
            File check = new File(filePath);
            return check.exists() && !check.isDirectory();
        }

    }