package dev.marcosmoreira.desktopconsultorio.session;

import dev.marcosmoreira.desktopconsultorio.contracts.session.SessionStore;
import java.util.Optional;

/**
 * Almacén de sesión simple en memoria para el runtime local del desktop.
 */
public class InMemorySessionStore implements SessionStore {

    private SessionSnapshot currentSession;

    @Override
    public synchronized void save(SessionSnapshot snapshot) {
        this.currentSession = snapshot;
        SessionSnapshot.setCurrent(snapshot);
    }

    @Override
    public synchronized Optional<SessionSnapshot> getCurrentSession() {
        return Optional.ofNullable(currentSession);
    }

    @Override
    public synchronized void clear() {
        this.currentSession = null;
        SessionSnapshot.setCurrent(null);
    }
}
