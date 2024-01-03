package org.isep.ui.menu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class USLP12_BD33 {

    public static void uslp12_bd33Start(String anoCivil) {

        // Call USBD_33
        try{
            // Load the credentials file
            Properties properties = new Properties();
            properties.load(new FileInputStream("db_credentials.properties"));

            // Read the properties
            String theUser = properties.getProperty("username");
            String thePassword = properties.getProperty("password");
            String theURL = properties.getProperty("dbURL_localhost");

            System.out.println();
            System.out.println("==================================================");
            System.out.println("Connecting to database...");
            System.out.println("Database URL: " + theURL);
            System.out.println("User: " + theUser);
            System.out.println("==================================================");
            System.out.println();

            // Get a connection
            Connection db_connection = DriverManager.getConnection(theURL, theUser, thePassword);

            // Prepare the stored procedure call
            CallableStatement callableStatement = db_connection.prepareCall("{call fncCultivosMaiorRega(?) }");

            // Assign values to params
            callableStatement.setDate(1, Date.valueOf(anoCivil)); // it will replace the first '?'

            // call Stored procedure
            callableStatement.execute();

            // Retrieving the result (if the function returns a value)
//            String result = callableStatement.getString(6);

            // Using the result obtained from the function call
//            System.out.println("Result: " + result);
            System.out.println("Lista das culturas com maior consumo de água do ano: " + anoCivil);
            db_connection.close();
            System.out.println();
            App.dataBaseMenu();

        } catch (SQLException | FileNotFoundException e) {
            System.out.println("ERRO: Não foi possivel obter lista.");
            System.out.println();
            App.dataBaseMenu();
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}