package org.isep.ui.menu;

import org.isep.Controllers.LoadData;
import org.isep.Utilities.graph.Vertex;

import java.util.List;
import java.util.Scanner;

public class USEI11 {

    static Scanner sc = new Scanner(System.in);

    static final String locais_small = "locais_small.csv";
    static final String esinf11 = "esinf11.csv";




   public static void usei11Start(){
       List<Vertex> locals = LoadData.readCSV2(locais_small);

       LoadData.changeSchedule(esinf11, locals);

       System.out.println();
       App.askAgain();

   }






}
