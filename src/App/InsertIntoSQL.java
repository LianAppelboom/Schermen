package App;

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
     *@param temperatuur
     *
     */
    public void insert(float temperatuur) {
        String sql = "update cooldown.fles SET temperatuur = ? where fles_id = '"+ Data.text + "'";
        try {
            PreparedStatement preparedStatement = connect().prepareStatement(sql);
            preparedStatement.setFloat(1, temperatuur);
            preparedStatement.executeUpdate();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
