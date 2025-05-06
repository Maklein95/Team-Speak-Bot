package teamspeakbot;

import teamspeakbot.handler.CommandHandler;
import teamspeakbot.handler.EventHandler;
import com.github.theholywaffle.teamspeak3.TS3Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import teamspeakbot.update.UpdateManager;
import teamspeakbot.users.UserManager;

public class BotRunner {
    private static final Logger logger = LoggerFactory.getLogger(BotRunner.class);

    public static void main(String[] args) {
        logger.info("🚀 Запуск TeamSpeak бота...");

        BotInitializer initializer = new BotInitializer();

        TS3Api api = initializer.getTs3ApiProvider().getApi();
        UserManager userManager = initializer.getUserManager();
        UpdateManager updateManager = initializer.getUpdateManager();

        try {
            int botClientId = api.whoAmI().getId(); // Получаем ClientID бота
            logger.info("✅ Определен botClientId: {}", botClientId);
        } catch (Exception e) {
            logger.error("❌ Ошибка при получении botClientId: {}", e.getMessage());
        }

        CommandHandler commandHandler = new CommandHandler(api, userManager);
        EventHandler eventHandler = new EventHandler(commandHandler, api, userManager);

        api.registerAllEvents();
        api.addTS3Listeners(eventHandler);

        logger.info("✅ Бот успешно запущен и готов к работе!");

        if (updateManager != null) {
            logger.info("UpdateManager инициализирован.");
            sendStartupUpdate(initializer, api, updateManager);
        }
    }

    private static void sendStartupUpdate(BotInitializer initializer, TS3Api api, UpdateManager updateManager) {
        if (updateManager == null) {
            return;
        }
        try {
            int channelId = Integer.parseInt(initializer.getConfig().getUpdateChannel());
            updateManager.sendUpdateMessage(api, channelId, "src/main/resources/updates.txt");
            logger.info("✅ Сообщение с обновлениями отправлено в канал ID {}", channelId);
        } catch (NumberFormatException e) {
            logger.error("❌ Ошибка: Некорректный формат `updateChannel` в конфиге!", e);
        } catch (Exception e) {
            logger.error("❌ Ошибка при отправке обновлений: {}", e.getMessage());
        }
    }
}
