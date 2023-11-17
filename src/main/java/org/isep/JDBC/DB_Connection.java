package org.isep.JDBC;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class DB_Connection {

    // DB connection URLs
    // -------------------------------------------------------------------
    private final String db_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    // private final String db_URL = "jdbc:oracle:thin:@vsgate-s1.dei.isep.ipp.pt:10988:XE";
    private final String userName = "";
    private final String password = "";


    // ******************** Basic CRUD Functionally ***********************

    // 1) --> Read
    public void select(String SQL_Query) {
        try {
            Connection db_connection = DriverManager.getConnection(this.db_URL, this.userName, this.password);
            Statement statement = db_connection.createStatement();
            ResultSet rs = statement.executeQuery(SQL_Query);

            while (rs.next()) {
                System.out.println(rs.getString("FirstName") + " " + rs.getString("LastName")); // column name(s)
            }

            db_connection.close();

        } catch (SQLException e) {
            System.out.println("Something went wrong :(");
            e.printStackTrace();
        }
    }

    public void select_and_Store(String SQL_Query) {
        ArrayList<String> output = new ArrayList<>();
        try {
            Connection db_connection = DriverManager.getConnection(this.db_URL, this.userName, this.password);
            Statement statement = db_connection.createStatement();
            ResultSet rs = statement.executeQuery(SQL_Query);

            while (rs.next()) {
                output.add(rs.getString("...")); // column name
                System.out.println(rs.getString("...")); // column name(s)
            }

            db_connection.close();

        } catch (SQLException e) {
            System.out.println("Something went wrong :(");
            e.printStackTrace();
        }
    }

    // 2) --> Create OR INSERT
    public void create_or_INSERT(String SQL_Query) {
        try {
            Connection db_connection = DriverManager.getConnection(this.db_URL, this.userName, this.password);
            Statement statement = db_connection.createStatement();
            statement.executeUpdate(SQL_Query);
            db_connection.close();

        } catch (SQLException e) {
            System.out.println("Something went wrong :(");
            e.printStackTrace();
        }
    }

    // 3) --> Delete
    public void delete(String SQL_Query) {
        try {
            Connection db_connection = DriverManager.getConnection(this.db_URL, this.userName, this.password);
            Statement statement = db_connection.createStatement();
            int rowsAffected = statement.executeUpdate(SQL_Query);

            System.out.println("Rows affected: " + rowsAffected);
            System.out.println("Delete complete");

            db_connection.close();

        } catch (SQLException e) {
            System.out.println("Something went wrong :(");
            e.printStackTrace();
        }
    }

    // ******************** Prepared Statements ***********************
    // 4)
    public void select_ps(String param) {
        try {

            Connection db_connection = DriverManager.getConnection(this.db_URL, this.userName, this.password);
            PreparedStatement preparedStatement = db_connection.prepareStatement("SELECT * FROM test WHERE FIRSTNAME=?");

            // Set the parameters
            preparedStatement.setString(1, param); // Summer of Love
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString("...")); // column name(s)
            }

            db_connection.close();

        } catch (SQLException e) {
            System.out.println("Something went wrong :(");
            e.printStackTrace();
        }
    }

    // ******************** Stored Procedures ***********************
    // 5)
    public void storedProcedure() {
        // NOTE: the procedure should already be in the database
        // ------------------------------------------------------
        try {
            Connection db_connection = DriverManager.getConnection(this.db_URL, this.userName, this.password);

            // Set the parameters
            String paramOne = "Some String";
            int paramTwo = 12345;

            // Prepare the stored procedure call
            CallableStatement callableStatement = db_connection.prepareCall("{call precedureName(?, ?)}");

            // Assign values to params
            callableStatement.setString(1, paramOne); // it will replace the first '?'
            callableStatement.setInt(2, paramTwo); // it will replace the second '?'

            // call Stored procedure
            callableStatement.execute();

            db_connection.close();

        } catch (SQLException e) {
            System.out.println("Something went wrong :(");
            e.printStackTrace();
        }
    }

    // ******************** Transactions ***********************
    // NOTES:
    // * A transaction is a unity of work.
    // * One or more SQL statements executed together
    //    - Either all the statements are executed - commit
    //    - Or none of the statements are executed - rollback

    // 6)
    public void transaction(String SQL_Query_1, String SQL_Query_2) {
        try {
            Connection db_connection = DriverManager.getConnection(this.db_URL, this.userName, this.password);

            // Turn off auto-commit
            db_connection.setAutoCommit(false); // because by default is set to TRUE

            // Transaction
            Statement statement = db_connection.createStatement();
            statement.executeUpdate(SQL_Query_1);

            statement.executeUpdate(SQL_Query_2);

            // Ask user if it is OK to save
            boolean ok = askUserToSave();

            if (ok) {
                // store in database
                db_connection.commit();
                System.out.println("Transaction committed!");
            } else {
                // rollback changes
                db_connection.rollback();
                System.out.println("Transaction Roll back!");
            }

            db_connection.close();

        } catch (SQLException e) {
            System.out.println("Something went wrong :(");
            e.printStackTrace();
        }
    }

    private boolean askUserToSave() {
        Scanner read = new Scanner(System.in);
        System.out.println("Do you want to save changes in DB? - (YES or NO)");
        String answer = read.nextLine();
        return answer.equalsIgnoreCase("yes");
    }

    // ******************** Metadata ***********************
    // 7)
    public void metadata() {
        try{
            Connection db_connection = DriverManager.getConnection(this.db_URL, this.userName, this.password);
            DatabaseMetaData databaseMetaData = db_connection.getMetaData();

            // Display info about database
            System.out.println("Product name: " + databaseMetaData.getDatabaseProductName());
            System.out.println("Product version " + databaseMetaData.getDatabaseProductVersion());
            System.out.println();

            // Display info about the jdbc driver
            System.out.println("JDBC driver name " + databaseMetaData.getDriverName());
            System.out.println("JDBC driver version " + databaseMetaData.getDriverVersion());
            System.out.println();

            db_connection.close();

        } catch (SQLException e) {
            System.out.println("Something went wrong :(");
            e.printStackTrace();
        }
    }

    // ******************** Reading connection properties from file ***********************
    public void loadCredentials(String SQL_Query) {
        try {
            // Load the credentials file
            Properties properties = new Properties();
            properties.load(new FileInputStream("db_credentials.properties")); // file with credentials

            // Read the properties
            String theUser = properties.getProperty("username");
            String thePassword = properties.getProperty("password");
            String theURL = properties.getProperty("dbURL");

            System.out.println("Connecting to database...");
            System.out.println("Database URL: " + theURL);
            System.out.println("User: " + theUser);
            System.out.println("=========================================================");
            System.out.println();

            Connection db_connection = DriverManager.getConnection(theURL, theUser, thePassword);
            Statement statement = db_connection.createStatement();
            ResultSet rs = statement.executeQuery(SQL_Query);

            while (rs.next()) {
                System.out.println(rs.getString("...")); // column name(s)
            }

            db_connection.close();

        } catch (SQLException | FileNotFoundException e) {
            System.out.println("Something went wrong :(");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
