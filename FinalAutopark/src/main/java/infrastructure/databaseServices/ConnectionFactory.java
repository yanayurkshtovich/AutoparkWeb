package infrastructure.databaseServices;

import java.sql.Connection;

public interface ConnectionFactory {
    Connection getConnection();
}
