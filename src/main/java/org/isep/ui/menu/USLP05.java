package org.isep.ui.menu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class USLP05 {

    public static void uslp05Start(String nomeParcela, String especieVegetal, String variedadePlanta, String dataRealizacao, double area) {

        // Call USBD_12
        try{
            // Load the credentials file
            Properties properties = new Properties();
            properties.load(new FileInputStream("db_credentials.properties"));

            // Read the properties
            String theUser = properties.getProperty("username");
            String thePassword = properties.getProperty("password");
            String theURL = properties.getProperty("dbURL_localhost");

            System.out.println("Connecting to database...");
            System.out.println("Database URL: " + theURL);
            System.out.println("User: " + theUser);
            System.out.println("=========================================================");
            System.out.println();

            // Get a connection
            Connection db_connection = DriverManager.getConnection(theURL, theUser, thePassword);

            // Prepare the stored procedure call
            CallableStatement callableStatement = db_connection.prepareCall("{call proc_USBD12(?, ?, ?, ?, ?) }");

            // Assign values to params
            callableStatement.setString(1, nomeParcela); // it will replace the first '?'
            callableStatement.setString(2, especieVegetal); // it will replace the second '?'
            callableStatement.setString(3, variedadePlanta); // it will replace the third '?'
            callableStatement.setDate(4, Date.valueOf(dataRealizacao)); // it will replace the fourth '?'
            callableStatement.setDouble(5, area); // it will replace the fifth '?'

            // call Stored procedure
            callableStatement.execute();

            // Retrieving the result (if the function returns a value)
            //int result = callableStatement.getInt(1);

            // Using the result obtained from the function call
            //System.out.println("Result: " + result);
            System.out.println("Registo de operação de monda efectuado com sucesso.");
            db_connection.close();
            System.out.println();
            App.dataBaseMenu();

        } catch (SQLException | FileNotFoundException e) {
            System.out.println("ERRO: Não é permitido realizar a operação de monda cuja área seja superior à do cultivo em questão. Para executar esta operação tem que existir um cultivo.");
            System.out.println();
            App.dataBaseMenu();
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
