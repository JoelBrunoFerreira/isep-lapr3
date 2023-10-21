package org.isep;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("Projecto Integrador - 3");
        // Win
        String fileWin ="C:\\Users\\vns30\\Documents\\ISEP\\LEI_2\\LAPR3\\sem3pi2023_24_g311\\rega.csv";
        // Linux
        String fileLinux ="rega.csv";

        try {
            Controlador c = new Controlador(fileLinux);
            Map<LocalTime, List<Parcela>> result = c.getInfo(fileLinux);
            c.regaAtiva(result);
            //printer
//            for (LocalTime lc : result.keySet()
//                 ) {
//                System.out.println(lc);
//                for (Parcela p : result.get(lc)
//                     ) {
//                    System.out.println(p);
//                }
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}