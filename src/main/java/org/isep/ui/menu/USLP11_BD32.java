package org.isep.ui.menu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class USLP11_BD32 {

    public static void uslp11_bd32Start(int sectorId, String dataRealizacao, String hora, int duracaoRega, int receitaId) {

        // USBD_32
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
            CallableStatement callableStatement = db_connection.prepareCall("{call registar_operacao_rega(?, ?, ?, ?, ?) }");

            // Assign values to params
            callableStatement.setInt(1, sectorId);
            callableStatement.setDate(2, Date.valueOf(dataRealizacao));
            callableStatement.setTimestamp(3, Timestamp.valueOf(hora));
            callableStatement.setInt(4, duracaoRega);
            callableStatement.setInt(5, receitaId);

            // call Stored procedure
            callableStatement.execute();

            System.out.println("Operação de rega registada com sucesso.");
            db_connection.close();
            System.out.println();
            App.dataBaseMenu();

        } catch (SQLException | FileNotFoundException e) {
            System.out.println("ERRO: Não foi possivel registar operação de rega.");
            System.out.println();
            App.dataBaseMenu();
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
