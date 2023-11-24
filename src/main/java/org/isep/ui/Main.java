package org.isep.ui;

import org.isep.Controllers.Controller;
import org.isep.Controllers.LoadData;
import org.isep.JDBC.DB_Connection;
import org.isep.JDBC.SQL_Queries;
import org.isep.Utilities.Distance.FurthestVertices;
import org.isep.Utilities.graph.*;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner read = new Scanner(System.in);
    public static Controller controller;
    private static LocalDate startDate = LocalDate.now();
    private static String filePath = "plano.csv";
    public static final List<Vertex> vertexList = new ArrayList<>();
    public static final List<EdgeEI04> EdgeEI04List = new ArrayList<>();


    public static void main(String[] args) {
        /*
        try {
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
                flag = true;
            }
        } catch (DateTimeParseException e) {
            System.out.println("Formato errado!");
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


        /*
        DB_Connection db = new DB_Connection();
        db.loadCredentialsAndQueringDB(SQL_Queries.getUSBD11());

         */

        loadGraph();

        KruskalAlgorithm algorithm = new KruskalAlgorithm();
        algorithm.runKruskal(vertexList,  EdgeEI04List);

    }


    public static void loadGraph() {

        Vertex CT1 = new Vertex("CT1");
        Vertex CT2 = new Vertex("CT2");
        Vertex CT3 = new Vertex("CT3");
        Vertex CT4 = new Vertex("CT4");
        Vertex CT5 = new Vertex("CT5");
        Vertex CT6 = new Vertex("CT6");
        Vertex CT7 = new Vertex("CT7");
        Vertex CT8 = new Vertex("CT8");
        Vertex CT9 = new Vertex("CT9");
        Vertex CT10 = new Vertex("CT10");
        Vertex CT11 = new Vertex("CT11");
        Vertex CT12 = new Vertex("CT12");
        Vertex CT13 = new Vertex("CT13");
        Vertex CT14 = new Vertex("CT14");
        Vertex CT15 = new Vertex("CT15");
        Vertex CT16 = new Vertex("CT16");
        Vertex CT17 = new Vertex("CT17");

        vertexList.add(CT1);
        vertexList.add(CT2);
        vertexList.add(CT3);
        vertexList.add(CT4);
        vertexList.add(CT5);
        vertexList.add(CT6);
        vertexList.add(CT7);
        vertexList.add(CT8);
        vertexList.add(CT9);
        vertexList.add(CT10);
        vertexList.add(CT11);
        vertexList.add(CT12);
        vertexList.add(CT13);
        vertexList.add(CT14);
        vertexList.add(CT15);
        vertexList.add(CT16);
        vertexList.add(CT17);


        EdgeEI04List.add(new EdgeEI04(56.717, CT1, CT6));

        EdgeEI04List.add(new EdgeEI04(65.574, CT2, CT7));
        EdgeEI04List.add(new EdgeEI04(125.105, CT2, CT8));
        EdgeEI04List.add(new EdgeEI04(163.996, CT2, CT11));

        EdgeEI04List.add(new EdgeEI04(157.223, CT3, CT4));

        EdgeEI04List.add(new EdgeEI04(157.223, CT4, CT3));
        EdgeEI04List.add(new EdgeEI04(162.527, CT4, CT9));

        EdgeEI04List.add(new EdgeEI04(90.186, CT5, CT9));
        EdgeEI04List.add(new EdgeEI04(100.563, CT5, CT6));
        EdgeEI04List.add(new EdgeEI04(111.134, CT5, CT17));

        EdgeEI04List.add(new EdgeEI04(67.584, CT6, CT10));

        EdgeEI04List.add(new EdgeEI04(95.957, CT7, CT14));

        EdgeEI04List.add(new EdgeEI04(207.558, CT8, CT14));

        EdgeEI04List.add(new EdgeEI04(63.448, CT10, CT13));
        EdgeEI04List.add(new EdgeEI04(67.584, CT10, CT6));
        EdgeEI04List.add(new EdgeEI04(110.848, CT10, CT1));
        EdgeEI04List.add(new EdgeEI04(125.041, CT10, CT5));
        EdgeEI04List.add(new EdgeEI04(62.655, CT11, CT5));
        EdgeEI04List.add(new EdgeEI04(121.584, CT11, CT13));
        EdgeEI04List.add(new EdgeEI04(142.470, CT11, CT10));

        EdgeEI04List.add(new EdgeEI04(50.467, CT12, CT3));
        EdgeEI04List.add(new EdgeEI04(62.877, CT12, CT1));
        EdgeEI04List.add(new EdgeEI04(70.717, CT12, CT15));

        EdgeEI04List.add(new EdgeEI04(111.686, CT13, CT7));

        EdgeEI04List.add(new EdgeEI04(89.813, CT14, CT13));
        EdgeEI04List.add(new EdgeEI04(95.957, CT14, CT7));
        EdgeEI04List.add(new EdgeEI04(114.913, CT14, CT2));
        EdgeEI04List.add(new EdgeEI04(207.558, CT14, CT8));

        EdgeEI04List.add(new EdgeEI04(43.598, CT15, CT3));

        EdgeEI04List.add(new EdgeEI04(68.957, CT16, CT3));
        EdgeEI04List.add(new EdgeEI04(79.560, CT16, CT17));
        EdgeEI04List.add(new EdgeEI04(82.996, CT16, CT12));
        EdgeEI04List.add(new EdgeEI04(103.704, CT16, CT9));
        EdgeEI04List.add(new EdgeEI04(110.133, CT16, CT4));

        EdgeEI04List.add(new EdgeEI04(62.879, CT17, CT9));
        EdgeEI04List.add(new EdgeEI04(69.282, CT17, CT1));
        EdgeEI04List.add(new EdgeEI04(73.828, CT17, CT6));
    }
}
