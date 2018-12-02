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
            port      = 9000;
            //port     = 3306;
            //port     = 5433;
            database = "postgres";
            username = "postgres";
            password = "melancia";

            //String url = "jdbc:postgresql://localhost:5432/example"
            String url = "jdbc:postgresql://" + hostname + ":" + port + "/" + database;

            //String url = "jdbc:mysql://" + hostname + ":" + port + "/" + database;
            //DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection(url, username, password);

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