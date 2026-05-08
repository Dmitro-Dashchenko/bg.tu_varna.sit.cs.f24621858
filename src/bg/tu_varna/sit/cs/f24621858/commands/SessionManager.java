package bg.tu_varna.sit.cs.f24621858.commands;

import java.util.LinkedHashMap;
import java.util.Map;

public class SessionManager {

    private final Map<Integer, Session> sessions = new LinkedHashMap<>();
    private int nextId = 1;
    private Session currentSession = null;

    public Session createSession() {
        Session session = new Session(nextId++);
        sessions.put(session.getId(), session);
        currentSession = session;
        return session;
    }

    public Session switchSession(int id) {
        Session session = sessions.get(id);
        if (session == null) {
            throw new SessionNullPointerException("session with ID " + id + " does not exist.");
        }
        currentSession = session;
        return session;
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public boolean hasActiveSession() {
        return currentSession != null && currentSession.hasImages();
    }

    public Map<Integer, Session> getAllSessions() {
        return Map.copyOf(sessions);
    }

    public void closeCurrentSession() {
        if (currentSession != null) {
            sessions.remove(currentSession.getId());
            currentSession = null;
        }
    }
}
