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
        System.out.println("0 --> Encerrar a aplicação");
        System.out.println("=============================================");

        int option = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.println("Selecione uma opção: ");

            try {
                option = read.nextInt();

                if (option >= 0 && option <= 4) {
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
                USLP03.uslp03Start();
                break;
            case 2:
                System.out.println("Case 2 --> not implemented yet");
                break;
            case 3:
                System.out.println("Case 3 --> not implemented yet");
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
        System.out.print("\t Joel Bruno Teixeira Ferreira - 1191843@isep.ipp.pt \n");
        System.out.print("\t Jorge Miguel Mendanha Pereira Cruz - 1221715@isep.ipp.pt \n");
        System.out.print("\t Fábio André Cardoso Borges - 1100719@isep.ipp.pt \n");
        System.out.print("\t Victor Salgado - 1221722@isep.ipp.pt \n");
        System.out.print("\t Ana Rita Andrade - 1180708@isep.ipp.pt \n");
        System.out.println("\n");

    }
}
