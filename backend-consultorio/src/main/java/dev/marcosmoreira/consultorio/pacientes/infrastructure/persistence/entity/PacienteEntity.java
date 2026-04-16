package dev.marcosmoreira.consultorio.pacientes.infrastructure.persistence.entity;

import dev.marcosmoreira.consultorio.shared.persistence.AuditableEntity;
import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Entidad JPA del módulo de pacientes.
 *
 * <p>Se alinea con la tabla {@code paciente} de la base de datos V2 del proyecto.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@Entity
@Table(schema = "consultorio", name = "paciente")
public class PacienteEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paciente_id")
    private Long pacienteId;

    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;

    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "cedula", length = 20)
    private String cedula;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "direccion_basica", length = 200)
    private String direccionBasica;

    /**
     * Constructor vacío requerido por JPA.
     */
    public PacienteEntity() {
    }

    /**
     * Construye una entidad completa de paciente.
     *
     * @param pacienteId identificador del paciente
     * @param nombres nombres del paciente
     * @param apellidos apellidos del paciente
     * @param telefono teléfono
     * @param cedula cédula
     * @param fechaNacimiento fecha de nacimiento
     * @param direccionBasica dirección básica
     */
    public PacienteEntity(
            Long pacienteId,
            String nombres,
            String apellidos,
            String telefono,
            String cedula,
            LocalDate fechaNacimiento,
            String direccionBasica
    ) {
        this.pacienteId = pacienteId;
        this.nombres = normalizeNullableText(nombres);
        this.apellidos = normalizeNullableText(apellidos);
        this.telefono = normalizeNullableText(telefono);
        this.cedula = normalizeNullableText(cedula);
        this.fechaNacimiento = fechaNacimiento;
        this.direccionBasica = normalizeNullableText(direccionBasica);
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = normalizeNullableText(nombres);
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = normalizeNullableText(apellidos);
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = normalizeNullableText(telefono);
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = normalizeNullableText(cedula);
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDireccionBasica() {
        return direccionBasica;
    }

    public void setDireccionBasica(String direccionBasica) {
        this.direccionBasica = normalizeNullableText(direccionBasica);
    }

    /**
     * Normaliza un texto opcional.
     *
     * @param value texto a normalizar
     * @return texto con trim aplicado o {@code null} si queda vacío
     */
    private String normalizeNullableText(String value) {
        if (value == null) {
            return null;
        }

        String normalized = value.trim();
        return normalized.isEmpty() ? null : normalized;
    }
}
