package org.isep.ui.menu;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    public static ArrayList<String> uslp04DataS = new ArrayList<>();
    public static ArrayList<Double> uslp04DataD = new ArrayList<>();

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
        System.out.print("Equipa de desenvolvimento:\n");
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
        System.out.println("5 --> Devolver todas as rotas possíveis entre um local de origem e destino.");
        System.out.println("6 --> ........");
        System.out.println("7 --> ........");
        System.out.println("8 --> Devolver N clusters com 1 hub por cluster");
        System.out.println("9 --> ........");
	    System.out.println("10 --> Alterar horários da rede de distribuição.");
	    System.out.println("=============================================");
        System.out.println("0 --> Voltar ao menu inicial                |");
        System.out.println("=============================================");

        int option = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Selecione uma opção: ");

            try {
                option = read.nextInt();

                if (option >= 0 && option <= 10) {
                    validInput = true;
                } else {
                    System.out.println("Opção inválida. Selecione uma opção entre 1 e 10.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Opção inválida. Selecione uma opção entre 1 e 10.");
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
            case 5:
                USEI06.usei06Start();
                break;
            case 6:
                USEI07.usei07Start();
                break;
            case 7:
                USEI08.usei08Start();
                break;
            case 8:
                USEI09.usei09Start();
                break;
            case 9:
                USEI10.usei10Start();
                break;
            case 10:
                USEI11.usei11Start();
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
        read.nextLine();

        switch (option) {
            case 1:
                // Caso de Sucesso:
                uslp04GetUserInput();
                USLP04.uslp04Start(uslp04DataS.get(0), // Horta Nova
                        uslp04DataS.get(1),            // Nabo Greleiro
                        uslp04DataS.get(2),            // Senhora Conceição
                        uslp04DataS.get(3),            // 2023-09-20
                        uslp04DataD.get(0),            // 0.9
                        uslp04DataD.get(1));           // 0.3

               // Casos de Insucesso:
                /*
               // * Area maior que area da parcela:
                USLP04.uslp04Start(uslp04DataS.get(0), // Campo Novo
                        uslp04DataS.get(1),            // Nabo Greleiro
                        uslp04DataS.get(2),            // Senhora Conceição
                        uslp04DataS.get(3),            // 2023-09-19
                        uslp04DataD.get(0),            // 1.8
                        uslp04DataD.get(1));           // 0.75

                // * Data no futuro
                USLP04.uslp04Start(uslp04DataS.get(0), // Campo Novo
                        uslp04DataS.get(1),            // Nabo Greleiro
                        uslp04DataS.get(2),            // Senhora Conceição
                        uslp04DataS.get(3),            // 2023-09-19
                        uslp04DataD.get(0),            // 1
                        uslp04DataD.get(1));           // 0.3

                 */
                break;
            case 2:
                //Sucesso:
                USLP05.uslp05Start("Campo Novo", "Cenoura", "Danvers Half Long", "2023-09-08", 0.5);

//                Insucesso:
//                 -Não existe cultivo:
//                USLP05.uslp05Start("Campo Novo", "Cenoura", "Danvers Half Long", "2023-10-11", 0.5);

//                 -Data no futuro:
//                USLP05.uslp05Start("Campo Novo", "Cenoura", "Danvers Half Long", "2025-09-08", 0.5);

                break;
            case 3:
                //Sucesso:
                USLP06.uslp06Start("Campo Grande", "2023-11-05",100,"Azeitona Galega");

//                Insucesso:
//                 -Produto inexistente:
//                 USLP06.uslp06Start("Campo Grande", "2023-10-05",800,"Maçã Golden");

//                 -Data no futuro:
//                 USLP06.uslp06Start("Campo Grande", "2024-11-05",100,"Azeitona Galega");
                break;
            case 4:
//                Sucesso:
                USLP07.uslp07Start("Campo Novo",null ,null,"2023-10-06" ,4000,1.1,"Fertimax Extrume de Cavalo");

                //Insucesso:
//                -Área acima do tamanho da parcela
//                USLP07.uslp07Start("Campo Novo", null, null,"2023-10-08" ,8000,2.1,"Fertimax Extrume de Cavalo");

//                -Data no futuro:
//                USLP07.uslp07Start("Campo Novo", null, null,"2024-10-08" ,8000,1.1,"Fertimax Extrume de Cavalo");

                break;
            case 5:
                //Sucesso:
                USLP08.uslp08Start("Campo Grande", "Oliveira", "Galega", "2023-11-06", 20.0);

//                Incucesso:
//                 -Quantidade superior à existente:
//                  USLP08.uslp08Start("Campo Grande", "Oliveira", "Galega", "2023-11-06", 60.0);

//                 -Data futuro:
//                  USLP08.uslp08Start("Campo Grande", "Oliveira", "Galega", "2024-11-06", 20.0);
                break;
            case 0:
                runApp();
                break;
        }

    }

    public static void uslp04GetUserInput() {

        System.out.println("================================================");
        System.out.println("Inserir dados para inserir operação de semeadura");
        System.out.println("================================================");
        System.out.println();

        String nomeParcela = "";
        while (nomeParcela.isEmpty()) {
            System.out.println("Insira o nome da Parcela: ");
            nomeParcela = read.nextLine().trim();
            if (nomeParcela.isEmpty()) {
                System.out.println("Por favor, insira um valor para a Parcela.");
            }
        }
        uslp04DataS.add(0, nomeParcela);

        String especieVegetal = "";
        while (especieVegetal.isEmpty()) {
            System.out.println("Insira o nome da espécie vegetal: ");
            especieVegetal = read.nextLine().trim();
            if (especieVegetal.isEmpty()) {
                System.out.println("Por favor, insira um valor para a espécie vegetal.");
            }
        }
        uslp04DataS.add(1, especieVegetal);

        String variedadePlanta = "";
        while (variedadePlanta.isEmpty()) {
            System.out.println("Insira a variedade da planta: ");
            variedadePlanta = read.nextLine().trim();
            if (variedadePlanta.isEmpty()) {
                System.out.println("Por favor, insira um valor para a variedade da planta.");
            }
        }
        uslp04DataS.add(2, variedadePlanta);

        String dataRealizacao = "";
        while (dataRealizacao.isEmpty()) {
            System.out.println("Insira a data de realização: -> yyyy-mm-dd");
            dataRealizacao = read.nextLine().trim();
            if (dataRealizacao.isEmpty()) {
                System.out.println("Por favor, insira um valor para a data de realização.");
            }
        }
        uslp04DataS.add(3, dataRealizacao);

        // Input validation for quantity (non-negative)
        double quantidade;
        do {
            System.out.println("Insira a quantidade: ");
            while (!read.hasNextDouble()) {
                System.out.println("Por favor, insira um número válido para a quantidade.");
                read.next(); // To clear the invalid input
            }
            quantidade = read.nextDouble();
        } while (quantidade < 0);
        uslp04DataD.add(0, quantidade);

        // Input validation for seedbed area (non-negative)
        double areaSemeadura;
        do {
            System.out.println("Insira a área da semeadura: ");
            while (!read.hasNextDouble()) {
                System.out.println("Por favor, insira um número válido para a área de semeadura.");
                read.next(); // To clear the invalid input
            }
            areaSemeadura = read.nextDouble();
        } while (areaSemeadura < 0);
        uslp04DataD.add(1, areaSemeadura);
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
