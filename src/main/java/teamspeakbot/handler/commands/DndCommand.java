package teamspeakbot.handler.commands;

import com.github.theholywaffle.teamspeak3.TS3Api;
import teamspeakbot.bot.BotMessages;
import teamspeakbot.users.UserManager;
import teamspeakbot.handler.MessageSender;

import static teamspeakbot.teamspeak.TS3ApiProvider.logger;

public class DndCommand implements Command {
    private final UserManager userManager;
    private final MessageSender messageSender;

    public DndCommand(UserManager userManager, MessageSender messageSender) {
        this.userManager = userManager;
        this.messageSender = messageSender;
    }

    @Override
    public void execute(TS3Api api, int clientDatabaseId, boolean isChannel, boolean isPrivate) {
        boolean currentState = userManager.isDndActive(clientDatabaseId);

        if (currentState) {
            userManager.disableDndMode(clientDatabaseId);
            messageSender.sendResponse(clientDatabaseId, BotMessages.DND_DISABLED, isChannel, isPrivate);
            logger.info("DND отключён для пользователя ID {}", clientDatabaseId);
        } else {
            userManager.setDndMode(clientDatabaseId);
            messageSender.sendResponse(clientDatabaseId, BotMessages.DND_ENABLED, isChannel, isPrivate);
            logger.info("DND включён для пользователя ID {}", clientDatabaseId);
        }
    }
}
