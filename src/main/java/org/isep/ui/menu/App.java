package org.isep.ui.menu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {

    public static Scanner read = new Scanner(System.in);
    public static void runApp() {

        System.out.println("=============================================");
        System.out.println("|           Farm Coordinator App            |");
        System.out.println("=============================================");
        System.out.println("1 --> Aceder ao Sistema de Rega");
        System.out.println("2 --> Aceder à rede de distribuição");
        System.out.println("3 --> Aceder à Base de Dados");
        System.out.println("4 --> Conheça a equipa de desemvolvimento");
        System.out.println("=============================================");
        System.out.println("0 --> Encerrar a aplicação                  |");
        System.out.println("=============================================");

        int option = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Selecione uma opção: ");

            try {
                option = read.nextInt();

                if (option >= 0 && option <= 4) {
                    validInput = true;
                } else {
                    System.out.println("Opção inválida. Selecione uma opção entre 1 e 4.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Opção inválida. Selecione uma opção entre 1 e 4.");
                read.nextLine();
            }
        }

        switch (option) {
            case 1:
                USLP03.uslp03Start();
                break;
            case 2:
                distributionNetworkMenu();
                break;
            case 3:
                dataBaseMenu();
                break;
            case 4:
                devTeam();
                break;
            case 0:
                System.exit(0);

        }
    }


    // ************************ Sub Menus **************************************
    // *************************************************************************
    public static void devTeam() {
        System.out.println("\n");
        System.out.print("Equipa de desemvolvimento:\n");
        System.out.println("----------------------------------------------------------------");
        System.out.print("\t * Joel Bruno Teixeira Ferreira - 1191843@isep.ipp.pt \n");
        System.out.print("\t * Jorge Miguel Mendanha Pereira Cruz - 1221715@isep.ipp.pt \n");
        System.out.print("\t * Fábio André Cardoso Borges - 1100719@isep.ipp.pt \n");
        System.out.print("\t * Victor Salgado - 1221722@isep.ipp.pt \n");
        System.out.print("\t * Ana Rita Andrade - 1180708@isep.ipp.pt \n");
        System.out.println("----------------------------------------------------------------");
        System.out.println("\n");
        askAgain();
    }

    public static void distributionNetworkMenu() {
        System.out.println("=============================================");
        System.out.println("|           Rede de Distribuição            |");
        System.out.println("=============================================");
        System.out.println("1 --> Listar hubs por ordem decrescente de centralidade e influência.");
        System.out.println("2 --> Devolver percurso entre os dois locais mais afastados da rede de distribuição.");
        System.out.println("3 --> Devolver rede de ligação minima, distância entre locais e distância total da rede.");
        System.out.println("4 --> Devolver a rede de cada cluster e o respectivo coeficiente de Silhoutte.");
        System.out.println("=============================================");
        System.out.println("0 --> Voltar ao menu inicial                |");
        System.out.println("=============================================");

        int option = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Selecione uma opção: ");

            try {
                option = read.nextInt();

                if (option >= 0 && option <= 4) {
                    validInput = true;
                } else {
                    System.out.println("Opção inválida. Selecione uma opção entre 1 e 4.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Opção inválida. Selecione uma opção entre 1 e 4.");
                read.nextLine();
            }
        }

        switch (option) {
            case 1:
                USEI02.usei02Start();
                break;
            case 2:
                USEI03.usei03Start();
                break;
            case 3:
                USEI04.usei04Start();
                break;
            case 4:
                USEI05.usei05Start();
                break;
            case 0:
                runApp();
                break;
        }
    }

    public static void dataBaseMenu() {
        System.out.println("=============================================");
        System.out.println("|           Acesso à Base de Dados          |");
        System.out.println("=============================================");
        System.out.println("1 --> Registar uma operação de semeadura.");
        System.out.println("2 --> Registar uma operação de monda.");
        System.out.println("3 --> Registar uma operação de colheita.");
        System.out.println("4 --> Registar uma operação de aplicação de factor de produção.");
        System.out.println("5 --> Registar uma operação de poda.");
        System.out.println("=============================================");
        System.out.println("0 --> Voltar ao menu inicial                |");
        System.out.println("=============================================");

        int option = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Selecione uma opção: ");

            try {
                option = read.nextInt();

                if (option >= 0 && option <= 5) {
                    validInput = true;
                } else {
                    System.out.println("Opção inválida. Selecione uma opção entre 1 e 5.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Opção inválida. Selecione uma opção entre 1 e 5.");
                read.nextLine();
            }
        }

        switch (option) {
            case 1:
                USLP04.uslp04Start();
                break;
            case 2:
                USLP05.uslp05Start("Campo Novo", "Cenoura", "Danvers Half Long", "2023-10-11", 0.5);
                break;
            case 3:
                USLP06.uslp06Start();
                break;
            case 4:
                USLP07.uslp07Start();
                break;
            case 5:
                USLP08.uslp08Start();
                break;
            case 0:
                runApp();
                break;
        }

    }

    public static void askAgain() {
        System.out.println("=============================================");
        System.out.println("1 --> Voltar ao menu inicial                |");
        System.out.println("0 --> Encerrar aplicação                    |");
        System.out.println("=============================================");

        int option = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Selecione uma opção: ");

            try {
                option = read.nextInt();

                if (option >= 0 && option <= 1) {
                    validInput = true;
                } else {
                    System.out.println("Opção inválida. Selecione uma opção entre 1 e 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Opção inválida. Selecione uma opção entre 1 e 2.");
                read.nextLine();
            }
        }
        switch (option) {
            case 1:
                App.runApp();
                break;
            case 0:
                System.exit(0);
                break;
        }
    }
}
