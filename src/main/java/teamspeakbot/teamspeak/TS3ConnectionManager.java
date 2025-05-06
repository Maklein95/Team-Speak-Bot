package teamspeakbot.teamspeak;

import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import teamspeakbot.bot.BotConfig;

public class TS3ConnectionManager {
    private static final Logger logger = LoggerFactory.getLogger(TS3ConnectionManager.class);
    private final BotConfig config;
    private TS3Query query;

    public TS3ConnectionManager(BotConfig config) {
        this.config = config;
    }

    /**
     * Устанавливает соединение с сервером TeamSpeak.
     */
    public TS3Query connect() {
        TS3Config ts3Config = new TS3Config();
        ts3Config.setHost(config.getHost());
        ts3Config.setEnableCommunicationsLogging(true);

        query = new TS3Query(ts3Config);
        try {
            query.connect();
            logger.info("✅ Подключение к TeamSpeak установлено!");
            return query;
        } catch (Exception e) {
            logger.error("❌ Ошибка подключения к TeamSpeak: ", e);
            throw new RuntimeException("Ошибка подключения к TS3", e);
        }
    }

    /**
     * Закрывает соединение.
     */
    public void disconnect() {
        if (query != null) {
            query.exit();
            logger.info("⏹️ Подключение к TeamSpeak закрыто.");
        }
    }
}
