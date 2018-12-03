package dao;

/**
 * Created by rodrigofranzoi on 02/12/18.
 */
public class Alimento {

    public int id;
    public String nome;
    public  double energia;
    public double ferro;
    public  double calcio;
    public double proteina;
    public double colesterol;

    Alimento(int id, String nome, double energia, double ferro, double calcio, double proteina, double colesterol) {
        this.id = id;
        this.energia = energia;
        this.ferro = ferro;
        this.calcio = calcio;
        this.proteina = proteina;
        this.colesterol = colesterol;
    }

}
