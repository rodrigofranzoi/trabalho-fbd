package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by rodrigofranzoi on 02/12/18.
 */

public class AlimentoDAO {

    private DataSource ds;

    public ArrayList<String> readAll() {

        try{

            String sql = "select * from academia.medida_caseira;";
            PreparedStatement ps = ds.getConnection().prepareCall(sql);
            ResultSet rs = ps.executeQuery();

            ArrayList<String> alimentos = new ArrayList<String>();

            while(rs.next()) {
                System.out.println(rs.toString());
                //int id = rs.getInt("id_alimento");
                String nome =  rs.getString("nome_medida");
//                double colesterol =  rs.getDouble("colesterol");
//                double ferro =  rs.getDouble("ferro");
//                double energia =  rs.getDouble("energia");
//                double proteina =  rs.getDouble("proteina");
//                double calcio =  rs.getDouble("calcio");

                //Alimento al = new Alimento(id, nome, energia, ferro, calcio,proteina,colesterol);
                System.out.println(nome);
                alimentos.add(nome);
            }
            ps.close();
            return  alimentos;

        } catch (SQLException ex) {
            System.err.println("Erro Conexão " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Erro " + ex.getMessage());
        }
        return null;
    }
}
