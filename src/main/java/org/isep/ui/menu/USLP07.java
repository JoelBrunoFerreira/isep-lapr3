package org.isep.ui.menu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class USLP07 {

    public static void uslp07Start(String nomeParcela, String especieVegetal, String variedadePlanta, String dataRealizacao, double quantidadeFatorProducao, double area, String nomeFatorDeProducao) {

        // Call USBD_14
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
            CallableStatement callableStatement = db_connection.prepareCall("{call registar_operacao_aplicacao_fator_producao(?, ?, ?, ?, ?,?,?)}");

            // Assign values to params
            callableStatement.setString(1, nomeParcela); // it will replace the first '?'
            callableStatement.setString(2, especieVegetal); // it will replace the second '?'
            callableStatement.setString(3, variedadePlanta); // it will replace the third '?'
            callableStatement.setDate(4, Date.valueOf(dataRealizacao)); // it will replace the fourth '?'
            callableStatement.setDouble(5, quantidadeFatorProducao); // it will replace the sixth '?'
            callableStatement.setDouble(6, area); // it will replace the seventh '?'
            callableStatement.setString(7, nomeFatorDeProducao); // it will replace the fifth '?'

            // call Stored procedure
            callableStatement.execute();

            System.out.println("Registo de operação de aplicação de fator de produção efectuado com sucesso.");
            db_connection.close();
            System.out.println();
            App.dataBaseMenu();

        } catch (SQLException | FileNotFoundException e) {
            System.out.println("ERRO: Área introduzida é superior à área da parcela. / Não existem cultivos activos associados à especie indicada. / Não são permitidas operações no futuro.");
            System.out.println();
            App.dataBaseMenu();
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
