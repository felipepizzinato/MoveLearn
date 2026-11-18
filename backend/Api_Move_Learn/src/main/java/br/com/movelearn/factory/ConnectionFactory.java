
package br.com.movelearn.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection getConnection() throws SQLException {
        String dbUser = System.getenv("DB_USER");
        String dbPass = System.getenv("DB_PASS");

        Connection connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@//oracle.fiap.com.br:1521/ORCL",
                dbUser,
                dbPass
        );

        return connection;
    }
}
