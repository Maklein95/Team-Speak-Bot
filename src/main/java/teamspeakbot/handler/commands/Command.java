package teamspeakbot.handler.commands;

import com.github.theholywaffle.teamspeak3.TS3Api;

public interface Command {
    void execute(TS3Api api, int clientDatabaseId, boolean isChannel, boolean isPrivate);
}
