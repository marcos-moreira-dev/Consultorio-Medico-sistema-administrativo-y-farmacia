package dev.marcosmoreira.consultorio.auditoria.infrastructure.query;

import dev.marcosmoreira.consultorio.auditoria.api.response.EventoAuditoriaResponse;
import dev.marcosmoreira.consultorio.auditoria.application.port.out.AuditoriaQueryPort;
import dev.marcosmoreira.consultorio.auditoria.domain.enums.TipoEventoAuditoria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador de consultas para el módulo de auditoría.
 *
 * <p>Implementa queries JPQL contra la tabla {@code audit_log} del schema
 * {@code consultorio}, aplicando filtros opcionales y paginación.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@Component
public class AuditoriaQueryAdapter implements AuditoriaQueryPort {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<EventoAuditoriaResponse> listar(
            String modulo,
            TipoEventoAuditoria tipoEvento,
            Long usuarioId,
            String correlationId,
            LocalDateTime fechaDesde,
            LocalDateTime fechaHasta,
            Pageable pageable
    ) {
        String jpql = "SELECT new dev.marcosmoreira.consultorio.auditoria.api.response.EventoAuditoriaResponse("
                + "a.eventoId, a.usuarioId, a.username, a.tipoEvento, a.modulo, "
                + "a.descripcion, a.entidadId, a.entidadTipo, a.correlationId, "
                + "a.direccionIp, a.fechaHora) "
                + "FROM AuditLogEntity a WHERE 1=1";

        String countJpql = "SELECT COUNT(a) FROM AuditLogEntity a WHERE 1=1";

        List<Object> params = new ArrayList<>();
        int paramIndex = 1;

        // Aplicar filtros opcionales
        if (modulo != null && !modulo.isBlank()) {
            jpql += " AND a.modulo = ?" + paramIndex;
            countJpql += " AND a.modulo = ?" + paramIndex;
            params.add(modulo);
            paramIndex++;
        }

        if (tipoEvento != null) {
            jpql += " AND a.tipoEvento = ?" + paramIndex;
            countJpql += " AND a.tipoEvento = ?" + paramIndex;
            params.add(tipoEvento.name());
            paramIndex++;
        }

        if (usuarioId != null) {
            jpql += " AND a.usuarioId = ?" + paramIndex;
            countJpql += " AND a.usuarioId = ?" + paramIndex;
            params.add(usuarioId);
            paramIndex++;
        }

        if (correlationId != null && !correlationId.isBlank()) {
            jpql += " AND a.correlationId = ?" + paramIndex;
            countJpql += " AND a.correlationId = ?" + paramIndex;
            params.add(correlationId);
            paramIndex++;
        }

        if (fechaDesde != null) {
            jpql += " AND a.fechaHora >= ?" + paramIndex;
            countJpql += " AND a.fechaHora >= ?" + paramIndex;
            params.add(fechaDesde);
            paramIndex++;
        }

        if (fechaHasta != null) {
            jpql += " AND a.fechaHora <= ?" + paramIndex;
            countJpql += " AND a.fechaHora <= ?" + paramIndex;
            params.add(fechaHasta);
            paramIndex++;
        }

        // Orden por defecto: fecha descendente
        jpql += " ORDER BY a.fechaHora DESC";

        // Query de datos
        Query query = entityManager.createQuery(jpql);
        for (int i = 0; i < params.size(); i++) {
            query.setParameter(i + 1, params.get(i));
        }
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        List<EventoAuditoriaResponse> content = query.getResultList();

        // Query de conteo
        Query countQuery = entityManager.createQuery(countJpql);
        for (int i = 0; i < params.size(); i++) {
            countQuery.setParameter(i + 1, params.get(i));
        }
        Long total = (Long) countQuery.getSingleResult();

        return new PageImpl<>(content, pageable, total);
    }
}
