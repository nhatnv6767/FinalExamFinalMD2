package ra.database;

import java.io.FileInputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class JDBCUtil {
    private static final String DB_PROPERTIES_FILE = "src/database.properties";

    public Connection openConnection() {
        try (FileInputStream input = new FileInputStream(DB_PROPERTIES_FILE)) {
            Properties prop = new Properties();
            prop.load(input);
            String url = prop.getProperty("db.url");
            String user = prop.getProperty("db.user");
            String password = prop.getProperty("db.password");
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void closeConnection(Connection conn, CallableStatement callSt) {
        try {
            if (callSt != null) callSt.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
