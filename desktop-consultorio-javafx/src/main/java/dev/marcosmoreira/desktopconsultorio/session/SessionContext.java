package dev.marcosmoreira.desktopconsultorio.session;

import dev.marcosmoreira.desktopconsultorio.contracts.session.SessionStore;
import java.util.Optional;

/**
 * Fachada ligera sobre el {@link SessionStore} para consumo de la UI.
 */
public class SessionContext {

    private final SessionStore sessionStore;

    public SessionContext(SessionStore sessionStore) {
        this.sessionStore = sessionStore;
    }

    public Optional<SessionSnapshot> currentSession() {
        return sessionStore.getCurrentSession();
    }

    public boolean isAuthenticated() {
        return sessionStore.getCurrentSession().map(SessionSnapshot::isAuthenticated).orElse(false);
    }

    public void clear() {
        sessionStore.clear();
    }
}
