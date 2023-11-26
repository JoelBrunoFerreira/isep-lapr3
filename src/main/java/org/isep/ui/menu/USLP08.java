package org.isep.ui.menu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class USLP08 {

    public static void uslp08Start(String paramOne, String paramTwo, String paramThree, String paramFour, int paramFive) {

        // Call USBD_15
        try{
            // Load the credentials file
            Properties properties = new Properties();
            properties.load(new FileInputStream("db_credentials.properties"));

            // Read the properties
            String theUser = properties.getProperty("username");
            String thePassword = properties.getProperty("password");
            String theURL = properties.getProperty("dbURL_remote");

            System.out.println("Connecting to database...");
            System.out.println("Database URL: " + theURL);
            System.out.println("User: " + theUser);
            System.out.println("=========================================================");
            System.out.println();

            // Get a connection
            Connection db_connection = DriverManager.getConnection(theURL, theUser, thePassword);

            // Prepare the stored procedure call
            CallableStatement callableStatement = db_connection.prepareCall("{call registar_operacao_poda(?, ?, ?, ?, ?)}");

            // Assign values to params
            callableStatement.setString(1, paramOne); // it will replace the first '?'
            callableStatement.setString(2, paramTwo); // it will replace the second '?'
            callableStatement.setString(3, paramThree); // it will replace the third '?'
            callableStatement.setDate(4, Date.valueOf(paramFour)); // it will replace the fourth '?'
            callableStatement.setInt(5, paramFive); // it will replace the fifth '?''

            // call Stored procedure
            callableStatement.execute();

            db_connection.close();

            // Retrieving the result (if the function returns a value)
            //int result = callableStatement.getInt(1);

            // Using the result obtained from the function call
            //System.out.println("Result: " + result);
            System.out.println("Registo de operação de poda efectuado com sucesso.");
        } catch (SQLException | FileNotFoundException e) {
            System.out.println("Something went wrong :(");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
