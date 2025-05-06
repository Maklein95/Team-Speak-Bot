package teamspeakbot.update;

import teamspeakbot.database.DatabaseManager;
import teamspeakbot.database.QueryProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateRepository {
    private final DatabaseManager databaseManager;
    private final QueryProvider queryProvider;

    public UpdateRepository(DatabaseManager databaseManager, QueryProvider queryProvider) {
        this.databaseManager = databaseManager;
        this.queryProvider = queryProvider;
    }

    public void saveOrUpdate(String updateDescription, String appVersion, boolean sent) {
        String checkSql = queryProvider.getQuery("check_update");
        try (Connection conn = databaseManager.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setString(1, updateDescription);
            checkStmt.setString(2, appVersion);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int updateId = rs.getInt("id");
                String updateSql = queryProvider.getQuery("mark_update_as_sent");
                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                    updateStmt.setBoolean(1, sent);
                    updateStmt.setInt(2, updateId);
                    updateStmt.executeUpdate();
                }
            } else {
                String insertSql = queryProvider.getQuery("record_update");
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setString(1, updateDescription);
                    insertStmt.setString(2, appVersion);
                    insertStmt.setBoolean(3, sent);
                    insertStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при сохранении обновления: " + e.getMessage());
        }
    }

    public boolean isUpdateSent(String appVersion) {
        String sql = queryProvider.getQuery("is_update_sent");
        try (Connection conn = databaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, appVersion);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            System.err.println("Ошибка при проверке статуса обновления: " + e.getMessage());
            return false;
        }
    }
}
