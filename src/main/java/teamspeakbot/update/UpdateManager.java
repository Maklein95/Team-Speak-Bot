package teamspeakbot.update;

import com.github.theholywaffle.teamspeak3.TS3Api;

import java.util.List;

public class UpdateManager {
    private final UpdateReader updateReader;
    private final UpdateRepository updateRepository;

    public UpdateManager(UpdateReader updateReader, UpdateRepository updateRepository) {
        this.updateReader = updateReader;
        this.updateRepository = updateRepository;
    }

    public void sendUpdateMessage(TS3Api api, int channelId, String filePath) {
        List<UpdateInfo> updates = updateReader.readUpdatesFromFile(filePath);
        if (updates.isEmpty()) {
            System.out.println("Нет обновлений для отправки.");
            return;
        }

        for (UpdateInfo update : updates) {
            String updateDescription = "Версия " + update.getVersion() + " - " + update.getDate();
            String appVersion = update.getVersion();

            if (updateRepository.isUpdateSent(appVersion)) {
                System.out.println("Обновление для версии " + appVersion + " уже отправлено. Пропускаем.");
                continue;
            }

            StringBuilder updateMessage = new StringBuilder("🔥 Обновления:\n\n")
                    .append("**Версия ").append(update.getVersion()).append("** - ").append(update.getDate()).append("\n");
            for (String change : update.getChanges()) {
                updateMessage.append("- ").append(change).append("\n");
            }

            api.sendChannelMessage(channelId, updateMessage.toString());
            updateRepository.saveOrUpdate(updateDescription, appVersion, true);
        }
    }
}
