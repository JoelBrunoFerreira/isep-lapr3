package org.isep.ui.menu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class USLP09_BD30 {

    public static void uslp09_bd30Start(int operacaoId) {

        // USBD_30
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
            CallableStatement callableStatement = db_connection.prepareCall("{call anular_operacao(?) }");

            // Assign values to params
            callableStatement.setInt(1, operacaoId); // it will replace the first '?'

            // call Stored procedure
            callableStatement.execute();

            System.out.println("Operação anulada");
            db_connection.close();
            System.out.println();
            App.dataBaseMenu();

        } catch (SQLException | FileNotFoundException e) {
            System.out.println("ERRO: Não foi possivel anular operaÇão.");
            System.out.println();
            App.dataBaseMenu();
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
