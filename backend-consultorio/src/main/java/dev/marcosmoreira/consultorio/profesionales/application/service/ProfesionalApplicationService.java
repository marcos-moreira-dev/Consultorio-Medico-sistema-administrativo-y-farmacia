package dev.marcosmoreira.consultorio.profesionales.application.service;

import dev.marcosmoreira.consultorio.profesionales.application.port.in.ActualizarProfesionalUseCase;
import dev.marcosmoreira.consultorio.profesionales.application.port.in.BuscarProfesionalUseCase;
import dev.marcosmoreira.consultorio.profesionales.application.port.in.CambiarEstadoProfesionalUseCase;
import dev.marcosmoreira.consultorio.profesionales.application.port.in.CrearProfesionalUseCase;
import dev.marcosmoreira.consultorio.profesionales.application.port.in.ListarProfesionalesUseCase;
import dev.marcosmoreira.consultorio.profesionales.application.port.out.ProfesionalPersistencePort;
import dev.marcosmoreira.consultorio.profesionales.domain.enums.EstadoProfesional;
import dev.marcosmoreira.consultorio.profesionales.domain.model.Profesional;
import dev.marcosmoreira.consultorio.shared.error.DuplicateResourceException;
import dev.marcosmoreira.consultorio.shared.error.ResourceNotFoundException;
import dev.marcosmoreira.consultorio.usuarios.application.port.in.BuscarUsuarioUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio de aplicación del módulo de profesionales.
 *
 * <p>Orquesta la gestión básica de la identidad operativa de los profesionales
 * clínicos del consultorio: creación, edición, consulta, listado y cambio de estado.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
