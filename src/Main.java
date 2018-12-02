
import dao.DataSource;

import javax.swing.JFrame;
import javax.xml.crypto.Data;

public class Main extends JFrame{
    private static final long serialVersionUID = 1L;
    public static void main(String[] args) {

        DataSource ds = new DataSource();
        ds.closeDataSource();

    }
}
