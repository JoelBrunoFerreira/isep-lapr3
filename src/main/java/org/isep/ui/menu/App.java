package org.isep.ui.menu;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    // USLPO4 Inputs
    public static ArrayList<String> uslp04DataS = new ArrayList<>();
    public static ArrayList<Double> uslp04DataD = new ArrayList<>();

    // USLPO5 Inputs
    public static ArrayList<String> uslp05DataS = new ArrayList<>();
    public static ArrayList<Double> uslp05DataD = new ArrayList<>();

    // USLPO6 Inputs
    public static ArrayList<String> uslp06DataS = new ArrayList<>();
    public static ArrayList<Float> uslp06DataF = new ArrayList<>();

    // USLPO7 Inputs
    public static ArrayList<String> uslp07DataS = new ArrayList<>();
    public static ArrayList<Double> uslp07DataD = new ArrayList<>();

    // USLPO8 Inputs
    public static ArrayList<String> uslp08DataS = new ArrayList<>();
    public static ArrayList<Double> uslp08DataD = new ArrayList<>();

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
        System.out.println("1 --> Listar hubs por ordem decrescente de centralidade e influência. - USEI02");
        System.out.println("2 --> Devolver percurso entre os dois locais mais afastados da rede de distribuição. - USEI03");
        System.out.println("3 --> Devolver rede de ligação minima, distância entre locais e distância total da rede. - USEI04");
        System.out.println("4 --> Devolver a rede de cada cluster e o respectivo coeficiente de Silhoutte. - USEI05");
        System.out.println("5 --> Devolver todas as rotas possíveis entre um local de origem e destino. - USEI06");
        System.out.println("6 --> Encontrar para um produtor que parte de um local origem o percurso de entrega que maximiza o " +
                "número de hubs pelo qual passa - USEI07");
        System.out.println("7 --> Devolver circuito, distancia total, número de carregamentos e tempo total. - USEI08");
        System.out.println("8 --> Devolver N clusters com 1 hub por cluster. USEI09");
        System.out.println("9 --> Devolver rede máxima de transporte de cabazes e número máximo de cabazes transportados. - USEI10");
	    System.out.println("10 --> Alterar horários da rede de distribuição. - USEI11");
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
        System.out.println("6 --> Anular uma operação não realizada.");
        System.out.println("7 --> Registar uma receita de fertirrega para usar em operações de rega.");
        System.out.println("8 --> Registar uma operação de rega, incluindo a componente de fertirrega. (se aplicável)");
        System.out.println("9 --> Obter a lista das culturas com maior consumo de água - por ano civil.");
        System.out.println("10 --> Obter a lista de substâncias de factores de produção.");
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
                    System.out.println("Opção inválida. Selecione uma opção entre 0 e 10.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Opção inválida. Selecione uma opção entre 0 e 10.");
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
                // Caso de Sucesso:
                uslp05GetUserInput();
                USLP05.uslp05Start(uslp05DataS.get(0), // Campo Novo
                        uslp05DataS.get(1),            // Cenoura
                        uslp05DataS.get(2),            // Danvers Half Long
                        uslp05DataS.get(3),            // 2023-09-08
                        uslp05DataD.get(0));           // 0.5


                // Casos de Insucesso:
                /*
                // * Não existe cultivo:
                USLP05.uslp05Start(uslp05DataS.get(0), // Campo Novo
                        uslp05DataS.get(1),            // Cenoura
                        uslp05DataS.get(2),            // Danvers Half Long
                        uslp05DataS.get(3),            // 2023-10-11
                        uslp05DataD.get(0));           // 0.5

                // * Data no futuro
                USLP05.uslp05Start(uslp05DataS.get(0), // Campo Novo
                        uslp05DataS.get(1),            // Cenoura
                        uslp05DataS.get(2),            // Danvers Half Long
                        uslp05DataS.get(3),            // 2025-09-08
                        uslp05DataD.get(0));           // 0.5

                 */
                break;
            case 3:
                // Caso de Sucesso:
                uslp06GetUserInput();
                USLP06.uslp06Start(uslp06DataS.get(0), // Campo Grande
                        uslp06DataS.get(1),            // 2023-11-05
                        uslp06DataF.get(0),            // 100
                        uslp06DataS.get(2));           // Azeitona Galega

              
                // Casos de Insucesso:
                /*
                // * Produto inexistente:
                USLP06.uslp06Start(uslp06DataS.get(0), // Campo Grande
                        uslp06DataS.get(1),            // 2023-10-05
                        uslp06DataF.get(0),            // 800
                        uslp06DataS.get(2));           // Maçã Golden

                // * Data no futuro:
                USLP06.uslp06Start(uslp06DataS.get(0), // Campo Grande
                        uslp06DataS.get(1),            // 2024-11-05
                        uslp06DataF.get(0),            // 100
                        uslp06DataS.get(2));           // Azeitona Galega

                 */
                break;
            case 4:
               // Caso de Sucesso:
                uslp07GetUserInput();
                USLP07.uslp07Start(uslp07DataS.get(0), // Campo Novo
                        uslp07DataS.get(1),            // Null
                        uslp07DataS.get(2),            // Null
                        uslp07DataS.get(3),            // 2023-10-06
                        uslp07DataD.get(0),            // 4000
                        uslp07DataD.get(1),            // 1.1
                        uslp07DataS.get(4));           // Fertimax Extrume de Cavalo

                // Casos de Insucesso:
                /*
                // * Área acima do tamanho da parcela
                USLP07.uslp07Start(uslp07DataS.get(0), // Campo Novo
                        uslp07DataS.get(1),            // Null
                        uslp07DataS.get(2),            // Null
                        uslp07DataS.get(3),            // 2023-10-08
                        uslp07DataD.get(0),            // 8000
                        uslp07DataD.get(1),            // 2.1
                        uslp07DataS.get(4));           // Fertimax Extrume de Cavalo

                // * Data no futuro:
                USLP07.uslp07Start(uslp07DataS.get(0), // Campo Novo
                        uslp07DataS.get(1),            // Null
                        uslp07DataS.get(2),            // Null
                        uslp07DataS.get(3),            // 2024-10-08
                        uslp07DataD.get(0),            // 8000
                        uslp07DataD.get(1),            // 1.1
                        uslp07DataS.get(4));           // Fertimax Extrume de Cavalo

                 */
                break;
            case 5:
                //Caso de Sucesso:
                uslp08GetUserInput();
                USLP08.uslp08Start(uslp08DataS.get(0), // Campo Grande
                        uslp08DataS.get(1),            // Oliveira
                        uslp08DataS.get(2),            // Galega
                        uslp08DataS.get(3),            // 2023-11-06
                        uslp08DataD.get(0));           // 20.0

               // Casos de Incucesso:
                /*
               // * Quantidade superior à existente:
                USLP08.uslp08Start(uslp08DataS.get(0), // Campo Grande
                        uslp08DataS.get(1),            // Oliveira
                        uslp08DataS.get(2),            // Galega
                        uslp08DataS.get(3),            // 2023-11-06
                        uslp08DataD.get(0));           // 60.0

                // * Data futuro:
                USLP08.uslp08Start(uslp08DataS.get(0), // Campo Grande
                        uslp08DataS.get(1),            // Oliveira
                        uslp08DataS.get(2),            // Galega
                        uslp08DataS.get(3),            // 2024-11-06
                        uslp08DataD.get(0));           // 20.0

                 */
                break;
            case 6:
                USLP09_BD30.uslp09_bd30Start(uslp09GetUserInput());
                break;
            case 7:
                System.out.println("not implemented yet...");
                break;
            case 8:
                System.out.println("not implemented yet...");
                break;
            case 9:
                USLP12_BD33.uslp12_bd33Start(uslp12GetUserInput());
                break;
            case 10:
                System.out.println("not implemented yet...");
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

    public static void uslp05GetUserInput() {

        System.out.println("================================================");
        System.out.println("Inserir dados para inserir uma operação de monda");
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
        uslp05DataS.add(0, nomeParcela);

        String especieVegetal = "";
        while (especieVegetal.isEmpty()) {
            System.out.println("Insira o nome da espécie vegetal: ");
            especieVegetal = read.nextLine().trim();
            if (especieVegetal.isEmpty()) {
                System.out.println("Por favor, insira um valor para a espécie vegetal.");
            }
        }
        uslp05DataS.add(1, especieVegetal);

        String variedadePlanta = "";
        while (variedadePlanta.isEmpty()) {
            System.out.println("Insira a variedade da planta: ");
            variedadePlanta = read.nextLine().trim();
            if (variedadePlanta.isEmpty()) {
                System.out.println("Por favor, insira um valor para a variedade da planta.");
            }
        }
        uslp05DataS.add(2, variedadePlanta);

        String dataRealizacao = "";
        while (dataRealizacao.isEmpty()) {
            System.out.println("Insira a data de realização: -> yyyy-mm-dd");
            dataRealizacao = read.nextLine().trim();
            if (dataRealizacao.isEmpty()) {
                System.out.println("Por favor, insira um valor para a data de realização.");
            }
        }
        uslp05DataS.add(3, dataRealizacao);

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
        uslp05DataD.add(0, areaSemeadura);
    }

    public static void uslp06GetUserInput() {

        System.out.println("===============================================");
        System.out.println("Inserir dados para inserir operação de colheita");
        System.out.println("===============================================");
        System.out.println();

        String nomeParcela = "";
        while (nomeParcela.isEmpty()) {
            System.out.println("Insira o nome da Parcela: ");
            nomeParcela = read.nextLine().trim();
            if (nomeParcela.isEmpty()) {
                System.out.println("Por favor, insira um valor para a Parcela.");
            }
        }
        uslp06DataS.add(0, nomeParcela);

        String dataRealizacao = "";
        while (dataRealizacao.isEmpty()) {
            System.out.println("Insira a data de realização: -> yyyy-mm-dd");
            dataRealizacao = read.nextLine().trim();
            if (dataRealizacao.isEmpty()) {
                System.out.println("Por favor, insira um valor para a data de realização.");
            }
        }
        uslp06DataS.add(1, dataRealizacao);

        float quantidadeColhida;
        do {
            System.out.println("Insira a quantidade: ");
            while (!read.hasNextDouble()) {
                System.out.println("Por favor, insira um número válido para a quantidade.");
                read.next(); // To clear the invalid input
            }
            quantidadeColhida = read.nextFloat();
        } while (quantidadeColhida < 0);
        uslp06DataF.add(0, quantidadeColhida);

        String nomeProduto = "";
        while (nomeProduto.isEmpty()) {
            System.out.println("Insira o nome do produto: ");
            nomeProduto = read.nextLine().trim();
            if (nomeProduto.isEmpty()) {
                System.out.println("Por favor, insira um valor para o nome do produto.");
            }
        }
        uslp06DataS.add(2, nomeProduto);
    }

    public static void uslp07GetUserInput() {

        System.out.println("======================================================================");
        System.out.println("Inserir dados para inserir operação de aplicação de factor de produção");
        System.out.println("======================================================================");
        System.out.println();

        String nomeParcela = "";
        while (nomeParcela.isEmpty()) {
            System.out.println("Insira o nome da Parcela: ");
            nomeParcela = read.nextLine().trim();
            if (nomeParcela.isEmpty()) {
                System.out.println("Por favor, insira um valor para a Parcela.");
            }
        }
        uslp07DataS.add(0, nomeParcela);

        String especieVegetal = "";
        while (especieVegetal.isEmpty()) {
            System.out.println("Insira o nome da espécie vegetal: ");
            especieVegetal = read.nextLine().trim();
            if (especieVegetal.isEmpty()) {
                System.out.println("Por favor, insira um valor para a espécie vegetal.");
            }
        }
        uslp07DataS.add(1, especieVegetal);

        String variedadePlanta = "";
        while (variedadePlanta.isEmpty()) {
            System.out.println("Insira a variedade da planta: ");
            variedadePlanta = read.nextLine().trim();
            if (variedadePlanta.isEmpty()) {
                System.out.println("Por favor, insira um valor para a variedade da planta.");
            }
        }
        uslp07DataS.add(2, variedadePlanta);

        String dataRealizacao = "";
        while (dataRealizacao.isEmpty()) {
            System.out.println("Insira a data de realização: -> yyyy-mm-dd");
            dataRealizacao = read.nextLine().trim();
            if (dataRealizacao.isEmpty()) {
                System.out.println("Por favor, insira um valor para a data de realização.");
            }
        }
        uslp07DataS.add(3, dataRealizacao);

        // Input validation for quantity (non-negative)
        double quantidadeFactorProducao;
        do {
            System.out.println("Insira a quantidade: ");
            while (!read.hasNextDouble()) {
                System.out.println("Por favor, insira um número válido para a quantidade.");
                read.next(); // To clear the invalid input
            }
            quantidadeFactorProducao = read.nextDouble();
        } while (quantidadeFactorProducao < 0);
        uslp07DataD.add(0, quantidadeFactorProducao);

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
        uslp07DataD.add(1, areaSemeadura);

        String nomeFactorProducao = "";
        while (nomeFactorProducao.isEmpty()) {
            System.out.println("Insira o nome do factor produção: ");
            nomeFactorProducao = read.nextLine().trim();
            if (nomeFactorProducao.isEmpty()) {
                System.out.println("Por favor, insira um valor para o nome do factor produção.");
            }
        }
        uslp07DataS.add(4, nomeFactorProducao);
    }

    public static void uslp08GetUserInput() {

        System.out.println("===========================================");
        System.out.println("Inserir dados para inserir operação de poda");
        System.out.println("===========================================");
        System.out.println();

        String nomeParcela = "";
        while (nomeParcela.isEmpty()) {
            System.out.println("Insira o nome da Parcela: ");
            nomeParcela = read.nextLine().trim();
            if (nomeParcela.isEmpty()) {
                System.out.println("Por favor, insira um valor para a Parcela.");
            }
        }
        uslp08DataS.add(0, nomeParcela);

        String especieVegetal = "";
        while (especieVegetal.isEmpty()) {
            System.out.println("Insira o nome da espécie vegetal: ");
            especieVegetal = read.nextLine().trim();
            if (especieVegetal.isEmpty()) {
                System.out.println("Por favor, insira um valor para a espécie vegetal.");
            }
        }
        uslp08DataS.add(1, especieVegetal);

        String variedadePlanta = "";
        while (variedadePlanta.isEmpty()) {
            System.out.println("Insira a variedade da planta: ");
            variedadePlanta = read.nextLine().trim();
            if (variedadePlanta.isEmpty()) {
                System.out.println("Por favor, insira um valor para a variedade da planta.");
            }
        }
        uslp08DataS.add(2, variedadePlanta);

        String dataRealizacao = "";
        while (dataRealizacao.isEmpty()) {
            System.out.println("Insira a data de realização: -> yyyy-mm-dd");
            dataRealizacao = read.nextLine().trim();
            if (dataRealizacao.isEmpty()) {
                System.out.println("Por favor, insira um valor para a data de realização.");
            }
        }
        uslp08DataS.add(3, dataRealizacao);

        // Input validation for quantity (non-negative)
        double quantidadePoda;
        do {
            System.out.println("Insira a quantidade: ");
            while (!read.hasNextDouble()) {
                System.out.println("Por favor, insira um número válido para a quantidade.");
                read.next(); // To clear the invalid input
            }
            quantidadePoda = read.nextDouble();
        } while (quantidadePoda < 0);
        uslp04DataD.add(0, quantidadePoda);
    }

    public static int uslp09GetUserInput() { // --> USBD30

        System.out.println("=================================");
        System.out.println("Anular uma operação não realizada");
        System.out.println("=================================");
        System.out.println();

        // Input validation for quantity (non-negative)
        int operacaoId;
        do {
            System.out.println("Insira o ID da operação: ");
            while (!read.hasNextInt()) {
                System.out.println("Por favor, insira um ID válido para a operação.");
                read.next(); // To clear the invalid input
            }
            operacaoId = read.nextInt();
        } while (operacaoId < 0);
        return operacaoId;
    }

    public static void uslp10GetUserInput() { // --> USBD31

    }

    public static void uslp11GetUserInput() { // --> USBD32

    }

    public static String uslp12GetUserInput() { // --> USBD33

        System.out.println("====================================================================");
        System.out.println("Obter a lista das culturas com maior consumo de água - por ano civil");
        System.out.println("====================================================================");
        System.out.println();

        String anoCivil = "";
        while (anoCivil.isEmpty()) {
            System.out.println("Insira o ano civil: -> yyyy-mm-dd");
            anoCivil = read.nextLine().trim();
            if (anoCivil.isEmpty()) {
                System.out.println("Por favor, insira um valor para o ano civil.");
            }
        }
        return anoCivil;
    }

    public static void uslp13GetUserInput() { // --> USBD34

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
