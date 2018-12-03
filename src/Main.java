
import dao.DataSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;

public class Main {
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {

        DataSource ds = new DataSource();

        while (true) {
            System.out.println("Digite o comando:\n\n" +
                    "1 - Dado um nome de um paciente retornar os dias que vai na academia, o nome do professor e horario(inicio e fim)\n" +
                    "2 - Retorna todos refeições com seus alimentos(e características desses incluindo quantidade) da dietas feitas por um nutricionista(dado o nome), de cada consulta\n" +
                    "3 - Dada uma consulta dá a dieta programada e seus alimentos com medida\n" +
                    "4 - Retorna nome e o numero de aulas que cada personal trainer dá na academia\n"+
                    "5 - Retorna os alunos que tem aula com os mesmos professores\n" +
                    "6 - Retorna os pacientes que fazem academia e tem consultas com um nutricionista\n" +
                    "7 - Dado um nome de um paciente, mostra seu historico de peso e o que tipo de funcionario que fez as medidas\n" +
                    "8 - Sair");

            String i = "";

            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                i = in.readLine();
            } catch (IOException e ) {
                System.err.println(e.getMessage());
            }


            switch (i) {
                case "1":

                    try {
                        System.out.println("1 - Dado um nome de um paciente retornar os dias que vai na academia, o nome do professor e horario(inicio e fim)\n" +
                                "Digite o nome do paciente: ");
                        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                        String nome = in.readLine();
                        func1(nome, ds);

                    } catch (IOException e ) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case "2":

                    try {
                        System.out.println("2 - Retorna todos refeições com seus alimentos(e características desses incluindo quantidade) da dietas feitas por um nutricionista(dado o nome), de cada consulta\n" +
                                "Digite o nome de um nutricionista: ");
                        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                        String nome = in.readLine();
                        func2(nome, ds);

                    } catch (IOException e ) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case "3":
                    try {
                        System.out.print("3 - Dada uma consulta dá a dieta programada e seus alimentos com medida\n" +
                                "Digite o número da consulta: ");
                        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                        String nome = in.readLine();
                        func3(nome, ds);

                    } catch (IOException e ) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case "4":

                        System.out.println("4 - retorna nome e o numero de aulas que cada personal trainer dá na academia");
                        func4("", ds);

                    break;
                case "5":

                        System.out.println("5 - Retorna os alunos que tem aula com os mesmos professores\n");
                        func5("", ds);
                    break;
                case "6":

                        System.out.println("6 - Retorna os pacientes que fazem academia e tem consultas com um nutricionista");
                        func6("", ds);
                    break;
                case "7":
                    try {
                        System.out.print( "7 - Dado um nome de um paciente, mostra seu historico de peso e o que tipo de funcionario que fez as medidas\n" +
                                "Digite o nome do paciente: ");
                        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                        String nome = in.readLine();
                        func7(nome, ds);
                    } catch (IOException e) {
                        System.err.print(e.getMessage());
                    }

                    break;
                case "8":
                    ds.closeDataSource();
                    return;
                default:

                    System.out.println("Comando não encontrado");

                    break;
            }
        }
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


