package teamspeakbot.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;


public class UserManager {
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserManager.class);


    public UserManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void insertUser(int clientDatabaseId, String defaultName) {
        userRepository.insertUser(clientDatabaseId, defaultName);
        logger.info("✅ Добавлен новый пользователь ID {} с именем '{}'", clientDatabaseId, defaultName);
    }

    public String getUserName(int clientDatabaseId, String defaultName) {
        String username = userRepository.executeQuerySingleResult("get_user_name", clientDatabaseId, "user_name", String.class);
        logger.debug("📌 Найдено имя пользователя для ID {}: {}", clientDatabaseId, username);
        return username != null ? username : defaultName;
    }

    public boolean shouldGreet(int clientDatabaseId) {
        Timestamp lastGreet = userRepository.executeQuerySingleResult("should_greet", clientDatabaseId, "last_greeting_time", Timestamp.class);
        return lastGreet == null || (System.currentTimeMillis() - lastGreet.getTime() > 604800000);
    }

    public boolean isDndActive(int clientDatabaseId) {
        Boolean dndActive = userRepository.executeQuerySingleResult("is_dnd_active", clientDatabaseId, "dnd", Boolean.class);
        logger.debug("📌 DND статус для ID {}: {}", clientDatabaseId, dndActive);
        return Boolean.TRUE.equals(dndActive);
    }

    public void updateGreetingTime(int clientDatabaseId) {
        userRepository.executeUpdate("update_greeting_time", clientDatabaseId);
        logger.debug("🔄 Обновлено время последнего приветствия для ID {}", clientDatabaseId);
    }

    public void setDndMode(int clientDatabaseId) {
        userRepository.executeUpdate("set_dnd_mode", clientDatabaseId);
        logger.debug("✅ Установлен DND режим для ID {}", clientDatabaseId);
    }

    public void disableDndMode(int clientDatabaseId) {
        userRepository.executeUpdate("disable_dnd_mode", clientDatabaseId);
        logger.debug("❌ Отключён DND режим для ID {}", clientDatabaseId);
    }
}
