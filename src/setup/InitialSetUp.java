package setup;

import java.sql.Connection;
import utility.DatabaseConnection;

public class InitialSetUp {
	 public static void init() {
	        Connection connection = DatabaseConnection.getInstance().getConnection();
	        AdminTableCreation.createAdminTable(connection);
	        MarksDetails_All_Table_Creation.createAllTables(connection);
	    }
}
