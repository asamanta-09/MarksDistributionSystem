package setup;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminTableCreation {

    public static void createAdminTable(Connection connection) {
    	try {
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, "admin", null);

            if (!tables.next()) { // Table does not exist
                String createTableSQL = """
                    CREATE TABLE admin (
                        id VARCHAR(6) PRIMARY KEY,
                        email VARCHAR(255) NOT NULL UNIQUE,
                        password VARCHAR(255) NOT NULL
                    )
                """;

                try (Statement stmt = connection.createStatement()) {
                    stmt.executeUpdate(createTableSQL);
                    System.out.println("Admin table was created.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
