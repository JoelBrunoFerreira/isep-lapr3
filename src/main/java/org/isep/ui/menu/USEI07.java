package org.isep.ui.menu;

import org.isep.Controllers.GetPathWithMaxHubs;
import org.isep.Utilities.graph.Vertex;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class USEI07 {
    static final String distancias_big = "distancias_big.csv";

    static final String distancias_small = "distancias_small.csv";

    static final String locais_big = "locais_big.csv";

    static final String locais_small = "locais_small.csv";

    static GetPathWithMaxHubs getPathWithMaxHubs = new GetPathWithMaxHubs(locais_small, distancias_small);
    static Scanner sc = new Scanner(System.in);

    public static void usei07Start() {
        int numberOfHubs = 8;
        double unloadingTime = 0.5;
        double averageSpeed = 50.0;
        boolean valid = false;

        System.out.println("Esta funcionalidade permite encontrar um percurso de entrega que maximize o número de hubs pelo qual passa. A rede" +
                "é composta por 17 localidades, " + numberOfHubs +  " destas representando um hub de distribuição. Foi estimado um período de meia hora " +
                "para efetuar a respetiva descarga nos hubs da rede. De modo a respeitar as regras do código da estrada, foi considerada uma velocidade" +
                "média de " + averageSpeed + " km/h.");

        System.out.println();
        double autonomy = 200.0;
        double chargingTime = 1.5;

        System.out.println("Dados do seu veículo: ");
        System.out.println("Autonomia: " + autonomy + " km");
        System.out.println("Tempo de carga: " + chargingTime + " h");



        int num = 0;
        while(!valid){
            System.out.print("Insira a localidade de origem: CT");
            try {
                num = sc.nextInt();
                if(num > 0 && num <= 17){
                    valid = true;
                    sc.nextLine();
                }else{
                    System.out.println("Insira um número entre 1 e 17.");
                }

            }catch(InputMismatchException e){
                System.out.println("Por favor, insira um número válido.");
                sc.nextLine();
            }
        }
        String source = "CT" + num;
        valid = false;


        LocalTime initialTime = null;

        while(!valid) {
            System.out.print("Insira a hora a que vai iniciar o percurso (formato hh:mm): ");
            try {
                String userInput = sc.nextLine();
                initialTime = LocalTime.parse(userInput);
                valid = true;
            } catch (DateTimeParseException e) {
                System.out.println("Formato de hora inválido. Utilize o formato hh:mm.");
            }
        }
        valid = false;


        double timeSpent = 0;
        while(!valid){
            System.out.print("Insira o número de horas para efetuar o percurso (não deve ultrapassar as 8 horas): ");
            try {
                timeSpent = sc.nextDouble();
                if(timeSpent > 0 && timeSpent <= 8){
                    valid = true;
                    sc.nextLine();
                }else{
                    System.out.println("Insira um número entre 1 e 8.");
                }

            }catch(InputMismatchException e){
                System.out.println("Por favor, insira um número válido.");
                sc.nextLine();
            }
        }


        long timeSpentInSeconds = (long) (timeSpent * 3600);
        long currentTimeInSeconds = initialTime.toSecondOfDay();
        long finalTimeInSeconds = timeSpentInSeconds + currentTimeInSeconds;

        long hours = finalTimeInSeconds / 3600;
        long minutes = (finalTimeInSeconds % 3600) / 60;
        long seconds = finalTimeInSeconds % 60;

        LocalTime finalTime = LocalTime.of((int) hours, (int) minutes, (int) seconds);



        // prints
        Vertex origin = null;
        ArrayList<Vertex> vertices = getPathWithMaxHubs.getMatrixGraph().vertices();
        for (Vertex local : vertices) {
            if (local.getName().equals(source)) {
                origin = local;
            }
        }
        LinkedList<Vertex> path = getPathWithMaxHubs.getPathWithMaxHubs(initialTime, finalTime, origin, autonomy, averageSpeed, unloadingTime, chargingTime, numberOfHubs);


        System.out.println();
        System.out.printf("%-15s | %-15s | %-15s\n", "Localidade", "Hora de chegada", "Hora de partida");
        for (Vertex local : path) {
            System.out.printf("%-15s | %-15s | %-15s\n",
                    local.getName(),
                    local.getArrivalTime(),
                    local.getDepartureTime());
        }

        System.out.println();
        System.out.println("RELATÓRIO DO PERCURSO:");
        System.out.print("Localidades que representam hubs: ");
        ArrayList<Vertex> visited = new ArrayList<>();
        for (Vertex local : path) {
            if (local.getIsHub() == true && !visited.contains(local)) {
                System.out.print(local.getName() + " ");
                visited.add(local);
            }
        }
        System.out.println();
        System.out.println("Número de vértices no percurso: " + path.size());
        System.out.println("Número de hubs no percurso: " + visited.size());
        double totalDistance = getPathWithMaxHubs.calculateTotalDistance(path);
        System.out.println("Distância total: "+ Math.round(totalDistance / 1000) + " km");
        int hubCount = 0;
        int chargeCount = 0;
        for (Vertex local: path){
            if(local.getIsHub()){
                hubCount++;
            }
            if(local.getIsUsedAsChargingPoint()){
                chargeCount++;
            }
        }
        System.out.println("Tempo gasto em descarga: " + Math.round(hubCount * unloadingTime * 10) / 10 + " h");
        System.out.println("Tempo gasto em carregamento da viatura: " + Math.round(chargeCount * chargingTime * 10) / 10 + " h");

    }
}
