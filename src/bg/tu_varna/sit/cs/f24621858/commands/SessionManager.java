package bg.tu_varna.sit.cs.f24621858.commands;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Manages the full lifecycle of all {@link Session} objects for the duration
 * of one application run.
 *
 * <p>Session IDs are assigned sequentially starting from {@code 1}.
 * At most one session is <em>current</em> (active) at a time; the current
 * session is the target of all image-editing commands.
 *
 * @author Dmitro Dashchenko
 *
 * @see Session
 */
public class SessionManager {

    /** All live sessions keyed by their id, in creation order. */
    private final Map<Integer, Session> sessions = new LinkedHashMap<>();

    /** ID that will be assigned to the next newly created session. */
    private int nextId = 1;

    /** The currently active session, or {@code null} when none is open. */
    private Session currentSession = null;

    /**
     * Creates a new {@link Session}, registers it, sets it as the current
     * session, and returns it.
     *
     * <p>The new session's ID equals the previous highest ID plus one,
     * starting from {@code 1} for the very first session.
     *
     * @return the newly created (and now current) session
     */
    public Session createSession() {
        Session session = new Session(nextId++);
        sessions.put(session.getId(), session);
        currentSession = session;
        return session;
    }

    /**
     * Switches the active session to the one identified by {@code id} and
     * returns it.
     *
     * @param id the target session's identifier
     * @return the session now set as current
     * @throws IllegalArgumentException if no session with the given id exists
     */
    public Session switchSession(int id) {
        Session session = sessions.get(id);
        if (session == null) {
            throw new SessionNullPointerException("session with ID " + id + " does not exist.");
        }
        currentSession = session;
        return session;
    }

    /**
     * Returns the currently active session.
     *
     * @return the current session, or {@code null} if no session is open
     */
    public Session getCurrentSession() {
        return currentSession;
    }

    /**
     * Returns {@code true} when there is an active session that contains at
     * least one loaded image.
     *
     * @return {@code true} if a non-empty session is currently active
     */
    public boolean hasActiveSession() {
        return currentSession != null && currentSession.hasImages();
    }

    /**
     * Returns an unmodifiable view of all registered sessions keyed by id.
     *
     * @return read-only map of {@code id → Session}
     */
    public Map<Integer, Session> getAllSessions() {
        return Map.copyOf(sessions);
    }

    /**
     * Removes the current session from the registry and sets the current
     * session to {@code null}.
     *
     * <p>After this call no session is active; the next command that requires
     * an active session will report an error until a new {@code load} is issued.
     * Called by {@code CloseCommand}.
     */
    public void closeCurrentSession() {
        if (currentSession != null) {
            sessions.remove(currentSession.getId());
            currentSession = null;
        }
    }
}
