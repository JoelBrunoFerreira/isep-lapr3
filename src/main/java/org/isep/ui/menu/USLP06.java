package org.isep.ui.menu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class USLP06 {

    public static void uslp06Start(String nomeParcela, String dataRealizacao, float quantidadeColhida, String nomeProduto) {

        // Call USBD_13
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

            // Set the parameters
            String paramOne = "Some String";
            int paramTwo = 12345;
            double paramThree = 3.14;

            // Get a connection
            Connection db_connection = DriverManager.getConnection(theURL, theUser, thePassword);

            // Prepare the stored procedure call
            CallableStatement callableStatement = db_connection.prepareCall("{call registar_operacao_colheita(?, ?, ?, ?)}");

            // Assign values to params
            callableStatement.setString(1, nomeParcela); // it will replace the first '?'
            callableStatement.setDate(2, Date.valueOf(dataRealizacao)); // it will replace the second '?'
            callableStatement.setFloat(3, quantidadeColhida); // it will replace the third '?'
            callableStatement.setString(4, nomeProduto); // it will replace the fourth '?'

            // call Stored procedure
            callableStatement.execute();

            db_connection.close();

            System.out.println("Registo de operação de colheita efectuado com sucesso.");
            db_connection.close();
            System.out.println();
            App.dataBaseMenu();

        } catch (SQLException | FileNotFoundException e) {
            System.out.println("ERRO: Não é possivel colher especies que não existem na parcela. Não são permitidas operações no futuro.");
            System.out.println();
            App.dataBaseMenu();
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
