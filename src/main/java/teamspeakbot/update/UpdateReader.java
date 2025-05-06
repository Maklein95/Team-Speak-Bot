package teamspeakbot.update;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UpdateReader {
    private UpdateRepository updateRepository;

    public List<UpdateInfo> readUpdatesFromFile(String filePath) {
        List<UpdateInfo> updates = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            String version = null;
            String date = null;
            List<String> changes = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.matches("^\\d+\\.\\d+\\.\\d+ - \\d{4}-\\d{2}-\\d{2}$")) {
                    if (version != null) {
                        updates.add(new UpdateInfo(version, date, changes));
                    }
                    String[] versionAndDate = line.split(" - ");
                    version = versionAndDate[0];
                    date = versionAndDate[1];
                    changes = new ArrayList<>();
                } else {
                    changes.add(line);
                }
            }

            if (version != null) {
                updates.add(new UpdateInfo(version, date, changes));
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла обновлений: " + e.getMessage());
        }
        return updates;
    }
    public void setUpdateRepository(UpdateRepository updateRepository) {
        this.updateRepository = updateRepository;
    }
}
