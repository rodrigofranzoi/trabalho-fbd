
import dao.DataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.*;
import javax.xml.crypto.Data;
import java.sql.*;

public class Main extends JFrame {
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {

        System.out.println("Digite o comando:\n\n\n " +
                "1 - Dado um nome de um paciente retornar os dias que vai na academia, o nome do professor e horario(inicio e fim)\n\n" +
                "2 - Retorna todos refeições com seus alimentos(e características desses incluindo quantidade) da dietas feitas por um nutricionista(dado o nome), de cada consulta\n\n" +
                "3 - Dada uma consulta dá a dieta programada e seus alimentos com medida\n\n" +
                "4 - Retorna nome e o numero de aulas que cada personal trainer dá na academia\n\n" +
                "5 - \n\n" +
                "6 - \n\n");


        switch ()




        DataSource ds = new DataSource();
        func1("bianca", ds);

    }




    static void func1(String n, DataSource ds) {

        try {

            Connection con = ds.getConnection();
            Statement stmt = con.createStatement();
            String sql = "select id_paciente,data_t,hora_inicio,hora_fim,nome\n" +
                    "from academia.treino natural join  academia.personal_trainer  natural join academia.funcionario  natural join academia.pessoa\n" +
                    "where id_paciente in (select id_paciente\n" +
                    "from academia.paciente natural join academia.pessoa\n" +
                    "where nome = '"+n+"');";
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println(rs.toString());

            while(rs.next()){
                String nome = rs.getString("nome");



                System.out.println(" Nome: " + nome);
            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }


}