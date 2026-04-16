package dev.marcosmoreira.desktopconsultorio.contracts.session;

import dev.marcosmoreira.desktopconsultorio.session.SessionSnapshot;
import java.util.Optional;

public interface SessionStore {
    void save(SessionSnapshot snapshot);
    Optional<SessionSnapshot> getCurrentSession();
    void clear();
}
