package teamspeakbot;

import teamspeakbot.bot.BotConfig;
import teamspeakbot.database.DatabaseManager;
import teamspeakbot.database.QueryCache;
import teamspeakbot.database.QueryFileReader;
import teamspeakbot.database.QueryProvider;
import teamspeakbot.flyway.FlywayInitializer;
import teamspeakbot.teamspeak.TS3ApiProvider;
import teamspeakbot.teamspeak.TS3ConnectionManager;
import teamspeakbot.update.UpdateManager;
import teamspeakbot.update.UpdateReader;
import teamspeakbot.update.UpdateRepository;
import teamspeakbot.users.UserManager;
import teamspeakbot.users.UserRepository;

import javax.sql.DataSource;

public class BotInitializer {
    private final BotConfig config;
    private final DatabaseManager databaseManager;
    private final QueryProvider queryProvider;
    private final UserRepository userRepository;
    private final UserManager userManager;
    private final UpdateRepository updateRepository;
    private final UpdateReader updateReader;
    private final UpdateManager updateManager;
    private final TS3ConnectionManager ts3ConnectionManager;
    private final TS3ApiProvider ts3ApiProvider;

    public BotInitializer() {
        this.config = BotConfig.load();
        this.databaseManager = new DatabaseManager(
                config.getDatabaseUrl(),
                config.getDatabaseUser(),
                config.getDatabasePassword()
        );

        this.queryProvider = new QueryCache(new QueryFileReader());
        this.userRepository = new UserRepository(queryProvider, databaseManager);
        this.userManager = new UserManager(userRepository);
        this.updateRepository = new UpdateRepository(databaseManager, queryProvider);
        this.updateReader = new UpdateReader();
        this.updateReader.setUpdateRepository(updateRepository);
        this.updateManager = new UpdateManager(updateReader, updateRepository);
        this.ts3ConnectionManager = new TS3ConnectionManager(config);
        this.ts3ApiProvider = new TS3ApiProvider(ts3ConnectionManager, config);

        migrateDatabase(databaseManager.getDataSource());
    }

    private void migrateDatabase(DataSource dataSource) {
        new FlywayInitializer(databaseManager).migrateDatabase();
    }

    public BotConfig getConfig() {
        return config;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public UpdateManager getUpdateManager() {
        return updateManager;
    }

    public TS3ApiProvider getTs3ApiProvider() {
        return ts3ApiProvider;
    }
}
