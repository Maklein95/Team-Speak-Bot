package teamspeakbot.handler;

import com.github.theholywaffle.teamspeak3.TS3Api;
import teamspeakbot.handler.commands.*;
import teamspeakbot.users.UserManager;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    private final TS3Api api;
    private final Map<String, Command> commands = new HashMap<>();
    private final MessageSender messageSender;

    public CommandHandler(TS3Api api, UserManager userManager) {
        this.api = api;
        this.messageSender = new MessageSender(api);

        commands.put("ping", new PingCommand(messageSender));
        commands.put("help", new HelpCommand(messageSender));
        commands.put("serverinfo", new ServerInfoCommand(messageSender));
        commands.put("rules", new RulesCommand(messageSender));
        commands.put("dnd", new DndCommand(userManager, messageSender));
        commands.put("dndstatus", new DndStatusCommand(userManager, messageSender));
    }

    public void handleCommand(int clientDatabaseId, String message, boolean isChannel, boolean isPrivate) {
        String commandKey = message.trim().toLowerCase().replace("!", "");
        Command command = commands.get(commandKey);

        if (command != null) {
            command.execute(api, clientDatabaseId, isChannel, isPrivate);
        } else {
            messageSender.sendResponse(clientDatabaseId, "❌ Ты еблан?Нет блять такой команды. Напиши !help", isChannel, isPrivate);
        }
    }
}
