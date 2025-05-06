package teamspeakbot.update;

import java.util.List;
import java.util.Objects;

public class UpdateInfo {
    private final String version;
    private final String date;
    private final List<String> changes;

    public UpdateInfo(String version, String date, List<String> changes) {
        this.version = version;
        this.date = date;
        this.changes = List.copyOf(changes);
    }

    public String getVersion() {
        return version;
    }

    public String getDate() {
        return date;
    }

    public List<String> getChanges() {
        return changes;
    }

    @Override
    public String toString() {
        return "UpdateInfo{" +
                "version='" + version + '\'' +
                ", date='" + date + '\'' +
                ", changes=" + changes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateInfo that = (UpdateInfo) o;
        return version.equals(that.version) &&
                date.equals(that.date) &&
                changes.equals(that.changes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, date, changes);
    }
}
