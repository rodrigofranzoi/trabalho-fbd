package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {

    private String hostname;
    private int    port;
    private String database;
    private String username;
    private String password;

    private Connection connection;

    public DataSource() {
        try {
            hostname = "localhost";
            port = 5432;
            database = "postgres";
            username = "postgres";
            password = "melancia";

            String url = "jdbc:postgresql://" + hostname + ":" + port + "/" + database;
            //String url = "postgresql://localhost";
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Conexão Efetuada!");

        } catch (SQLException ex) {
            System.err.println("Erro Conexão " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Erro " + ex.getMessage());
        }


    }

    public Connection getConnection() {
        return this.connection;
    }

    public void closeDataSource() {

        try {
            connection.close();
            System.out.println("Conexão encerrada com sucesso!");
        } catch (SQLException ex) {
            System.err.println("Erro para desconectar " + ex.getMessage());
        }

    }

}