public class ProfesionalApplicationService implements
        CrearProfesionalUseCase,
        ActualizarProfesionalUseCase,
        BuscarProfesionalUseCase,
        ListarProfesionalesUseCase,
        CambiarEstadoProfesionalUseCase {

    private final ProfesionalPersistencePort profesionalPersistencePort;
    private final BuscarUsuarioUseCase buscarUsuarioUseCase;

    /**
     * Construye el servicio de aplicación del módulo.
     *
     * @param profesionalPersistencePort puerto de persistencia de profesionales
     * @param buscarUsuarioUseCase caso de uso para validar existencia de usuario
     */
    public ProfesionalApplicationService(
            ProfesionalPersistencePort profesionalPersistencePort,
            BuscarUsuarioUseCase buscarUsuarioUseCase
    ) {
        this.profesionalPersistencePort = profesionalPersistencePort;
        this.buscarUsuarioUseCase = buscarUsuarioUseCase;
    }

    /**
     * Registra un nuevo profesional.
     *
     * @param usuarioId identificador del usuario asociado, si existe
     * @param nombres nombres del profesional
     * @param apellidos apellidos del profesional
     * @param especialidadBreve especialidad breve
     * @param registroProfesional registro profesional, si aplica
     * @return profesional creado
     */
    @Override
    @Transactional
    public Profesional crear(
            Long usuarioId,
            String nombres,
            String apellidos,
            String especialidadBreve,
            String registroProfesional
    ) {
        validateOptionalPositiveId(usuarioId, "Si se envía usuarioId, debe ser mayor que cero.");
        String normalizedNombres = normalizeRequiredText(nombres, "Los nombres son obligatorios.");
        String normalizedApellidos = normalizeRequiredText(apellidos, "Los apellidos son obligatorios.");
        String normalizedEspecialidad = normalizeNullableText(especialidadBreve);
        String normalizedRegistro = normalizeNullableText(registroProfesional);

        validateUsuarioExistenteSiCorresponde(usuarioId);
        validateUserAssociationForCreate(usuarioId);

        Profesional profesional = new Profesional();
        profesional.setUsuarioId(usuarioId);
        profesional.setNombres(normalizedNombres);
        profesional.setApellidos(normalizedApellidos);
        profesional.setEspecialidadBreve(normalizedEspecialidad);
        profesional.setRegistroProfesional(normalizedRegistro);
        profesional.setEstadoProfesional(EstadoProfesional.ACTIVO);

        return profesionalPersistencePort.guardar(profesional);
    }

    /**
     * Actualiza los datos básicos de un profesional.
     *
     * @param profesionalId identificador del profesional
     * @param usuarioId identificador del usuario asociado, si existe
     * @param nombres nombres del profesional
     * @param apellidos apellidos del profesional
     * @param especialidadBreve especialidad breve
     * @param registroProfesional registro profesional, si aplica
     * @return profesional actualizado
     */
    @Override
    @Transactional
    public Profesional actualizar(
            Long profesionalId,
            Long usuarioId,
            String nombres,
            String apellidos,
            String especialidadBreve,
            String registroProfesional
    ) {
        validatePositiveId(profesionalId, "El identificador del profesional debe ser mayor que cero.");
        validateOptionalPositiveId(usuarioId, "Si se envía usuarioId, debe ser mayor que cero.");

        Profesional profesional = buscarPorId(profesionalId);

        validateUsuarioExistenteSiCorresponde(usuarioId);
        validateUserAssociationForUpdate(usuarioId, profesionalId);

        profesional.setUsuarioId(usuarioId);
        profesional.setNombres(normalizeRequiredText(nombres, "Los nombres son obligatorios."));
        profesional.setApellidos(normalizeRequiredText(apellidos, "Los apellidos son obligatorios."));
        profesional.setEspecialidadBreve(normalizeNullableText(especialidadBreve));
        profesional.setRegistroProfesional(normalizeNullableText(registroProfesional));

        return profesionalPersistencePort.guardar(profesional);
    }

    /**
     * Busca un profesional por su identificador único.
     *
     * @param profesionalId identificador del profesional
     * @return profesional encontrado
     */
    @Override
    public Profesional buscarPorId(Long profesionalId) {
        validatePositiveId(profesionalId, "El identificador del profesional debe ser mayor que cero.");

        return profesionalPersistencePort.buscarPorId(profesionalId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró un profesional con ID " + profesionalId + "."
                ));
    }

    /**
     * Lista profesionales aplicando filtros opcionales.
     *
     * @param usuarioId identificador del usuario asociado, o {@code null} si no aplica
     * @param estadoProfesional estado lógico, o {@code null} si no aplica
     * @param q texto libre, o {@code null} si no aplica
     * @return lista de profesionales encontrados
     */
    @Override
    public Page<Profesional> listar(Long usuarioId, EstadoProfesional estadoProfesional, String q, Pageable pageable) {
        validateOptionalPositiveId(usuarioId, "Si se envía usuarioId, debe ser mayor que cero.");
        Pageable sanitizedPageable = dev.marcosmoreira.consultorio.shared.util.PageableUtils.sanitize(
                pageable,
                Sort.by(Sort.Order.asc("apellidos"), Sort.Order.asc("nombres"), Sort.Order.asc("profesionalId")),
                "apellidos", "nombres", "profesionalId", "estadoProfesional"
        );
        return profesionalPersistencePort.listar(usuarioId, estadoProfesional, normalizeNullableText(q), sanitizedPageable);
    }

    /**
     * Cambia el estado lógico de un profesional.
     *
     * @param profesionalId identificador del profesional
     * @param nuevoEstado nuevo estado a asignar
     * @return profesional actualizado
     */
    @Override
    @Transactional
    public Profesional cambiarEstado(Long profesionalId, EstadoProfesional nuevoEstado) {
        validatePositiveId(profesionalId, "El identificador del profesional debe ser mayor que cero.");

        if (nuevoEstado == null) {
            throw new IllegalArgumentException("El nuevo estado es obligatorio.");
        }

        Profesional profesional = buscarPorId(profesionalId);
        profesional.setEstadoProfesional(nuevoEstado);

        return profesionalPersistencePort.guardar(profesional);
    }

    /**
     * Valida que el usuario exista, si se envió uno.
     *
     * @param usuarioId identificador de usuario opcional
     */
    private void validateUsuarioExistenteSiCorresponde(Long usuarioId) {
        if (usuarioId != null) {
            buscarUsuarioUseCase.buscarPorId(usuarioId);
        }
    }

    /**
     * Valida la unicidad de asociación usuario-profesional al crear.
     *
     * @param usuarioId usuario opcional a asociar
     */
    private void validateUserAssociationForCreate(Long usuarioId) {
        if (usuarioId != null && profesionalPersistencePort.existsByUsuarioId(usuarioId)) {
            throw new DuplicateResourceException(
                    "Ya existe un profesional asociado al usuario con ID " + usuarioId + "."
            );
        }
    }

    /**
     * Valida la unicidad de asociación usuario-profesional al actualizar.
     *
     * @param usuarioId usuario opcional a asociar
     * @param profesionalId profesional actual
     */
    private void validateUserAssociationForUpdate(Long usuarioId, Long profesionalId) {
        if (usuarioId != null
                && profesionalPersistencePort.existsByUsuarioIdAndProfesionalIdNot(usuarioId, profesionalId)) {
            throw new DuplicateResourceException(
                    "Ya existe otro profesional asociado al usuario con ID " + usuarioId + "."
            );
        }
    }

    /**
     * Valida que un identificador obligatorio sea positivo.
     *
     * @param value identificador a validar
     * @param message mensaje de error
     */
    private void validatePositiveId(Long value, String message) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Valida que un identificador opcional, si existe, sea positivo.
     *
     * @param value identificador opcional a validar
     * @param message mensaje de error
     */
    private void validateOptionalPositiveId(Long value, String message) {
        if (value != null && value <= 0) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Normaliza y valida un texto obligatorio.
     *
     * @param value texto a validar
     * @param message mensaje de error
     * @return texto normalizado
     */
    private String normalizeRequiredText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }

        return value.trim();
    }

    /**
     * Normaliza un texto opcional.
     *
     * @param value texto a normalizar
     * @return texto normalizado o {@code null} si queda vacío
     */
    private String normalizeNullableText(String value) {
        if (value == null) {
            return null;
        }

        String normalized = value.trim();
        return normalized.isEmpty() ? null : normalized;
    }
}
