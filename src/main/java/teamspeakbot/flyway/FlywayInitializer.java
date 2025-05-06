package teamspeakbot.flyway;

import org.flywaydb.core.Flyway;
import teamspeakbot.database.DatabaseManager;

import javax.sql.DataSource;

public class FlywayInitializer {
    private final DataSource dataSource;

    public FlywayInitializer(DatabaseManager databaseManager) {
        this.dataSource = databaseManager.getDataSource();
    }

    public void migrateDatabase() {
        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();
    }
}
