package teamspeakbot.bot;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BotConfig implements Config {
    private final String host;
    private final String login;
    private final String password;
    private final String botNickname;
    private final String updateChannel;
    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;

    private BotConfig(Builder builder) {
        this.host = builder.host;
        this.login = builder.login;
        this.password = builder.password;
        this.botNickname = builder.botNickname;
        this.updateChannel = builder.updateChannel;
        this.dbUrl = builder.dbUrl;
        this.dbUser = builder.dbUser;
        this.dbPassword = builder.dbPassword;
    }

    public static BotConfig load() {
        Properties properties = new Properties();
        try (InputStream input = BotConfig.class.getClassLoader().getResourceAsStream("bot.properties")) {
            if (input == null) {
                throw new RuntimeException("Не удалось найти файл bot.properties");
            }
            properties.load(input);

            return new Builder()
                    .host(properties.getProperty("host"))
                    .login(properties.getProperty("login"))
                    .password(properties.getProperty("password"))
                    .botNickname(properties.getProperty("bot.nickname"))
                    .updateChannel(properties.getProperty("update.channel"))
                    .dbUrl(properties.getProperty("db.url"))
                    .dbUser(properties.getProperty("db.user"))
                    .dbPassword(properties.getProperty("db.password"))
                    .build();

        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке файла конфигурации bot.properties", e);
        }
    }

    public static class Builder {
        private String host;
        private String login;
        private String password;
        private String botNickname;
        private String updateChannel;
        private String dbUrl;
        private String dbUser;
        private String dbPassword;

        public Builder host(String host) {
            this.host = host;
            return this;
        }

        public Builder login(String login) {
            this.login = login;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder botNickname(String botNickname) {
            this.botNickname = botNickname;
            return this;
        }

        public Builder updateChannel(String updateChannel) {
            this.updateChannel = updateChannel;
            return this;
        }

        public Builder dbUrl(String dbUrl) {
            this.dbUrl = dbUrl;
            return this;
        }

        public Builder dbUser(String dbUser) {
            this.dbUser = dbUser;
            return this;
        }

        public Builder dbPassword(String dbPassword) {
            this.dbPassword = dbPassword;
            return this;
        }

        public BotConfig build() {
            return new BotConfig(this);
        }
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getBotNickname() {
        return botNickname;
    }

    @Override
    public String getUpdateChannel() {
        return updateChannel;
    }

    @Override
    public String getDatabaseUrl() {
        return dbUrl;
    }

    @Override
    public String getDatabaseUser() {
        return dbUser;
    }

    @Override
    public String getDatabasePassword() {
        return dbPassword;
    }
}
