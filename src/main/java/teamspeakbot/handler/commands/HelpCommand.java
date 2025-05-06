package teamspeakbot.handler.commands;

import com.github.theholywaffle.teamspeak3.TS3Api;
import teamspeakbot.bot.BotMessages;
import teamspeakbot.handler.MessageSender;

import static teamspeakbot.teamspeak.TS3ApiProvider.logger;

public class HelpCommand implements Command {
    private final MessageSender messageSender;

    public HelpCommand(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public void execute(TS3Api api, int clientDatabaseId, boolean isChannel, boolean isPrivate) {
        messageSender.sendResponse(clientDatabaseId, BotMessages.HELP_MESSAGE, isChannel, isPrivate);
        logger.info("Запрос команды !help пользователем ID {}", clientDatabaseId);
    }
}
