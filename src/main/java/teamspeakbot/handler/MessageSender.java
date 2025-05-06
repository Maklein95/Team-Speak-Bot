package teamspeakbot.handler;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class MessageSender {

    private final TS3Api api;
    private static final Logger logger = LoggerFactory.getLogger(MessageSender.class);


    public MessageSender(TS3Api api) {
        this.api = api;
    }

    public void sendResponse(int clientDatabaseId, String message, boolean isChannel, boolean isPrivate) {
        if (isChannel) {
            api.sendChannelMessage(message);
            logger.debug("📢 Отправлено сообщение в канал: {}", message);
        } else if (isPrivate) {
            int clientId = getClientIdByDatabaseId(clientDatabaseId);
            if (clientId == -1) {
                logger.error("❌ Ошибка: не найден clientId для clientDatabaseId=" + clientDatabaseId);
                return;
            }
            api.sendPrivateMessage(clientId, message);
            logger.debug("📩 Личное сообщение пользователю ID {}: {}", clientDatabaseId, message);
        }
    }

    private int getClientIdByDatabaseId(int clientDatabaseId) {
        Optional<Client> client = api.getClients().stream()
                .filter(c -> c.getDatabaseId() == clientDatabaseId)
                .findFirst();
        return client.map(Client::getId).orElse(-1);
    }
}
