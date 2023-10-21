package org.isep;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Projecto Integrador - 3");

        Controlador c = new Controlador("C:\\Users\\vns30\\Documents\\ISEP\\LEI_2\\LAPR3\\sem3pi2023_24_g311\\rega.csv");
        c.printer();
    }
}