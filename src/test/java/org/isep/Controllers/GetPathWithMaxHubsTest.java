package org.isep.Controllers;

import org.isep.Utilities.graph.Vertex;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GetPathWithMaxHubsTest {

    static final String distancias_big = "distancias_big.csv";

    static final String distancias_small = "distancias_small.csv";

    static final String locais_big = "locais_big.csv";

    static final String locais_small = "locais_small.csv";

    GetPathWithMaxHubs getPathWithMaxHubs = new GetPathWithMaxHubs(locais_small, distancias_small);

    @Test

    void testGetPathWithMaxHubs(){

        LocalTime initialTime = LocalTime.of(8, 0);

        LocalTime finalTime = LocalTime.of(18, 0);

        Vertex origin = null;

        ArrayList<Vertex> vertices = getPathWithMaxHubs.getMatrixGraph().vertices();

        for(Vertex local: vertices){

            if(local.getName().equals("CT1")){

                origin = local;

            }

        }

        double autonomy = 200.0;

        double averageSpeed = 50.0;

        double unloadingTime = 0.5;

        double chargingTime = 1.5;

        int numberOfHubs = 5;

        LinkedList<Vertex> path = getPathWithMaxHubs.getPathWithMaxHubs(initialTime, finalTime, origin, autonomy, averageSpeed, unloadingTime, chargingTime, numberOfHubs);

        int hubsPassed = 0;

        for (Vertex local: path){

            System.out.println(local.getName());

            if(local.getIsHub()){

                hubsPassed++;

            }

        }

        System.out.println("número de vértices: " + path.size());

        System.out.println("número de hubs: " + hubsPassed);

        System.out.print("vértices que representam hubs: ");

        for (Vertex local: path){

            if(local.getIsHub() == true){

                System.out.print(local.getName() + " ");

            }

        }

        assertNotNull(path);

        assertFalse(path.isEmpty());

    }

    @Test

    void calculateUnloadingTime() {

        Vertex v1 = new Vertex("C10");

        Vertex v2 = new Vertex("C11");

        Vertex v3 = new Vertex("C12");

        List<Vertex> hubs = new ArrayList<>();

        hubs.add(v1);

        hubs.add(v2);

        hubs.add(v3);

        LocalTime unloadingTime = LocalTime.of(0,30,0);

        LocalTime result = getPathWithMaxHubs.calculateUnloadingTime(hubs, unloadingTime);

        assertNotNull(result);

        assertEquals(LocalTime.of(1, 30, 0), result);

    }

    @Test

    void calculateChargingTime() {

        Vertex v1 = new Vertex("C10");

        Vertex v2 = new Vertex("C11");

        Vertex v3 = new Vertex("C12");

        List<Vertex> hubs = new ArrayList<>();

        hubs.add(v1);

        hubs.add(v2);

        hubs.add(v3);

        LocalTime unloadingTime = LocalTime.of(0,30,0);

        LocalTime result = getPathWithMaxHubs.calculateChargingTime(hubs, unloadingTime);

        assertNotNull(result);

        assertEquals(LocalTime.of(1, 30, 0), result);

    }

}