package cz.oksystem.aihackathon.writerassistant.repository;

import com.azure.ai.openai.models.ChatRequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class SessionDataRepository {

    private Map<UUID, List<ChatRequestMessage>> store = new HashMap<>();

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    public void addToSessionHistory(UUID session, List<ChatRequestMessage> data) {
        logger.info("Session: {}. Persisted {} messages to history.", session, data.size());
        if (!store.containsKey(session)) {
            store.put(session, data);
            return;
        }
        store.get(session).addAll(data);
    }

    public List<ChatRequestMessage> getSessionHistory(UUID session) {
        var res = store.get(session);
        if (res == null ) {
            logger.info("No history associated with session: {}", session);
            return Collections.emptyList();
        }

        logger.info("Retrieved {} records asssociated with session {}.", res.size(), session);
        return res;
    }

}
