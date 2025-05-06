package teamspeakbot.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class QueryFileReader implements QueryProvider {
    private static final Logger logger = LoggerFactory.getLogger(QueryFileReader.class);
    private static final String QUERY_PATH = "query/%s.sql";

    @Override
    public String getQuery(String queryName) {
        String filePath = String.format(QUERY_PATH, queryName);
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(filePath)) {
            if (input == null) {
                logger.error("Файл запроса не найден: {}", filePath);
                throw new RuntimeException("SQL файл не найден: " + filePath);
            }
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error("Ошибка при чтении файла запроса '{}'", filePath, e);
            throw new RuntimeException("Ошибка при чтении файла запроса: " + queryName, e);
        }
    }
}
