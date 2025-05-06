package teamspeakbot.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class QueryCache implements QueryProvider {
    private static final Logger logger = LoggerFactory.getLogger(QueryCache.class);
    private final QueryProvider queryProvider;
    private final Map<String, String> cache = new ConcurrentHashMap<>();

    public QueryCache(QueryProvider queryProvider) {
        this.queryProvider = queryProvider;
    }

    @Override
    public String getQuery(String key) {
        return cache.computeIfAbsent(key, k -> {
            logger.debug("Загрузка SQL-запроса из источника: {}", k);
            return queryProvider.getQuery(k);
        });
    }
}
