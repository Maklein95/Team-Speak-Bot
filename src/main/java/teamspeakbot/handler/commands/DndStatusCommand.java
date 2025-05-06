package teamspeakbot.handler.commands;

import com.github.theholywaffle.teamspeak3.TS3Api;
import teamspeakbot.bot.BotMessages;
import teamspeakbot.users.UserManager;
import teamspeakbot.handler.MessageSender;

import static teamspeakbot.teamspeak.TS3ApiProvider.logger;

public class DndStatusCommand implements Command {
    private final UserManager userManager;
    private final MessageSender messageSender;

    public DndStatusCommand(UserManager userManager, MessageSender messageSender) {
        this.userManager = userManager;
        this.messageSender = messageSender;
    }

    @Override
    public void execute(TS3Api api, int clientDatabaseId, boolean isChannel, boolean isPrivate) {
        boolean isDndActive = userManager.isDndActive(clientDatabaseId);
        String statusMessage = isDndActive ? BotMessages.DND_ENABLED : BotMessages.DND_DISABLED;
        messageSender.sendResponse(clientDatabaseId, statusMessage, isChannel, isPrivate);
        logger.info("Проверка DND для пользователя ID {}", clientDatabaseId, statusMessage);
    }
}
