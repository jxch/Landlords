package mysqlTools;

import java.sql.DriverManager;
import java.sql.SQLException;


public class MysqlConnection {
    // FIXME: 2018/1/8
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/";
    private static final String databaseName = "userinfo";

    public static java.sql.Statement mysqlStatement(String databaseName, String username, String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        java.sql.Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName, username, password);
        return connection.createStatement();
    }
}
