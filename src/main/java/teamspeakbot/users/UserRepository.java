package teamspeakbot.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import teamspeakbot.database.DatabaseManager;
import teamspeakbot.database.QueryProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;

public class UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);
    private final QueryProvider queryProvider;
    private final DatabaseManager databaseManager;

    public UserRepository(QueryProvider queryProvider, DatabaseManager databaseManager) {
        this.queryProvider = queryProvider;
        this.databaseManager = databaseManager;
    }

    public <T> T executeQuerySingleResult(String queryKey, int clientDatabaseId, String columnName, Class<T> type) {
        String sql = queryProvider.getQuery(queryKey);
        try (Connection conn = databaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, clientDatabaseId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getObject(columnName, type);
            }
        } catch (SQLException e) {
            logger.error("❌ Ошибка SQL в executeQuerySingleResult '{}': {}", queryKey, e.getMessage());
        }
        return null;
    }

    // Универсальный метод для выполнения UPDATE-запросов
    public void executeUpdate(String queryKey, Object... params) {
        System.out.println("📌 Выполняем SQL-запрос: " + queryKey + " с параметрами: " + Arrays.toString(params));
        String sql = queryProvider.getQuery(queryKey);
        try (Connection conn = databaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            int rowsUpdated = pstmt.executeUpdate();
            logger.info("✅ SQL-обновление '{}', затронуто строк: {}", queryKey, rowsUpdated);
        } catch (SQLException e) {
            logger.error("❌ Ошибка SQL в executeUpdate '{}': {}", queryKey, e.getMessage());
        }
    }

    public void insertUser(int clientDatabaseId, String userName) {
        String insertUser = queryProvider.getQuery("user_insert");
        try (Connection conn = databaseManager.getConnection();
             PreparedStatement insertStmt = conn.prepareStatement(insertUser)) {
            insertStmt.setInt(1, clientDatabaseId);
            insertStmt.setString(2, userName);
            insertStmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при записи в БД: " + e.getMessage());
        }
    }
}
