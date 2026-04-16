package dev.marcosmoreira.consultorio.pacientes.application.service;

import dev.marcosmoreira.consultorio.pacientes.application.port.in.ActualizarPacienteUseCase;
import dev.marcosmoreira.consultorio.pacientes.application.port.in.BuscarPacienteUseCase;
import dev.marcosmoreira.consultorio.pacientes.application.port.in.CrearPacienteUseCase;
import dev.marcosmoreira.consultorio.pacientes.application.port.in.ListarPacientesUseCase;
import dev.marcosmoreira.consultorio.pacientes.application.port.out.PacientePersistencePort;
import dev.marcosmoreira.consultorio.pacientes.domain.exception.PacienteDuplicadoException;
import dev.marcosmoreira.consultorio.pacientes.domain.model.Paciente;
import dev.marcosmoreira.consultorio.shared.error.ResourceNotFoundException;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio de aplicación del módulo de pacientes.
 *
 * <p>Orquesta la gestión básica de pacientes: creación, actualización,
 * consulta puntual y listado.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
public class PacienteApplicationService implements
        CrearPacienteUseCase,
        ActualizarPacienteUseCase,
        BuscarPacienteUseCase,
        ListarPacientesUseCase {

    private final PacientePersistencePort pacientePersistencePort;

    /**
     * Construye el servicio de aplicación del módulo.
     *
     * @param pacientePersistencePort puerto de persistencia de pacientes
     */
    public PacienteApplicationService(PacientePersistencePort pacientePersistencePort) {
        this.pacientePersistencePort = pacientePersistencePort;
    }

    /**
     * Registra un nuevo paciente.
     *
     * @param nombres nombres del paciente
     * @param apellidos apellidos del paciente
     * @param telefono teléfono, si existe
     * @param cedula cédula, si existe
     * @param fechaNacimiento fecha de nacimiento, si existe
     * @param direccionBasica dirección básica, si existe
     * @return paciente creado
     */
    @Override
    @Transactional
    public Paciente crear(
            String nombres,
            String apellidos,
            String telefono,
            String cedula,
            LocalDate fechaNacimiento,
            String direccionBasica
    ) {
        String normalizedNombres = normalizeRequiredText(nombres, "Los nombres son obligatorios.");
        String normalizedApellidos = normalizeRequiredText(apellidos, "Los apellidos son obligatorios.");
        String normalizedTelefono = normalizeNullableText(telefono);
        String normalizedCedula = normalizeNullableText(cedula);
        String normalizedDireccion = normalizeNullableText(direccionBasica);

        validateCedulaUniquenessForCreate(normalizedCedula);

        Paciente paciente = new Paciente();
        paciente.setNombres(normalizedNombres);
        paciente.setApellidos(normalizedApellidos);
        paciente.setTelefono(normalizedTelefono);
        paciente.setCedula(normalizedCedula);
        paciente.setFechaNacimiento(fechaNacimiento);
        paciente.setDireccionBasica(normalizedDireccion);

        return pacientePersistencePort.guardar(paciente);
    }

    /**
     * Actualiza un paciente existente.
     *
     * @param pacienteId identificador del paciente
     * @param nombres nombres del paciente
     * @param apellidos apellidos del paciente
     * @param telefono teléfono, si existe
     * @param cedula cédula, si existe
     * @param fechaNacimiento fecha de nacimiento, si existe
     * @param direccionBasica dirección básica, si existe
     * @return paciente actualizado
     */
    @Override
    @Transactional
    public Paciente actualizar(
            Long pacienteId,
            String nombres,
            String apellidos,
            String telefono,
            String cedula,
            LocalDate fechaNacimiento,
            String direccionBasica
    ) {
        validatePositiveId(pacienteId, "El identificador del paciente debe ser mayor que cero.");

        Paciente paciente = buscarPorId(pacienteId);

        String normalizedNombres = normalizeRequiredText(nombres, "Los nombres son obligatorios.");
        String normalizedApellidos = normalizeRequiredText(apellidos, "Los apellidos son obligatorios.");
        String normalizedTelefono = normalizeNullableText(telefono);
        String normalizedCedula = normalizeNullableText(cedula);
        String normalizedDireccion = normalizeNullableText(direccionBasica);

        validateCedulaUniquenessForUpdate(normalizedCedula, pacienteId);

        paciente.setNombres(normalizedNombres);
        paciente.setApellidos(normalizedApellidos);
        paciente.setTelefono(normalizedTelefono);
        paciente.setCedula(normalizedCedula);
        paciente.setFechaNacimiento(fechaNacimiento);
        paciente.setDireccionBasica(normalizedDireccion);

        return pacientePersistencePort.guardar(paciente);
    }

    /**
     * Busca un paciente por su identificador único.
     *
     * @param pacienteId identificador del paciente
     * @return paciente encontrado
     */
    @Override
    public Paciente buscarPorId(Long pacienteId) {
        validatePositiveId(pacienteId, "El identificador del paciente debe ser mayor que cero.");

        return pacientePersistencePort.buscarPorId(pacienteId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró un paciente con ID " + pacienteId + "."
                ));
    }

    /**
     * Lista pacientes aplicando filtros opcionales.
     *
     * @param cedula cédula exacta, o {@code null} si no aplica
     * @param fechaNacimiento fecha de nacimiento exacta, o {@code null} si no aplica
     * @param q texto libre, o {@code null} si no aplica
     * @return lista de pacientes encontrados
     */
    @Override
    public Page<Paciente> listar(String cedula, LocalDate fechaNacimiento, String q, Pageable pageable) {
        Pageable sanitizedPageable = dev.marcosmoreira.consultorio.shared.util.PageableUtils.sanitize(
                pageable,
                Sort.by(Sort.Order.asc("apellidos"), Sort.Order.asc("nombres"), Sort.Order.asc("pacienteId")),
                "apellidos", "nombres", "pacienteId", "fechaNacimiento"
        );
        return pacientePersistencePort.listar(normalizeNullableText(cedula), fechaNacimiento, normalizeNullableText(q), sanitizedPageable);
    }

    /**
     * Valida unicidad de cédula al crear, cuando la cédula viene informada.
     *
     * @param cedula cédula a verificar
     */
    private void validateCedulaUniquenessForCreate(String cedula) {
        if (cedula != null && pacientePersistencePort.existsByCedulaIgnoreCase(cedula)) {
            throw new PacienteDuplicadoException(
                    "Ya existe un paciente con la cédula '" + cedula + "'."
            );
        }
    }

    /**
     * Valida unicidad de cédula al actualizar, cuando la cédula viene informada.
     *
     * @param cedula cédula a verificar
     * @param pacienteId identificador del paciente actual
     */
    private void validateCedulaUniquenessForUpdate(String cedula, Long pacienteId) {
        if (cedula != null
                && pacientePersistencePort.existsByCedulaIgnoreCaseAndPacienteIdNot(cedula, pacienteId)) {
            throw new PacienteDuplicadoException(
                    "Ya existe otro paciente con la cédula '" + cedula + "'."
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
