package teamspeakbot.teamspeak;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import teamspeakbot.bot.BotConfig;

public class TS3ApiProvider {
    public static final Logger logger = LoggerFactory.getLogger(TS3ApiProvider.class);
    private final TS3Api api;

    public TS3ApiProvider(TS3ConnectionManager connectionManager, BotConfig botConfig) {
        TS3Query query = connectionManager.connect();
        api = query.getApi();

        try {
            // 🔹 Сначала выбираем сервер
            api.selectVirtualServerById(1); // Или api.selectVirtualServerByPort(botConfig.getServerPort());

            // 🔹 Затем подписываемся на события
            api.registerEvent(TS3EventType.SERVER);
            api.registerEvent(TS3EventType.TEXT_CHANNEL);
            api.registerEvent(TS3EventType.TEXT_PRIVATE);

            logger.info("✅ Бот подписан на события TeamSpeak!");

        } catch (Exception e) {
            logger.error("❌ Ошибка при подписке на события TS3: {}", e.getMessage());
        }
    }

    public TS3Api getApi() {
        return api;
    }
}
