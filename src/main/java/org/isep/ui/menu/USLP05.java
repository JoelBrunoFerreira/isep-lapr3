package org.isep.ui.menu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class USLP05 {

    public static void uslp05Start() {

        // Call USBD_12
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

            // Set the parameters
            String paramOne = "Some String";
            int paramTwo = 12345;
            double paramThree = 3.14;

            // Get a connection
            Connection db_connection = DriverManager.getConnection(theURL, theUser, thePassword);

            // Prepare the stored procedure call
            CallableStatement callableStatement = db_connection.prepareCall("{call registrar_operacao_monda(?, ?, ?)}");

            // Assign values to params
            callableStatement.setString(1, paramOne); // it will replace the first '?'
            callableStatement.setInt(2, paramTwo); // it will replace the second '?'
            callableStatement.setDouble(3, paramThree); // it will replace the three '?'

            // call Stored procedure
            callableStatement.execute();

            System.out.println("Registo de operação de monda efectuado com sucesso.");
            db_connection.close();

        } catch (SQLException | FileNotFoundException e) {
            System.out.println("Something went wrong :(");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
