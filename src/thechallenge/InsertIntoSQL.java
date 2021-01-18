package thechallenge;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class InsertIntoSQL {

    /**
     * Connect to the vb1.db database
     *
     * @return the Connection object
     */
    private Connection connect() {
        Connection conn = null;
        String driver = "com.mysql.cj.jdbc.Driver";
        // MySQL connection string, pas zonodig het pad aan:
        String connection = "jdbc:mysql://localhost/cooldown?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String user = "root";
        String password = "root";
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(connection, user, password);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Insert a new row into the tbl1 table
     *
     * @param temperatuur
     */
    public void insert(float temperatuur) {
        String tijdstip = "4";
        String sql = "INSERT INTO fles(temperatuur, temperatuur_g) VALUES(?,?)";
        try {
            PreparedStatement preparedStatement = connect().prepareStatement(sql);
            preparedStatement.setString(1, tijdstip);
            preparedStatement.setFloat(2, temperatuur);
            preparedStatement.executeUpdate();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}