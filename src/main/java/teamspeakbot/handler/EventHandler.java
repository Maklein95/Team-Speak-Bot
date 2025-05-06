package teamspeakbot.handler;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import com.github.theholywaffle.teamspeak3.api.TextMessageTargetMode;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import teamspeakbot.bot.BotMessages;
import teamspeakbot.users.UserManager;

import java.util.Optional;

public class EventHandler extends TS3EventAdapter {
    private final CommandHandler commandHandler;
    private final UserManager userManager;
    private final TS3Api api; // Добавили TS3Api

    public EventHandler(CommandHandler commandHandler, TS3Api api, UserManager userManager) {
        this.commandHandler = commandHandler;
        this.api = api; // Сохраняем API
        this.userManager = userManager;
    }

    @Override
    public void onTextMessage(TextMessageEvent event) {
        String message = event.getMessage();
        String uniqueId = event.getInvokerUniqueId();

        if (!message.startsWith("!")){
            return;
        }

        // Проверяем, не является ли отправитель ботом
        if (uniqueId.equals(api.whoAmI().getUniqueIdentifier())) {
            System.out.println("⏩ Бот отправил сообщение сам себе. Игнорируем.");
            return;
        }

        Optional<Client> client = api.getClients().stream()
                .filter(c -> c.getUniqueIdentifier().equals(uniqueId))
                .findFirst();

        int clientDatabaseId = client.map(Client::getDatabaseId).orElse(-1);

        if (clientDatabaseId == -1) {
            System.err.println("❌ Ошибка: clientDatabaseId не найден!");
            return;
        }

        boolean isChannel = event.getTargetMode() == TextMessageTargetMode.CHANNEL;
        boolean isPrivate = event.getTargetMode() == TextMessageTargetMode.CLIENT;

        System.out.println("📌 Сообщение от uniqueId: " + uniqueId + " -> clientDatabaseId: " + clientDatabaseId);
        commandHandler.handleCommand(clientDatabaseId, message, isChannel, isPrivate);
    }

    @Override
    public void onClientJoin(ClientJoinEvent event) {
        int clientDatabaseId = event.getClientDatabaseId();
        String userName = userManager.getUserName(clientDatabaseId, "user_name");

        if ("user_name".equals(userName)) {
            userManager.insertUser(clientDatabaseId, event.getClientNickname());
        }

        if (userManager.shouldGreet(clientDatabaseId)) {
            api.sendPrivateMessage(event.getClientId(), BotMessages.getWelcomeMessage(event.getClientNickname()));
            userManager.updateGreetingTime(clientDatabaseId);
        }
    }
}