            while(rs.next()){
                String nome = rs.getString("nome");
                String data_t = rs.getString("data_t");
                int id_paciente = rs.getInt("id_paciente");
                String hora_inicio = rs.getString("hora_inicio");
                String hora_fim = rs.getString("hora_fim");

                System.out.println("ID: " + id_paciente + " Nome: " + nome + " Data: " + data_t + " Inicio: " + hora_fim + " Fim: "+ hora_fim);
            }
            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }


    static void func2(String n, DataSource ds) {

        try {

            Connection con = ds.getConnection();
            Statement stmt = con.createStatement();
            String sql = "select id_consulta,nome,horario,nome_alimento,nome_medida,porcao_g,energia,proteina,calcio,ferro,colesterol\n" +
                    "from academia.medida_caseira natural join academia.alimento join academia.op_refeicao using(id_medida) natural join academia.refeicao\n" +
                    "where id_consulta in(select id_consulta\n" +
                    "\t\t     from academia.consulta\n" +
                    "\t\t     where crn in( select crn\n" +
                    "\t\t\t\t   from academia.nutricionista \n" +
                    "\t\t\t\t   where cod_f in(select cod_f\n" +
                    "\t\t\t\t\t          from academia.funcionario\n" +
                    "\t\t\t\t\t          where cod_p in(select cod_p\n" +
                    "\t\t\t\t\t\t                 from academia.pessoa\n" +
                    "\t\t\t\t\t\t                 where nome='"+n+"'))))\n" +
                    "order by id_consulta;";
            ResultSet rs = stmt.executeQuery(sql);


            while(rs.next()){

                String nome = rs.getString("nome");
                String horario = rs.getString("horario");
                int id_paciente = rs.getInt("id_consulta");
                String nome_alimento = rs.getString("nome_alimento");
                String nome_medida = rs.getString("nome_medida");
                Double porcao_g = rs.getDouble("porcao_g");
                Double energia = rs.getDouble("energia");
                Double proteina = rs.getDouble("proteina");
                Double calcio = rs.getDouble("calcio");
                Double ferro = rs.getDouble("ferro");
                Double colesterol = rs.getDouble("colesterol");

                System.out.println("id_paciente: " + id_paciente +
                        " nome: " + nome +
                        " horario: " + horario +
                        " nome_alimento: " + nome_alimento +
                        " nome_medida: " + nome_medida +
                        " porcao_g: " + porcao_g +
                        " energia: " + energia +
                        " proteina: " + proteina +
                        " calcio: " + calcio +
                        " ferro: " + ferro +
                        " colesterol: " + colesterol);
            }
            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    static void func3(String n, DataSource ds) {

        try {

            Connection con = ds.getConnection();
            Statement stmt = con.createStatement();
            String sql = "select categoria_dieta, nome, nome_alimento, nome_medida, porcao_g\n " +
            "from academia.consulta natural join academia.dieta natural join academia.refeicao natural join academia.op_refeicao natural join academia.alimento join academia.medida_caseira using(id_medida)\n" +
                    "where id_consulta = " + n + "\n" +
                    "order by categoria_dieta;";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                String nome = rs.getString("nome");
                String categoria_dieta = rs.getString("categoria_dieta");
                String nome_medida = rs.getString("nome_medida");
                String nome_alimento = rs.getString("nome_alimento");
                double porcao_g = rs.getDouble("porcao_g");

                System.out.println("nome: " + nome + " categoria_dieta: " + categoria_dieta + " nome_medida: " + nome_medida + " nome_alimento: " + nome_alimento + " porcao_g: "+ porcao_g);
            }
            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    static void func4(String n, DataSource ds) {

        try {

            Connection con = ds.getConnection();
            Statement stmt = con.createStatement();
            String sql = "    select nome, count(*)\n" +
                    "    from academia.pessoa natural join academia.funcionario natural join academia.personal_trainer natural join academia.treino\n" +
                    "    group by nome;";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                String nome = rs.getString("nome");
                int count = rs.getInt("count");

                System.out.println("nome: " + nome + " count: " + count);
            }
            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    static void func5(String n, DataSource ds) {

        try {

            Connection con = ds.getConnection();
            Statement stmt = con.createStatement();
            String sql = " select nome, id_paciente\n" +
                    "from academia.pessoa natural join academia.paciente p\n" +
                    "where id_paciente <> 1 and not exists(select cref\n" +
                    "\t\t\t\t      from academia.treino\n" +
                    "\t\t\t\t      where id_paciente=1 and cref not in(select distinct(cref)\n" +
                    "\t\t\t\t\t\t\t\t\t  from academia.treino\n" +
                    "\t\t\t\t\t\t\t\t\t  where id_paciente= p.id_paciente));";
            ResultSet rs = stmt.executeQuery(sql);


            while(rs.next()){
                String nome = rs.getString("nome");
                int id_paciente = rs.getInt("id_paciente");

                System.out.println("nome: " + nome + " id_paciente: " + id_paciente);
            }
            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    static void func6(String n, DataSource ds) {

        try {

            Connection con = ds.getConnection();
            Statement stmt = con.createStatement();
            String sql = "select distinct(id_paciente),nome\n" +
                    "from academia.pessoa natural join academia.paciente natural join academia.consulta natural join academia.treino\n" +
                    "order by id_paciente;";
            ResultSet rs = stmt.executeQuery(sql);


            while(rs.next()){
                String nome = rs.getString("nome");
                int id_paciente = rs.getInt("id_paciente");

                System.out.println("nome: " + nome + " id_paciente: " + id_paciente);
            }
            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    static void func7(String n, DataSource ds) {

        try {

            Connection con = ds.getConnection();
            Statement stmt = con.createStatement();
            String sql = "select  func,data_m, altura,peso\n" +
                    "from academia.medidas_corporais natural join academia.paciente natural join academia.pessoa join academia.funcionario using(cod_f)\n" +
                    "where nome ='"+n+"';";
            ResultSet rs = stmt.executeQuery(sql);


            while(rs.next()){

                String data_m = rs.getString("data_m");
                double peso = rs.getDouble("peso");
                double altura = rs.getDouble("altura");
                String func = rs.getString("func");

                System.out.println("funcao: " + func + " data_m: " + data_m + " peso: " + peso + " altura: " + altura);
            }
            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

}