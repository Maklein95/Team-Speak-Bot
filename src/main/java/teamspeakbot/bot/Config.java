package teamspeakbot.bot;

public interface Config {
    String getHost();
    String getLogin();
    String getPassword();
    String getBotNickname();
    String getUpdateChannel();
    String getDatabaseUrl();
    String getDatabaseUser();
    String getDatabasePassword();
}